package com.fal.challengech5.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fal.challengech5.R
import com.fal.challengech5.databinding.FragmentDecisionBinding
import com.fal.challengech5.viewModel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DecisionFragment : Fragment() {

    private var _binding: FragmentDecisionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDecisionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnYes.setOnClickListener {
        }
    }

}