package com.ibenew.wanandroid.mvvm.models.data

/**
 * Create by wuyt on 2020/1/3 13:54
 * {@link }
 */

data class Token(val token: String)

data class UserType(val type: String)

data class UserInfo(
    val accountId: String,
    val accountLevel: String,
    val accountLevelMeaning: String,
    val accountNumber: String,
    val accountType: String,
    val accountTypeMeaning: String,
    val fullName: String,
    val status: String,
    val telephone: String,
    val type: String,
    val userId: String,
    val userName: String,
    val positionName: String,
    val password: String,
    val vendor: UserVendor
)

data class UserVendor(var barcodeTag: String, var vendorId: String)

data class LoginInfo(
    val employeeId: String,
    val lastLogonDate: String,
    val token: String,
    val imToken: String,
    val urlAfterLogin: String,
    val userId: String,
    val userName: String,
    val customerId: String
)