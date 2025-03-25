package com.example.hintochallenge

import com.example.hintochallenge.data.dao.PostDao
import com.example.hintochallenge.data.model.PostEntity
import com.example.hintochallenge.data.repositories.RepositoryDbImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RepositoryDbImplTest {

    @Mock
    private lateinit var postDao: PostDao

    private lateinit var repository: RepositoryDbImpl

    @Before
    fun setup() {
        postDao = mock(PostDao::class.java)
        repository = RepositoryDbImpl(postDao)
    }

    @Test
    fun `getPostsFromDb on success`() = runTest {
        val mockPosts = listOf(PostEntity(1, 1, "Title", "Body"))
        `when`(postDao.getAllPosts()).thenReturn(mockPosts)

        val result = repository.getAllPosts()

        assertTrue(result.isSuccess)
        val posts = result.getOrNull()
        assertNotNull(posts)
        assertTrue(posts!!.isNotEmpty())
    }

    @Test
    fun `getPostsFromDb on failure`() = runTest {
        `when`(postDao.getAllPosts()).thenReturn(emptyList())

        val result = repository.getAllPosts()

        assertTrue(result.isFailure)
        assertEquals("No posts found in DB", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getPostsFromDb on exception`() = runTest {
        `when`(postDao.getAllPosts()).thenThrow(RuntimeException("DB error"))

        val result = repository.getAllPosts()

        assertTrue(result.isFailure)
        assertEquals("DB error", result.exceptionOrNull()?.message)
    }
}