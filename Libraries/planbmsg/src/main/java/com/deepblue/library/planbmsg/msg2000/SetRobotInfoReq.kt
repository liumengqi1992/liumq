package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SetRobotInfoReq : Request(2020, "设置机器人信息") {

    companion object {
        const val RENAME = 1
    }

    fun rename(name: String): SetRobotInfoReq {
        number = RENAME
        json = Data()
        (json as Data).name = name
        return this
    }
    
    fun setData(data:Data): SetRobotInfoReq{
        json=data
        return this
    }

    class Data {
        var type: String? = null//机器人ID，2：洗地机，6：清洗消毒机器人
        var name: String? = null//机器人名称
        var model: String? = null//机器型号
        var software_version: String? = null//软件版本
        var birthday: String? = null//出厂日期
        var activation_time: String? = null//激活时间
        var runup_time: String? = null//最后启动时间
        var last_user: String? = null//最近登录用户
        var uuid: String? = null//uuid
        var serial_number: String? = null//序列号
    }
}