package com.deepblue.libraries.arouter

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.deepblue.arouter.LoginInterceptor
import com.deepblue.arouter.NeedLoginBaseActivity
import com.deepblue.libraries.R
import kotlinx.android.synthetic.main.arouter_activity_test3.*

@Route(path = Const.PATH_TEST3)
class Test3Activity : NeedLoginBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arouter_activity_test3)

        btnLogout.setOnClickListener {
            LoginInterceptor.isLogin = false
            finish()
        }
    }
}