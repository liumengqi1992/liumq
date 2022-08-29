package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 配置无线网络
 */
class WifiReq(ssid: String, passwd: String, dhcp: Boolean, ip: String? = null, mask: String? = null) : Request(3004, "robot_config_wifi_req") {

    init {
        json = Data(ssid, passwd, dhcp, ip, mask)
    }

    /**
     * 数据区
     * @param ssid Wifi 的名字
     * @param passwd Wifi 密码
     * @param 使用 DHCP
     * @param ip 机器人所在的无线网络网关
     * @param mask 机器人所在的无线网络子网掩码
     *
     * 如果 dhcp 为 true, 那么其他字段都会忽略
     * 如果 dhcp 为 false,那么其他三个字端必须提供
     * 若不符合 ip 规则,怎会返回错误
     */
    class Data(val ssid: String, val passwd: String, val dhcp: Boolean, val ip: String? = null, val mask: String? = null)
}
