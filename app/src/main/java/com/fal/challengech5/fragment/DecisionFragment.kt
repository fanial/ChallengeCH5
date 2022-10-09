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
            var getData = arguments?.getString("idTask")
            val getUser = arguments?.getString("userId")
            deleteTask(getData!!, getUser!!)
            Log.i("DELETE TASK", "Task ID = ${getData}, UserID = ${getUser}")
        }
    }

    private fun deleteTask(data: String, getUser : String) {
        val model = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.callDeleteData(getUser, data)
        model.deleteLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(context, "Delete Data Success", Toast.LENGTH_SHORT).show()
                Log.d("deleteFilm", it.toString())
            }
        })

    }

}