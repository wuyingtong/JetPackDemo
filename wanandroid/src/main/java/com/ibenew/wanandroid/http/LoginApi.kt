package com.ibenew.wanandroid.http

import androidx.lifecycle.LiveData
import com.ibenew.wanandroid.mvvm.models.data.LoginInfo
import com.ibenew.wanandroid.mvvm.models.data.Token
import com.ibenew.wanandroid.mvvm.models.data.UserType
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Create by wuyt on 2020/1/3 14:24
 * {@link }
 */
interface LoginApi {
    companion object {
        const val TOKEN_URL = "eihView/resources/eih/login/token"//获取初始token
        const val USER_TYPE_URL = "eihView/resources/eih/login/getUserByUserName"//查询账户类型
        const val USER_LOGIN_URL = "eihView/resources/eih/login/mobile"//登录接口(平台端)
        const val USER_SRM_LOGIN_URL = "srmView/resources/srm/login/mobile"//登录接口(供应商端)
        const val USER_ACCOUNTS_URL = "eihView/resources/eih/accounts"//用户信息(平台端)
        const val USER_SRM_ACCOUNTS_URL = "srmView/resources/srm/SrmAccounts"//用户信息(供应商端)
        const val USER_APP_VERSION_URL = "eihView/resources/xsa/mdmApplication/search"//版本更新
        const val USER_LOGIN_OUT_URL = "eihView/resources/eih/login"//退出登录
        const val USER_FILE_UPLOAD_URL = "eihView/resources/xsa/file"//附件上传
        const val USER_FILE_DELETE_URL = "eihView/resources/xsa/documents"//附件删除
        const val USER_ACCESS_MENU_LIST_URL = "eihView/resources/xsa/application/userAccess"//用户菜单
    }


    @GET(TOKEN_URL)
    fun token(): LiveData<BaseResponse<Token>>

    @GET(USER_TYPE_URL)
    fun getUserType(@Query("keyword") userName: String): LiveData<BaseResponse<UserType>>

    @POST(USER_LOGIN_URL)
    fun login(@Header("sign") sign: String,
              @Header("token") token: String,
              @Body body: RequestBody): LiveData<BaseResponse<LoginInfo>>
}