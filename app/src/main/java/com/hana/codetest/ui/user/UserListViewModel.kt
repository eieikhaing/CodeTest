package com.hana.codetest.ui.user

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.hana.codetest.HanaApplication.Companion.context
import com.hana.codetest.base.BaseViewModel
import com.hana.codetest.models.post.PostUser
import com.hana.codetest.models.post.UserRequest
import com.hana.codetest.models.user.User
import com.hana.codetest.repository.DataRepository
import com.hana.codetest.repository.Resource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val dataRepository: DataRepository,
) : BaseViewModel() {
    private val _userListResponse = MutableLiveData<Resource<List<User>>>()
    val userListResponse: LiveData<Resource<List<User>>> get() = _userListResponse
    private val _uploadUserResponse = MutableLiveData<Resource<PostUser>>()
    val uploadUserResponse: LiveData<Resource<PostUser>> get() = _uploadUserResponse


    private val _localUsers = MutableLiveData<List<User>>()
    val localUsers: LiveData<List<User>> get() = _localUsers

    fun callApi() {
        _userListResponse.value = Resource.Loading()
        viewModelScope.launch {
            _userListResponse.value =
                dataRepository.remote.getUsers(
                ) as Resource<List<User>>
        }
    }

    fun insertUsers(usersEntity: List<User>) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.local.insertUsers(usersEntity)
        }
    }

    fun getUsersFromDatabase() {
        viewModelScope.launch {
            val localUsersFromDB = dataRepository.remote.getUsers()
            _localUsers.value = localUsersFromDB.data!!
            Log.d("##Users", localUsersFromDB.data[0].email)
            exportUserDataToJSON(localUsersFromDB.data)
        }
    }

    fun uploadData(userId: String, title: String, body: String) {
        var userRequest = UserRequest(userId, 1, title, body)
        _uploadUserResponse.value = Resource.Loading()
        viewModelScope.launch {
            _uploadUserResponse.value =
                dataRepository.remote.uploadData(
                    userRequest
                ) as Resource<PostUser>
        }
    }
    private fun exportUserDataToJSON(userList: List<User>) {

        val json = Gson().toJson(userList)
        saveJSONToFile(context, json)
    }

    private fun saveJSONToFile(context: Context, json: String) {
        try {
            val file = File(context.filesDir, "user.json")
            file.createNewFile()
            val fileWriter = FileWriter(file)
            fileWriter.write(json)
            fileWriter.close()
            Log.d("##FileLocation",file.absolutePath)
            Toast.makeText(context, "User data exported successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "User data not exported", Toast.LENGTH_SHORT).show()

        }
    }

}