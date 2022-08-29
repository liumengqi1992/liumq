package com.deepblue.library.robotmsg.control

import android.text.TextUtils
import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.Route

class GetPathRes : Response() {
    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var data = ArrayList<Route>()
        var task_name: String = ""
        var task_label: String = ""
            get() {
                if (TextUtils.isEmpty(field)) {
                    return task_name
                }
                return field
            }
    }
}