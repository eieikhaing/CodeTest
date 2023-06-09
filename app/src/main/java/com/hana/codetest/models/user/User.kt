package com.hana.codetest.models.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hana.codetest.utilities.USERS_TABLE
import java.io.Serializable

@Entity(tableName = USERS_TABLE)
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
   // val address: Address,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "movie_type")  val website: String,
    //val company: Company
) : Serializable {
    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: Geo
    )

    data class Geo(
        val lat: String,
        val lng: String
    )

    data class Company(
        val name: String,
        val catchPhrase: String,
        val bs: String
    )
}