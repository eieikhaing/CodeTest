package com.hana.codetest.models.User.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hana.codetest.models.user.User



class UsersTypeConverter {

    @TypeConverter
    fun fromString(value: String): List<User> {
        val listType = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(Users: List<User>): String {
        val gson = Gson()
        return gson.toJson(Users)
    }

}