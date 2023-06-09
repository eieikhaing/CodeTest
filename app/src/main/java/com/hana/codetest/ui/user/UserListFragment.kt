package com.hana.codetest.ui.user

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hana.codetest.R
import com.hana.codetest.base.BaseFragment
import com.hana.codetest.databinding.DialogUploadUserBinding
import com.hana.codetest.databinding.FragmentUserListBinding
import com.hana.codetest.models.post.PostUser
import com.hana.codetest.models.user.User
import com.hana.codetest.navigator.Screens
import com.hana.codetest.repository.Resource
import com.hana.codetest.utilities.provideNavigator
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

@AndroidEntryPoint
class UserListFragment : BaseFragment() {

    private val userListViewModel: UserListViewModel by lazy { ViewModelProvider(this)[UserListViewModel::class.java] }

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListAdapter: UserListAdapter

    override fun observeViewModel() {
        userListViewModel.userListResponse.observe(this, ::handleUserList)
        userListViewModel.localUsers.observe(this, ::handleLocalUsers)
        userListViewModel.uploadUserResponse.observe(this, ::handleUploadUser)
    }

    private fun handleUploadUser(serverResponse: Resource<PostUser>?) {
        when (serverResponse) {
            is Resource.Loading ->
                loadingDialog.show()

            is Resource.Success -> {
                serverResponse.data?.let {
                    loadingDialog.dismiss()
                    Toast.makeText(context, "User Data Uploaded!", Toast.LENGTH_SHORT).show()
                }
            }

            is Resource.DataError -> {
                loadingDialog.dismiss()
            }

            else -> {}
        }
    }

    private fun handleLocalUsers(users: List<User>?) {
        if (users != null) {
            userListAdapter.setData(users)
            navigateDetail()
        }
    }

    private fun navigateDetail() {
        userListAdapter.onItemClick = { user ->
            val bundle = Bundle()
            bundle.putSerializable("user", user)
            requireView().provideNavigator()
                .navigateTo(Screens.USER_LIST_TO_DETAIL, bundle)
        }
    }

    private fun handleUserList(serverResponse: Resource<List<User>>?) {
        when (serverResponse) {
            is Resource.Loading ->
                loadingDialog.show()

            is Resource.Success -> {
                serverResponse.data?.let {
                    loadingDialog.dismiss()
                    offlineCacheUsers(it)
                }
            }

            is Resource.DataError -> {
                loadingDialog.dismiss()
            }

            else -> {}
        }
    }

    private fun offlineCacheUsers(users: List<User>) {
        insertUser(users)
    }

    private fun insertUser(usersEntity: List<User>) {
        userListViewModel.insertUsers(usersEntity)
        userListViewModel.getUsersFromDatabase()

    }

    override fun initViewBinding() {
        binding = FragmentUserListBinding.inflate(layoutInflater)
        binding.viewModel = userListViewModel
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        userListViewModel.callApi()
        userListAdapter = UserListAdapter()
        binding.rvUserList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.fabUpload.setOnClickListener {
            showUploadUserDialog()
        }
        binding.btnGenerateJson.setOnClickListener {
            generateJsonFromFile("/data/user/0/com.hana.codetest/files/user.json")
        }

        return binding.root

    }

    private fun generateJsonFromFile(filePath: String): String? {
        try {
            val file = File(filePath)
            if (!file.exists()) {
                println("File not found at path: $filePath")
                return null
            }

            val bufferedReader = BufferedReader(InputStreamReader(file.inputStream()))
            val stringBuilder = StringBuilder()
            var line: String?

            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            bufferedReader.close()

            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    private fun showUploadUserDialog() {
        val dialogBinding = DialogUploadUserBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
        val dialog = builder.show()
        dialog.setCancelable(false)
        dialogBinding.apply {
            btnUpdate.setOnClickListener {
                if (!TextUtils.isEmpty(etUserId.text.toString()) && !TextUtils.isEmpty(etTitle.text.toString()) && !TextUtils.isEmpty(
                        etBody.text.toString()
                    )
                ) {
                    dialog.dismiss()
                    userListViewModel.uploadData(
                        etUserId.text.toString(),
                        etTitle.text.toString(),
                        etBody.text.toString()
                    )
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}