package com.deepblue.library.robotmsg.bean

class TaskList {
    var task_chain_name: String = ""
    var map_name: String = ""
    var task_chain = ArrayList<Taskchain>()

    class Taskchain {
        var task_type: Int = 0
        var task_name: String = ""
        var task_element = ArrayList<String>()
    }
}