package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SetLevelReq(to_back:Int,to_alarm:Int,to_work:Int,to_ota:Int,to_mid:Int,to_min:Int,to_max:Int,to_water:Int,to_wind:Int): Request(2016, "通用任务参数设置") {

    init {
        json = Data(to_back,to_alarm,to_work,to_ota,to_mid,to_min,to_max,to_water,to_wind)
    }

    /**
     * 数据区
     * @param serial_number 序列号
     */
    class Data(val min_battery_level_to_back: Int,val min_battery_level_to_alarm: Int,val min_battery_level_to_work: Int,val min_battery_level_to_ota: Int,
              val normal_water_motor_level:Int, val saving_water_motor_level:Int, val deep_water_motor_level:Int, val water_motor_level:Int, val wind_motor_level:Int)
}