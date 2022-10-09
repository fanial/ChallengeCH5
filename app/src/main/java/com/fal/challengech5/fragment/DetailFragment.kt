package com.fal.challengech5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fal.challengech5.R
import com.fal.challengech5.databinding.FragmentDetailBinding
import com.fal.challengech5.model.ResponseDataTaskItem

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var share : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Shared Preference
        share = requireActivity().getSharedPreferences("account", Context.MODE_PRIVATE)
        val name = share.getString("username","username")
        Log.d("Homescreen", "Username : $name")

        //Bundle from adapter
        val getData = arguments?.getSerializable("dataTask") as ResponseDataTaskItem
        val idTask = getData.idTask
        val userid = getData.userId
        val category = getData.category
        val content = getData.content
        val title = getData.title
        val image = getData.image
        binding.vTitle.text = title
        Glide.with(this).load(image).into(binding.vImage)
        binding.vContent.text = content
        binding.vCategory.text = category

        binding.btnUpdate.setOnClickListener {
            val data = Bundle()
            data.putString("idTask", idTask)
            data.putString("userId", userid)
            data.putString("category", category)
            data.putString("content", content)
            data.putString("title", title)
            data.putString("image", image)
            findNavController().navigate(R.id.action_detailFragment_to_updateFragment, data)
            Log.d("DATA UPDATE", "data bundle : ${data}")
        }

        binding.btnDelete.setOnClickListener {
            val data = Bundle()
            data.putString("idTask", idTask)
            data.putString("userId", userid)
            findNavController().navigate(R.id.action_detailFragment_to_decisionFragment, data)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.remove()
                activity?.onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}