package com.adriraj.flagquizapp.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.adriraj.flagquizapp.R
import com.adriraj.flagquizapp.database.FlagsDao
import com.adriraj.flagquizapp.databinding.FragmentQuizBinding
import com.adriraj.flagquizapp.model.FlagsModel
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper

class FragmentQuiz : Fragment() {
    lateinit var binding: FragmentQuizBinding
    var flagList = ArrayList<FlagsModel>()

    var correct = 0
    var empty = 0
    var wrong = 0
    var questionNumber = 0

    val dao = FlagsDao()

    var optionControl = false

    lateinit var correctFlag : FlagsModel

    var wrongFlags = ArrayList<FlagsModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(layoutInflater)
        val view = binding.root

        flagList = dao.getRandomTenRecords(DatabaseCopyHelper(requireActivity()))

//        for(flag in flagList){
//            Log.d("flags", flag.id.toString())
//            Log.d("flags", flag.country_name.toString())
//            Log.d("flags", flag.flag_name.toString())
//            Log.d("flags", "**********************")
//        }

        showData()


        binding.buttonA.setOnClickListener {
            answerControl(binding.buttonA)
        }

        binding.buttonB.setOnClickListener {
            answerControl(binding.buttonB)
        }

        binding.buttonC.setOnClickListener {
            answerControl(binding.buttonC)
        }

        binding.buttonD.setOnClickListener {
            answerControl(binding.buttonD)
        }

        binding.buttonNext.setOnClickListener {
            questionNumber++

            if (questionNumber > 9){

                if (!optionControl){
                    empty++
                }

                //Toast.makeText(requireContext(), "The quiz is finished", Toast.LENGTH_SHORT).show()

                val action = FragmentQuizDirections.actionFragmentQuizToFragmentResult(correct, empty, wrong)
                this.findNavController().apply {
                    navigate(action)
                    popBackStack(R.id.fragmentResult, false)
                }

            }else{

                showData()

                if (!optionControl){
                    empty++
                    binding.empty.text = empty.toString()
                }else{
                    setButtonToInitialProperties()
                }
            }
            //setButtonToInitialProperties()
            optionControl = false

        }
        return view
    }

    private fun showData(){
        binding.question.text = resources.getString(R.string.question_number).plus(" ").plus(questionNumber + 1)
        correctFlag = flagList[questionNumber]
        binding.flag.setImageResource(resources.getIdentifier(correctFlag.flag_name, "drawable", requireActivity().packageName))
        wrongFlags = dao.getRandomThreeRecords(DatabaseCopyHelper(requireActivity()), correctFlag.id)

        val mixOptions = HashSet<FlagsModel>()
        mixOptions.clear()

        mixOptions.add(correctFlag)
        mixOptions.add(wrongFlags[0])
        mixOptions.add(wrongFlags[1])
        mixOptions.add(wrongFlags[2])

        val options = ArrayList<FlagsModel>()
        options.clear()

        for (eachFlag in mixOptions){
            options.add(eachFlag)
        }

        binding.buttonA.text = options[0].country_name
        binding.buttonB.text = options[1].country_name
        binding.buttonC.text = options[2].country_name
        binding.buttonD.text = options[3].country_name


    }

    private fun answerControl(button : Button){
        val clickedOption = button.text.toString()
        val correctAnswer = correctFlag.country_name

        if (clickedOption == correctAnswer){
            correct++
            binding.correct.text = correct.toString()
            button.setBackgroundColor(Color.GREEN)
        } else {
            wrong++
            binding.wrong.text = wrong.toString()
            button.setBackgroundColor(Color.RED)
            button.setTextColor(Color.WHITE)

            when (correctAnswer) {
                binding.buttonA.text -> binding.buttonA.setBackgroundColor(Color.GREEN)
                binding.buttonB.text -> binding.buttonB.setBackgroundColor(Color.GREEN)
                binding.buttonC.text -> binding.buttonC.setBackgroundColor(Color.GREEN)
                binding.buttonD.text -> binding.buttonD.setBackgroundColor(Color.GREEN)
            }
        }

            binding.buttonA.isClickable = false
            binding.buttonB.isClickable = false
            binding.buttonC.isClickable = false
            binding.buttonD.isClickable = false

            optionControl = true

    }

    private fun setButtonToInitialProperties(){
        binding.buttonA.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }

        binding.buttonB.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }

        binding.buttonC.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }

        binding.buttonD.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }
    }
}