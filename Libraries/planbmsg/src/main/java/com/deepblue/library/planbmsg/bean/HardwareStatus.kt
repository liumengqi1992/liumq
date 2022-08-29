package com.deepblue.library.planbmsg.bean

/**
 * 查询机器人硬件状态（11009）
 */
class HardwareStatus {
    //激光
    var laser_status: Boolean? = null

    //超声波
    var ultrasonic_status: Boolean? = null

    //摄像头
    var camera_status: Boolean? = null

    //陀螺仪
    var gyro_status: Boolean? = null

    //odom
    var odom_status: Boolean? = null

    //电池
    var battery_status: Boolean? = null

    //刷盘电机
    var brush_motor_status: Boolean? = null

    //刷盘升降电机
    var brush_lift_status: Boolean? = null

    //吸水扒升降电机
    var water_lift_status: Boolean? = null

    //吸风电机
    var vaccum_motor_status: Boolean? = null

    //清水泵
    var cleanwater_pump_status: Boolean? = null

    //行走电机
    var walk_motor_status: Boolean? = null

    //污水泵
    var DIRTYWATER_PUMP_status: Boolean? = null
}