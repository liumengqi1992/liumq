package com.deepblue.libraries.arouter

import com.deepblue.arouter.ARouterApplication
import com.deepblue.arouter.LoginInterceptor

class MainApplication : ARouterApplication() {

    override fun onCreate() {
        super.onCreate()
        //设置登录Activity
        LoginInterceptor.loginActivity = LoginActivity::class.java
    }
}