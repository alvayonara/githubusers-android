package com.alvayonara.github_apps.core.repository

import com.alvayonara.github_apps.core.data.source.UserRepository
import com.alvayonara.github_apps.core.data.source.remote.UserRemoteSource
import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User
import com.alvayonara.github_apps.core.utils.DataDummy
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private val userRemoteSource: UserRemoteSource = mockk()
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepository(userRemoteSource)
    }

    /**
     * Get list users
     */
    @Test
    fun `given success response then return list of users`() = runBlocking {
        // given
        val nextPage = 10
        val fakeResponse = DataDummy.getUserResponses()
        val expectedUser = DataDummy.getUser()
        coEvery { userRemoteSource.getUsers(nextPage) } returns fakeResponse

        // when
        val actual = userRepository.getUsers(nextPage)

        // then
        assertEquals(expectedUser, actual)
    }

    @Test(expected = IOException::class)
    fun `given network is error when get list of users then throw IOException`() = runTest {
        // given
        val nextPage = 10
        coEvery { userRemoteSource.getUsers(nextPage) } throws IOException()

        // when
        userRepository.getUsers(nextPage)
    }

    @Test(expected = HttpException::class)
    fun `given unauthorized network when get list of users then throw HttpException`() = runTest {
        // given
        val nextPage = 10
        val responseUnauthorized =
            Response.error<List<User>>(HttpURLConnection.HTTP_UNAUTHORIZED, mockk(relaxed = true))
        coEvery { userRemoteSource.getUsers(nextPage) } throws HttpException(responseUnauthorized)

        // when
        userRepository.getUsers(nextPage)
    }

    @Test(expected = HttpException::class)
    fun `given forbidden network when get list of users then throw HttpException`() = runTest {
        // given
        val nextPage = 10
        val responseForbidden =
            Response.error<List<User>>(HttpURLConnection.HTTP_FORBIDDEN, mockk(relaxed = true))
        coEvery { userRemoteSource.getUsers(nextPage) } throws HttpException(responseForbidden)

        // when
        userRepository.getUsers(nextPage)
    }

    /**
     * Get profile
     */
    @Test
    fun `given success response then return profile`() = runBlocking {
        // given
        val username = DataDummy.getProfile().login
        val fakeResponse = DataDummy.getProfileResponses()
        val expectedProfile = DataDummy.getProfile()
        coEvery { userRemoteSource.getProfile(username) } returns fakeResponse

        // when
        val actual = userRepository.getProfile(username)

        // then
        assertEquals(expectedProfile, actual)
    }

    @Test(expected = IOException::class)
    fun `given network is error when get profile then throw IOException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        coEvery { userRemoteSource.getProfile(username) } throws IOException()

        // when
        userRepository.getProfile(username)
    }

    @Test(expected = HttpException::class)
    fun `given unauthorized network when get profile then throw HttpException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        val responseUnauthorized =
            Response.error<Profile>(HttpURLConnection.HTTP_UNAUTHORIZED, mockk(relaxed = true))
        coEvery { userRemoteSource.getProfile(username) } throws HttpException(responseUnauthorized)

        // when
        userRepository.getProfile(username)
    }

    @Test(expected = HttpException::class)
    fun `given forbidden network when get profile then throw HttpException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        val responseForbidden =
            Response.error<Profile>(HttpURLConnection.HTTP_FORBIDDEN, mockk(relaxed = true))
        coEvery { userRemoteSource.getProfile(username) } throws HttpException(responseForbidden)

        // when
        userRepository.getProfile(username)
    }
}