package com.fal.challengech5.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fal.challengech5.model.ResponseDataUserItem
import com.fal.challengech5.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel : ViewModel() {
    lateinit var userData : MutableLiveData<ResponseDataUserItem?>

    init {
        userData = MutableLiveData()
    }

    fun liveUser() : MutableLiveData<ResponseDataUserItem?>{
        return userData
    }

    fun postDataUser(email : String, id : String, password : String, username : String){
        ApiClient.instance.postUser(ResponseDataUserItem(email, id, password, username))
            .enqueue(object : Callback<ResponseDataUserItem>{
                override fun onResponse(
                    call: Call<ResponseDataUserItem>,
                    response: Response<ResponseDataUserItem>,
                ) {
                    if (response.isSuccessful){
                        userData.postValue(response.body())
                    }else{
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseDataUserItem>, t: Throwable) {
                    userData.postValue(null)
                }
            })
    }
}