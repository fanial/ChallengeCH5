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
    var updateData : MutableLiveData<List<ResponseDataTaskItem>?>
    var deleteData : MutableLiveData<String?>

    init {
        allData = MutableLiveData()
        updateData = MutableLiveData()
        deleteData = MutableLiveData()
    }

    fun allLiveData() : MutableLiveData<List<ResponseDataTaskItem>?>{
        return allData
    }

    fun updateLiveData() : MutableLiveData<List<ResponseDataTaskItem>?>{
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

    fun callUpdateData(userId : String, idTask : String, category : String, content : String, image : String, title : String){
        ApiClient.instance.putData(userId, idTask, ResponseDataTaskItem(category, content, idTask, image, title, userId))
            .enqueue(object : Callback<List<ResponseDataTaskItem>>{
                override fun onResponse(
                    call: Call<List<ResponseDataTaskItem>>,
                    response: Response<List<ResponseDataTaskItem>>,
                ) {
                    if (response.isSuccessful){
                        updateData.postValue(response.body())
                    }else{
                        updateData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataTaskItem>>, t: Throwable) {
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