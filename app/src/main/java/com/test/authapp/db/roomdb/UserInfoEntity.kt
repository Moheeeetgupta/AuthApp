package com.test.authapp.db.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfoTable")
data class UserInfoEntity(
    // Email id is used to uniquely identify each user
    @PrimaryKey
    val emailId: String,

    val userName: String,
    val countryName: String,
    val password: String
)
