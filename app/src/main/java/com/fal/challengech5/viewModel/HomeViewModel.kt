package com.fal.challengech5.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fal.challengech5.model.ResponseDataTaskItem
import com.fal.challengech5.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var allData : MutableLiveData<List<ResponseDataTaskItem>?>
    var updateData : MutableLiveData<ResponseDataTaskItem?>
    var deleteData : MutableLiveData<String?>

    init {
        allData = MutableLiveData()
        updateData = MutableLiveData()
        deleteData = MutableLiveData()
    }

    fun allLiveData() : MutableLiveData<List<ResponseDataTaskItem>?>{
        return allData
    }

    fun updateLiveData() : MutableLiveData<ResponseDataTaskItem?>{
        return updateData
    }

    fun deleteLiveData() : MutableLiveData<String?>{
        return deleteData
    }

    //Retrofit
    fun callAllData(userId: String){
        ApiClient.instance.getTask(userId)
            .enqueue(object : Callback<List<ResponseDataTaskItem>>{
                override fun onResponse(
                    call: Call<List<ResponseDataTaskItem>>,
                    response: Response<List<ResponseDataTaskItem>>,
                ) {
                    if (response.isSuccessful){
                        allData.postValue(response.body())
                    }else{
                        allData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataTaskItem>>, t: Throwable) {
                    allData.postValue(null)
                }

            })
    }

    fun callUpdateData(category : String, content : String, idTask : String, image : String, title : String, userId : String){
        ApiClient.instance.putData(userId, idTask, ResponseDataTaskItem(category, content, idTask, image, title, userId))
            .enqueue(object : Callback<ResponseDataTaskItem>{
                override fun onResponse(
                    call: Call<ResponseDataTaskItem>,
                    response: Response<ResponseDataTaskItem>,
                ) {
                    if (response.isSuccessful){
                        updateData.postValue(response.body())
                    }else{
                        updateData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataTaskItem>, t: Throwable) {
                    updateData.postValue(null)
                }

            })
    }

    fun callDeleteData(userId: String, idTask: String){
        ApiClient.instance.delData(userId, idTask)
            .enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        deleteData.postValue(response.body())
                    }else{
                        deleteData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    deleteData.postValue(null)
                }

            })
    }
}