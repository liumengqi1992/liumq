package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.CleanMode

class SetScrubberCleanModeReq : Request(5001, "设置洗地机清洁模式") {

    fun standard(): String {
        number = CleanMode.STANDARD.ordinal
        json = Data(number)
        return toString()
    }

    fun dry(): String {
        number = CleanMode.DRY.ordinal
        json = Data(number)
        return toString()
    }

    fun water(): String {
        number = CleanMode.WATER.ordinal
        json = Data(number)
        return toString()
    }

    fun deep(): String {
        number = CleanMode.DEEP.ordinal
        json = Data(number)
        return toString()
    }
    fun disinfection():String{
        number = CleanMode.DISINFECTION.ordinal
        json = Data(number)
        return toString()
    }
    fun dis_standard():String{
        number = CleanMode.DIS_STANDARD.ordinal
        json = Data(number)
        return toString()
    }
    fun dis_dry():String{
        number = CleanMode.DIS_DRY.ordinal
        json = Data(number)
        return toString()
    }
    fun dis_water():String{
        number = CleanMode.DIS_WATER.ordinal
        json = Data(number)
        return toString()
    }
    fun dis_deep():String{
        number = CleanMode.DIS_DEEP.ordinal
        json = Data(number)
        return toString()
    }
    fun mopping():String{
        number = CleanMode.MOPPING.ordinal
        json = Data(number)
        return toString()
    }
    fun dis_mopp():String{
        number = CleanMode.DIS_MOPPING.ordinal
        json = Data(number)
        return toString()
    }

    /**
     * 数据区
     * @param mode 清洁模式
     */
    class Data(val mode: Int)
}