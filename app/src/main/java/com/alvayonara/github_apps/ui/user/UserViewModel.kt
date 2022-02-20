package com.alvayonara.github_apps.ui.user

import androidx.lifecycle.*
import com.alvayonara.github_apps.core.data.source.Result
import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User
import com.alvayonara.github_apps.core.domain.usecase.UserUseCase
import com.alvayonara.github_apps.core.utils.Constant
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _users: MutableLiveData<Result<List<User>>> = MutableLiveData()
    val users: LiveData<Result<List<User>>> get() = _users

    private val _nextPage: MutableLiveData<Int> = MutableLiveData()
    private val _username: MutableLiveData<String> = MutableLiveData()

    init {
        getUsers()
    }

    fun setNextPage(nextPage: Int) {
        _nextPage.value = nextPage
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun getUsers() {
        viewModelScope.launch {
            _users.postValue(Result.loading())
            try {
                _users.postValue(Result.success(userUseCase.getUsers(Constant.Services.FIRST_PAGE)))
            } catch (throwable: Throwable) {
                _users.postValue(Result.error(throwable))
            }
        }
    }

    val usersNextPage: LiveData<Result<List<User>>> = _nextPage.switchMap {
        liveData {
            try {
                emit(Result.success(userUseCase.getUsers(it)))
            } catch (throwable: Throwable) {
                emit(Result.error(throwable))
            }
        }
    }

    val profile: LiveData<Result<Profile>> = _username.switchMap {
        liveData {
            emit(Result.loading())
            try {
                emit(Result.success(userUseCase.getProfile(it)))
            } catch (throwable: Throwable) {
                emit(Result.error(throwable))
            }
        }
    }
}