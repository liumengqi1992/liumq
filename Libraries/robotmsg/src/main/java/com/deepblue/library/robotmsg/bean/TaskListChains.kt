package com.deepblue.library.robotmsg.bean

class TaskListChains {

    var task_chain_name: String = ""//任务链的名字,唯一确定任务链
    var map_name: String = ""//任务链地图名字,任务链中的任务必须在同一副地图中
    var task_chain = ArrayList<Task>()//任务链内容

    class Task {
        var task_name: String = ""
        var task_type: Int = 0
        var task_element = ArrayList<String>()
        var task_labels = ArrayList<String>()
    }


}