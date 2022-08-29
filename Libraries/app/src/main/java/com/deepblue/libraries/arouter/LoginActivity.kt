package com.deepblue.libraries.arouter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.deepblue.arouter.LoginInterceptor
import com.deepblue.libraries.R
import kotlinx.android.synthetic.main.arouter_activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arouter_activity_login)

        btnLogin.setOnClickListener {
            LoginInterceptor.loginSuccess(this)
        }
    }

    override fun onBackPressed() {
    }
}