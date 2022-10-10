package com.fal.challengech5.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fal.challengech5.model.ResponseDataUserItem
import com.fal.challengech5.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel : ViewModel() {
    var userData : MutableLiveData<ResponseDataUserItem?>
    var idUser : MutableLiveData<ResponseDataUserItem?>
    var updateUser : MutableLiveData<ResponseDataUserItem?>

    init {
        userData = MutableLiveData()
        idUser = MutableLiveData()
        updateUser = MutableLiveData()
    }

    fun liveUser() : MutableLiveData<ResponseDataUserItem?>{
        return userData
    }

    fun liveUserid() : MutableLiveData<ResponseDataUserItem?>{
        return idUser
    }

    fun liveUpdateUser() : MutableLiveData<ResponseDataUserItem?>{
        return updateUser
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

    fun putUser(id: String, username: String, email: String, password: String){
        ApiClient.instance.putUser(id, ResponseDataUserItem(email, id, password, username))
            .enqueue(object : Callback<ResponseDataUserItem>{
                override fun onResponse(
                    call: Call<ResponseDataUserItem>,
                    response: Response<ResponseDataUserItem>,
                ) {
                    if (response.isSuccessful){
                        updateUser.postValue(response.body())
                    }else{
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseDataUserItem>, t: Throwable) {
                    updateUser.postValue(null)
                }

            })
    }

    fun getUserbyId(id: String){
        ApiClient.instance.getUserId(id)
            .enqueue(object : Callback<ResponseDataUserItem>{
                override fun onResponse(
                    call: Call<ResponseDataUserItem>,
                    response: Response<ResponseDataUserItem>,
                ) {
                    if (response.isSuccessful){
                        idUser.postValue(response.body())
                    }else{
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseDataUserItem>, t: Throwable) {
                    idUser.postValue(null)
                }

            })
    }
}