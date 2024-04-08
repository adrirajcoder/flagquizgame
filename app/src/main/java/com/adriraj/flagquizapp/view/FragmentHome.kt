package com.adriraj.flagquizapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.adriraj.flagquizapp.R
import com.adriraj.flagquizapp.databinding.FragmentHomeBinding
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper

class FragmentHome : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        createAndOpenDatabase()

        binding.startButton.setOnClickListener{
            val direction = FragmentHomeDirections.actionFragmentHomeToFragmentQuiz()
            this.findNavController().navigate(direction)
            //it.findNavController()
            //requireActivity().findNavController()
        }

        return view
    }

    private fun createAndOpenDatabase(){
        try {
            val helper = DatabaseCopyHelper(requireContext())
            helper.createDataBase()
            helper.openDataBase()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}