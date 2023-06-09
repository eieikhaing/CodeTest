package com.hana.codetest.models.user.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hana.codetest.models.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserList(userEntity: List<User>)

    @Query("Select * From users_table")
    fun readUsers(): Flow<List<User>>
}