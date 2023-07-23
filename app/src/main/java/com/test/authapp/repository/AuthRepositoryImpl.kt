package com.test.authapp.repository

import com.test.authapp.db.roomdb.UserInfoDao
import com.test.authapp.db.roomdb.UserInfoEntity

class AuthRepositoryImpl(private val userInfoDao: UserInfoDao) : AuthRepositoryInterface {
    override suspend fun registerUser(userInfoEntity: UserInfoEntity) {
        userInfoDao.registerUser(userInfoEntity)
    }

    override suspend fun getUserInfo(emailId: String): String? {
        return userInfoDao.getUserInfo(emailId)
    }
}