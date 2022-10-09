package com.fal.challengech5.fragment

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.fal.challengech5.R
import com.fal.challengech5.databinding.FragmentLoginBinding
import com.fal.challengech5.model.ResponseDataUserItem
import com.fal.challengech5.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var sharedPref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentLoginBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("account", Context.MODE_PRIVATE)

        binding.btnRegist.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registFragment)
        }

        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            auth(username,password)
        }

    }

    private fun auth(username: String, password: String) {
        ApiClient.instance.getUser()
            .enqueue(object : Callback<List<ResponseDataUserItem>>{
                override fun onResponse(
                    call: Call<List<ResponseDataUserItem>>,
                    response: Response<List<ResponseDataUserItem>>,
                ) {
                    if (response.isSuccessful){
                        val resBody = response.body()
                        if (resBody != null){
                            Log.d(tag,"RESPONSE : ${resBody.toString()}")
                            for (i in 0 until resBody.size) {
                                if(resBody[i].username.equals(username) && resBody[i].password.equals(password)) {
                                    var addData = sharedPref.edit()
                                    addData.putString("email", resBody[i].email)
                                    addData.putString("username",resBody[i].username)
                                    addData.putString("password",resBody[i].password)
                                    addData.putString("id",resBody[i].id)
                                    addData.apply()
                                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                                    Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                    //binding.tvAlertCantLogin.visibility = View.INVISIBLE
                                } else {
                                    //binding.tvAlertCantLogin.visibility = View.VISIBLE
                                  Toast.makeText(context, "Username / Password salah", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }else{
                        Toast.makeText(context, "Failed to Load Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataUserItem>>, t: Throwable) {
                    Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show()
                }

            })
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
        onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}