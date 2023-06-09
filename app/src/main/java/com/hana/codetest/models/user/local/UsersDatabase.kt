package com.hana.codetest.models.user.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hana.codetest.models.User.local.UsersTypeConverter
import com.hana.codetest.models.user.User
import com.hana.codetest.models.user.local.Dao.UserDao

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UsersTypeConverter::class)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

