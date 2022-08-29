package com.deepblue.library.planbmsg.bean

/**
 * 查询机器人状态（11002）
 */
class RobotBattery {

    //电池电量，范围[0,100]
    var battery_level = 0
    //电池温度，单位°
    var battery_temp = 0
    //电池是否在充电
    var charging = false
    //电压
    var voltage = 0.0
    //电流
    var current = 0.0
}