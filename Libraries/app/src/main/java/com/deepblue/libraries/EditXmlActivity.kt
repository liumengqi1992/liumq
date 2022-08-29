package com.deepblue.libraries

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.deepblue.library.utils.FileUtils
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_edit_xml.*

class EditXmlActivity : AppCompatActivity() {

    /**
     * <?xml version='1.0' encoding='gb2312'?>
    <ftpSetting>
    <!--机器人服务器信息-->
    <robotServerSetting>
    <!--服务器IP-->
    <serverIP>192.168.16.92</serverIP>
    <!--服务器端口号-->
    <serverPort>20037</serverPort>
    <!--心跳时间/s-->
    <heartTime>6</heartTime>
    </robotServerSetting>

    </ftpSetting>
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_xml)

        val list = FileUtils.readAssets(this, "ftpConfig.xml")
        for (i in 0 until list.size) {
            val text = list[i]
            if (text.isEmpty()) {
                continue
            }
            val indexStart = text.indexOf('>')
            if (indexStart <= 0) {
                continue
            }
            val indexEnd = text.indexOf('<', indexStart)
            if (indexEnd <= indexStart) {
                continue
            }
            val indexTagStart = text.indexOf('<')
            if (indexTagStart < 0 || indexTagStart >= indexStart) {
                continue
            }
            val indexTagEnd = indexStart
            val tag = text.subSequence(indexTagStart +  1, indexTagEnd)
            val value = text.subSequence(indexStart + 1, indexEnd)

            val tableRow = View.inflate(this, R.layout.table_row_xml, null)
            val tvTag = tableRow.findViewById<TextView>(R.id.tvTag)
            val etValue = tableRow.findViewById<EditText>(R.id.etValue)
            tvTag.text = tag
            etValue.setText(value)

            tlRoot.addView(tableRow)
        }
    }
}