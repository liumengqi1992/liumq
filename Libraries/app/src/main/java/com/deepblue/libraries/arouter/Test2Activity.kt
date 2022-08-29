package com.deepblue.libraries.arouter

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.deepblue.arouter.NeedLoginBaseActivity
import com.deepblue.libraries.R
import kotlinx.android.synthetic.main.arouter_activity_test2.*

@Route(path = Const.PATH_TEST2)
class Test2Activity : NeedLoginBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arouter_activity_test2)

        btnGotoTest3.setOnClickListener {
            ARouter.getInstance().build(Const.PATH_TEST3).navigation()
        }
    }
}