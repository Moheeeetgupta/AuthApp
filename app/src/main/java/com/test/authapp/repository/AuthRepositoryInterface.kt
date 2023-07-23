package com.test.authapp.repository

import com.test.authapp.db.roomdb.UserInfoEntity

interface AuthRepositoryInterface {
    suspend fun registerUser(userInfoEntity: UserInfoEntity)
    suspend fun getUserInfo(emailId: String): String?
}