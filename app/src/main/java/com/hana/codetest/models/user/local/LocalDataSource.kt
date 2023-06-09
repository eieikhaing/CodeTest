package com.hana.codetest.models.user.local

import com.hana.codetest.models.user.User
import com.hana.codetest.models.user.local.Dao.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao
){
    suspend fun insertUsers(userEntity: List<User>){
        userDao.insertUserList(userEntity)
    }

    fun readUsers() : Flow<List<User>> {
        return userDao.readUsers()
    }

}