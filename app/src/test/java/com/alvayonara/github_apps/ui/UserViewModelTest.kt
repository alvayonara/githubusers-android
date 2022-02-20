package com.alvayonara.github_apps.ui

import androidx.lifecycle.Observer
import com.alvayonara.github_apps.core.data.source.Result
import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User
import com.alvayonara.github_apps.core.domain.usecase.UserUseCase
import com.alvayonara.github_apps.core.utils.DataDummy
import com.alvayonara.github_apps.ui.user.UserViewModel
import com.alvayonara.github_apps.utils.BaseUnitTest
import com.alvayonara.github_apps.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class UserViewModelTest : BaseUnitTest() {

    private val userUseCase: UserUseCase = mockk()
    private lateinit var userViewModel: UserViewModel

    private val observerUsers: Observer<Result<List<User>>> = mockk(relaxUnitFun = true)
    private val observerProfile: Observer<Result<Profile>> = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        userViewModel = UserViewModel(userUseCase)
        userViewModel.users.observeForever(observerUsers)
        userViewModel.profile.observeForever(observerProfile)
    }

    @After
    fun finish() {
        userViewModel.users.removeObserver(observerUsers)
        userViewModel.profile.removeObserver(observerProfile)
    }

    /**
     * Get list users
     */
    @Test
    fun `given success response when get list of users should return result`() {
        // given
        val userResponse = DataDummy.getUser()
        coEvery { userUseCase.getUsers(any()) } returns userResponse

        // when
        userViewModel.getUsers()

        // then
        assertNotNull(userViewModel.users.getOrAwaitValue())
        assertEquals(Result.success(userResponse), userViewModel.users.getOrAwaitValue())
    }

    @Test
    fun `given throwable when get list of users`() {
        // given
        coEvery { userUseCase.getUsers(any()) } throws IOException()

        // when
        userViewModel.getUsers()

        // then
        assertNotNull(userViewModel.users.getOrAwaitValue())
    }

    /**
     * Get next page list users
     */
    @Test
    fun `given success response when get next page list of users should emit result`() {
        // given
        val userResponse = DataDummy.getUser()
        coEvery { userUseCase.getUsers(any()) } returns userResponse

        // when
        userViewModel.setNextPage(10)

        // then
        assertNotNull(userViewModel.usersNextPage.getOrAwaitValue())
        assertEquals(Result.success(userResponse), userViewModel.usersNextPage.getOrAwaitValue())
    }

    @Test
    fun `given throwable when get next page list of users`() {
        // given
        coEvery { userUseCase.getUsers(any()) } throws IOException()

        // when
        userViewModel.setNextPage(10)

        // then
        assertNotNull(userViewModel.usersNextPage.getOrAwaitValue())
    }

    /**
     * Get profile
     */
    @Test
    fun `given success response when get profile should emit result`() {
        // given
        val profileResponse = DataDummy.getProfile()
        coEvery { userUseCase.getProfile(any()) } returns profileResponse

        // when
        userViewModel.setUsername(profileResponse.login)

        // then
        assertNotNull(userViewModel.profile.getOrAwaitValue())
        assertEquals(Result.success(profileResponse), userViewModel.profile.getOrAwaitValue())
    }

    @Test
    fun `given throwable when get profile`() {
        // given
        coEvery { userUseCase.getProfile(any()) } throws IOException()

        // when
        userViewModel.setUsername(DataDummy.getProfile().login)

        // then
        assertNotNull(userViewModel.profile.getOrAwaitValue())
    }
}