package com.deepblue.library.robotmsg.bean

class RobotStatus {
    var battery: Boolean = false
    var battery_lifetime: Int = 0
    var battery_usedtime: Int = 0
    var camera: Boolean = false
    var gps: Boolean = false
    var imu: Boolean = false
    var infrared: Boolean = false //红外
    var laser: Boolean = false  //激光
    var motor: Boolean = false  // 电机
    var robot_lifetime: Int = 0
    var robot_usedtime: Int = 0
    var ultrasonic: Boolean = false //超声
}