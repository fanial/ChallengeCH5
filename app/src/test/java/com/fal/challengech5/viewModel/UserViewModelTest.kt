package com.fal.challengech5.viewModel

import com.fal.challengech5.model.ResponseDataUserItem
import com.fal.challengech5.network.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.http.Body

class UserViewModelTest {

    lateinit var service: ApiService
    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getUserData(): Unit = runBlocking {
        val resAllUser = mockk<Call<List<ResponseDataUserItem>>>()
        every {
            service.getUser()
        }returns resAllUser

        val result = service.getUser()

        verify {
            service.getUser()
        }
        assertEquals(result, resAllUser)
    }

    @Test
    fun postUserData(): Unit = runBlocking {
        val resPostUser = mockk<Call<ResponseDataUserItem>>()

        every {
            service.postUser(user = ResponseDataUserItem(
                "abc@gmail,",
                "1",
                "abc123",
                "qwerty"
            ))
        }returns resPostUser

        val result = service.postUser(user = ResponseDataUserItem(
            "abc@gmail,",
            "1",
            "abc123",
            "qwerty"
        ))
        verify {
            service.postUser(user = ResponseDataUserItem(
                "abc@gmail,",
                "1",
                "abc123",
                "qwerty"
            ))
        }
        assertEquals(result, resPostUser)
    }
}