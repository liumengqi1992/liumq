package com.deepblue.library.robotmsg.bean

class User {

    companion object {
        const val OperatorUser = 3
        const val ManagerUser = 2
        const val AdminUser = 1
        const val RootUser = 0
    }

    var name: String = ""
    var user_type: Int = OperatorUser
}