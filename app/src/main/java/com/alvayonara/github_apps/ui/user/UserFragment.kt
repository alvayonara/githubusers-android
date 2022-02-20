package com.alvayonara.github_apps.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.github_apps.GitHubApplication
import com.alvayonara.github_apps.R
import com.alvayonara.github_apps.core.base.BaseFragment
import com.alvayonara.github_apps.core.ui.user.UserController
import com.alvayonara.github_apps.core.utils.*
import com.alvayonara.github_apps.core.data.source.Result
import com.alvayonara.github_apps.databinding.FragmentUserBinding
import com.alvayonara.github_apps.ui.ViewModelFactory
import javax.inject.Inject

class UserFragment : BaseFragment<FragmentUserBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val userViewModel: UserViewModel by viewModels { factory }

    @Inject
    lateinit var userController: UserController

    private var _currentPage = Constant.Services.FIRST_PAGE
    private var _username = ""
    private var _isScrolled = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserBinding
        get() = FragmentUserBinding::inflate

    override fun inject() {
        (requireActivity().application as GitHubApplication).appComponent.inject(this)
    }

    override fun setup() {
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupView() {
        binding.srUser.setOnRefreshListener {
            binding.srUser.hideLoading()
            resetPage()
            userViewModel.getUsers()
        }
    }

    override fun setupRecyclerView() {
        userController.onUserClickCallback = {
            _username = it
            userViewModel.setUsername(it)
        }

        binding.rvUser.apply {
            addOnScrollListener(object : RecyclerViewLoadMore() {
                override fun onLoadMore() {
                    /**
                     * Check if fetch data success then increment current page.
                     * So it will prevent increment page when fetch data failed.
                     */
                    if (!_isScrolled) {
                        _isScrolled = true
                        _currentPage += 10
                    }
                    loadMoreState(true)
                    userViewModel.setNextPage(_currentPage)
                }
            })
            layoutManager = LinearLayoutManager(requireActivity())
            setController(userController)
        }
    }

    override fun subscribeViewModel() {
        userViewModel.apply {
            users.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Result.Status.LOADING -> showLoading(true)
                    Result.Status.SUCCESS -> {
                        showLoading(false)
                        result.body?.let { userController.setUsers(it.toMutableList()) }
                    }
                    Result.Status.ERROR -> {
                        showLoading(false)
                        result.throwable?.let { throwable ->
                            binding.sbUser.showErrorSnackbar(
                                text = getString(R.string.txt_snackbar, throwable.getThrowable()),
                                onRetry = {
                                    userViewModel.getUsers()
                                }
                            )
                        }
                    }
                }
            }

            usersNextPage.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Result.Status.LOADING -> Unit
                    Result.Status.SUCCESS -> {
                        _isScrolled = false
                        result.body?.let {
                            if (it.isEmpty()) loadMoreState(false)
                            userController.addUsers(it.toMutableList())
                        }
                    }
                    Result.Status.ERROR -> {
                        loadMoreState(false)
                        result.throwable?.let { throwable ->
                            binding.sbUser.showErrorSnackbar(
                                text = getString(R.string.txt_snackbar, throwable.getThrowable()),
                                onRetry = {
                                    userViewModel.setNextPage(_currentPage)
                                }
                            )
                        }
                    }
                }
            }

            profile.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Result.Status.LOADING -> showLoading(true)
                    Result.Status.SUCCESS -> {
                        showLoading(false)
                        result.body?.let {
                            requireActivity().showToast(
                                String.format(
                                    getString(R.string.txt_profile),
                                    it.name,
                                    it.login,
                                    it.email,
                                    it.createdAt.dateTimeConvert(
                                        Constant.DateFormat.FORMAT_DATE_TIMEZONE,
                                        Constant.DateFormat.FORMAT_DATE_MMM_DD_YYYY
                                    )
                                )
                            )
                        }
                    }
                    Result.Status.ERROR -> {
                        showLoading(false)
                        result.throwable?.let { throwable ->
                            binding.sbUser.showErrorSnackbar(
                                text = getString(R.string.txt_snackbar, throwable.getThrowable()),
                                onRetry = {
                                    userViewModel.setUsername(_username)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbUser.visible()
        } else {
            binding.pbUser.gone()
        }
    }

    private fun resetPage() {
        _currentPage = Constant.Services.FIRST_PAGE
    }

    private fun loadMoreState(isLoadMore: Boolean) {
        userController.setIsLoadMore(isLoadMore)
    }
}