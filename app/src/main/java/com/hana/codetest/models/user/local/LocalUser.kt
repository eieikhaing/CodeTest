package com.hana.codetest.models.user.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hana.codetest.utilities.USERS_TABLE

@Entity(tableName = USERS_TABLE)
data class LocalUser(
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "website")  val website: String,
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
