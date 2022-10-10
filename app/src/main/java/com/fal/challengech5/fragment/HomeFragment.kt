package com.fal.challengech5.fragment

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fal.challengech5.R
import com.fal.challengech5.adapter.ListAdapter
import com.fal.challengech5.databinding.FragmentHomeBinding
import com.fal.challengech5.model.ResponseDataTaskItem
import com.fal.challengech5.network.ApiClient
import com.fal.challengech5.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : ListAdapter
    lateinit var share : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //SharedPref
        share = requireActivity().getSharedPreferences("account", Context.MODE_PRIVATE)
        val name = share.getString("username","username")
        val userId = share.getString("id", "0")
        binding.welcomeBar.text = "Welcome, $name!!"
        Log.d("Homescreen", "Username : $name")

        //VM
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.allLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setData(it as ArrayList<ResponseDataTaskItem>)
        })
        viewModel.callAllData(userId!!)

        //Retrofit
        showList(userId)

        //RV
        adapter = ListAdapter(ArrayList())
        binding.rvTask.adapter = adapter
        binding.rvTask.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //Profile
        binding.btnProfil.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

    }

    private fun showList(userId : String) {
            ApiClient.instance.getTask(userId)
                .enqueue(object : Callback<List<ResponseDataTaskItem>>{
                    override fun onResponse(
                        call: Call<List<ResponseDataTaskItem>>,
                        response: Response<List<ResponseDataTaskItem>>,
                    ) {
                        if (response.isSuccessful){
                            binding.rvTask.adapter = ListAdapter(response.body()!!)
                            binding.rvTask.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            Log.d("Response API", "LOAD DATA SUCCESS")
                        }else{
                            Log.d("Response API", "LOAD DATA FAILED")
                        }
                    }

                    override fun onFailure(call: Call<List<ResponseDataTaskItem>>, t: Throwable) {
                        Toast.makeText(context, "Something Wrong", Toast.LENGTH_LONG).show()
                    }

                })
    }

    override fun onResume() {
        super.onResume()
        val userId = share.getString("id", "0")
        viewModel.callAllData(userId!!)
        //Retrofit
        showList(userId)
    }
}