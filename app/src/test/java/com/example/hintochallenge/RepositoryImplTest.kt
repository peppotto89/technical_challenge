package com.example.hintochallenge

import com.example.hintochallenge.data.api.ApiService
import com.example.hintochallenge.data.model.PostDto
import com.example.hintochallenge.data.repositories.RepositoryImpl
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RepositoryImplTest {

    @Mock
    lateinit var mockApiService: ApiService

    @InjectMocks
    private lateinit var repository: RepositoryImpl

    @Before
    fun setup() {
        mockApiService = mock(ApiService::class.java)
        repository = RepositoryImpl(mockApiService)
    }

    @Test
    fun `test fetchData success`() = runTest {

        val post = PostDto(userId = 1, id = 1, title = "title", body = "body")
        val post2 = PostDto(userId = 2, id = 2, title = "title2", body = "body2")
        val mockResponse = Response.success(listOf(post,post2))
        `when`(mockApiService.getPosts()).thenReturn(mockResponse)

        val result = repository.getPosts()

        assertTrue(result.isSuccess)
        val posts = result.getOrNull()
        assertNotNull(posts)
        assertTrue(posts!!.isNotEmpty())
    }

    @Test
    fun `test fetchData failure`() = runTest {

        val errorResponse = Response.error<List<PostDto>>(404, ResponseBody.create(null, "Not Found"))

        `when`(mockApiService.getPosts()).thenReturn(errorResponse)

        val result = repository.getPosts()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)
        assertTrue(result.exceptionOrNull()?.message?.contains("API error") ?: false)
    }

    @Test
    fun `test fetchData network exception`() = runTest {

        `when`(mockApiService.getPosts()).thenThrow(RuntimeException("Network Error"))

        val result = repository.getPosts()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is RuntimeException)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }
}