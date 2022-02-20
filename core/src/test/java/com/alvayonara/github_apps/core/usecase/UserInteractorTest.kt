package com.alvayonara.github_apps.core.usecase

import com.alvayonara.github_apps.core.data.source.UserRepository
import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User
import com.alvayonara.github_apps.core.domain.usecase.UserInteractor
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
class UserInteractorTest {

    private val userRepository: UserRepository = mockk()
    private lateinit var userInteractor: UserInteractor

    @Before
    fun setUp() {
        userInteractor = UserInteractor(userRepository)
    }

    /**
     * Get list users
     */
    @Test
    fun `given success response then return list of users`() = runBlocking {
        // given
        val nextPage = 10
        val users = DataDummy.getUser()
        coEvery { userRepository.getUsers(nextPage) } returns users

        // when
        val actual = userInteractor.getUsers(nextPage)

        // then
        assertEquals(users, actual)
    }

    @Test(expected = IOException::class)
    fun `given network is error when get list of users then throw IOException`() = runTest {
        // given
        val nextPage = 10
        coEvery { userRepository.getUsers(nextPage) } throws IOException()

        // when
        userInteractor.getUsers(nextPage)
    }

    @Test(expected = HttpException::class)
    fun `given unauthorized network when get list of users then throw HttpException`() = runTest {
        // given
        val nextPage = 10
        val responseUnauthorized =
            Response.error<List<User>>(HttpURLConnection.HTTP_UNAUTHORIZED, mockk(relaxed = true))
        coEvery { userRepository.getUsers(nextPage) } throws HttpException(responseUnauthorized)

        // when
        userInteractor.getUsers(nextPage)
    }

    @Test(expected = HttpException::class)
    fun `given forbidden network when get list of users then throw HttpException`() = runTest {
        // given
        val nextPage = 10
        val responseForbidden =
            Response.error<List<User>>(HttpURLConnection.HTTP_FORBIDDEN, mockk(relaxed = true))
        coEvery { userRepository.getUsers(nextPage) } throws HttpException(responseForbidden)

        // when
        userInteractor.getUsers(nextPage)
    }

    /**
     * Get profile
     */
    @Test
    fun `given success response then return profile`() = runBlocking {
        // given
        val username = DataDummy.getProfile().login
        val profile = DataDummy.getProfile()
        coEvery { userRepository.getProfile(username) } returns profile

        // when
        val actual = userInteractor.getProfile(username)

        // then
        assertEquals(profile, actual)
    }

    @Test(expected = IOException::class)
    fun `given network is error when get profile then throw IOException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        coEvery { userRepository.getProfile(username) } throws IOException()

        // when
        userInteractor.getProfile(username)
    }

    @Test(expected = HttpException::class)
    fun `given unauthorized network when get profile then throw HttpException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        val responseUnauthorized =
            Response.error<Profile>(HttpURLConnection.HTTP_UNAUTHORIZED, mockk(relaxed = true))
        coEvery { userRepository.getProfile(username) } throws HttpException(responseUnauthorized)

        // when
        userInteractor.getProfile(username)
    }

    @Test(expected = HttpException::class)
    fun `given forbidden network when get profile then throw HttpException`() = runTest {
        // given
        val username = DataDummy.getProfile().login
        val responseForbidden =
            Response.error<Profile>(HttpURLConnection.HTTP_FORBIDDEN, mockk(relaxed = true))
        coEvery { userRepository.getProfile(username) } throws HttpException(responseForbidden)

        // when
        userInteractor.getProfile(username)
    }
}