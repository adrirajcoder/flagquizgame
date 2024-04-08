package com.adriraj.flagquizapp.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.adriraj.flagquizapp.R
import com.adriraj.flagquizapp.databinding.FragmentResultBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class FragmentResult : Fragment() {
    lateinit var binding: FragmentResultBinding

    private var correctNumber = 0F
    private var emptyNumber = 0F
    private var wrongNumber = 0F
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(layoutInflater)
        val view = binding.root

        val args = arguments?.let {
            FragmentResultArgs.fromBundle(it)
        }

        args?.let {
            correctNumber = it.correct.toFloat()
            emptyNumber = it.empty.toFloat()
            wrongNumber = it.wrong.toFloat()
        }

        val barEntriesArrayListCorrect = ArrayList<BarEntry>()
        val barEntriesArrayListEmpty = ArrayList<BarEntry>()
        val barEntriesArrayListWrong = ArrayList<BarEntry>()

        barEntriesArrayListCorrect.add(BarEntry(0F, correctNumber))
        barEntriesArrayListEmpty.add(BarEntry(1F, emptyNumber))
        barEntriesArrayListWrong.add(BarEntry(2F, wrongNumber))

        val barDataSetCorrect = BarDataSet(barEntriesArrayListCorrect, "Correct").apply {
            color = Color.GREEN
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.WHITE))
        }
        val barDataSetEmpty = BarDataSet(barEntriesArrayListEmpty, "Empty").apply {
            color = Color.BLUE
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.WHITE))
        }
        val barDataSetWrong = BarDataSet(barEntriesArrayListWrong, "Wrong").apply {
            color = Color.RED
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.WHITE))
        }

        val barData = BarData(barDataSetCorrect, barDataSetEmpty, barDataSetWrong)

        binding.resultChart.data = barData

        binding.buttonNewQuiz.setOnClickListener {
            this.findNavController().popBackStack(R.id.fragmentHome, false)
        }

        binding.buttonExit.setOnClickListener {
            requireActivity().finish()
        }
        return view
    }

}