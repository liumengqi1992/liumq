package com.deepblue.libraries

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import com.deepblue.libraries.xml.FtpSetting
import com.deepblue.libraries.xml.RobotServerSetting
import com.deepblue.library.xml.XMLUtils
import com.socks.library.KLog

class XMLActivity : AppCompatActivity() {

    private val xml = "<?xml version='1.0' encoding='gb2312'?>\n" +
            "<ftpSetting>\n" +
            "    <!--机器人服务器信息-->\n" +
            "    <robotServerSetting>\n" +
            "        <!--服务器IP-->\n" +
            "        <serverIP>192.168.16.92</serverIP>\n" +
            "        <!--服务器端口号-->\n" +
            "        <serverPort>20037</serverPort>\n" +
            "        <!--心跳时间/s-->\n" +
            "        <heartTime>6</heartTime>\n" +
            "    </robotServerSetting>\n" +
            "\n" +
            "</ftpSetting>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml)

        val names = arrayOf("ftpSetting", "robotServerSetting")
        val types = arrayOf(FtpSetting::class.java, RobotServerSetting::class.java)
        val objXML = XMLUtils.fromXML(xml, FtpSetting(), names, types)

        val robotServerSetting = RobotServerSetting()
        robotServerSetting.serverIP = "192.168.16.92"
        robotServerSetting.serverPort = 20037
        robotServerSetting.heartTime = 6
        val ftpSetting = FtpSetting()
        ftpSetting.robotServerSetting = robotServerSetting
        val xml = XMLUtils.toXML(ftpSetting, "ftpSetting", FtpSetting::class.java)
        KLog.d("xml", xml)
        KLog.xml("xml", xml)
        KLog.d("base64", Base64.encodeToString(xml.toByteArray(), Base64.NO_WRAP))
    }
}