package com.test.authapp.db.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserInfoDao {

    @Insert
    suspend fun registerUser(userInfoEntity: UserInfoEntity)

    @Query("SELECT password FROM UserInfoTable WHERE emailId = :emailId")
    suspend fun getUserInfo(emailId: String): String?
}