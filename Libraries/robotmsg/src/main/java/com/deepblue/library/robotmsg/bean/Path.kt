package com.deepblue.library.robotmsg.bean

class Path {

    var action: String = ""
    var nav_type: String? = null
    var type: String = ""
    var poses = ArrayList<Pos>()

    class Pos {
        var theta: Double = 0.0
        var x: Double = 0.0
        var y: Double = 0.0
    }
}