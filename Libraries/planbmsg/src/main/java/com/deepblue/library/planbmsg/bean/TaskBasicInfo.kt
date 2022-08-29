package com.deepblue.library.planbmsg.bean

import java.io.Serializable

class TaskBasicInfo : Serializable {

    companion object {
        //清扫
        const val TASK_TYPE_CLEAN = 1
        //送货
        const val TASK_TYPE_DELIVERY = 2
        //巡逻
        const val TASK_TYPE_PATROL = 3
        //前往
        const val TASK_TYPE_GOTO = 4
        //迎宾
        const val TASK_TYPE_WELCOME = 5
        //巡航
        const val TASK_TYPE_CRUISE = 6
        //吸尘
        const val TASK_TYPE_VACUUM = 7
        //补货
        const val TASK_TYPE_REPLENISH = 8
        //洗地
        const val TASK_TYPE_WASH = 9

        const val TASK_WELT=100

        //一次性任务
        const val TASK_MODE_ONCE = 0
        //循环
        const val TASK_MODE_LOOP = 1

        //立即执行
        const val EXECUTATION_TYPE_IMMEDIATELY = 1
        //定时执行
        const val EXECUTATION_TYPE_TIMMING = 2
        //不执行
        const val NO_EXECUTATION_TYPE = 3

        //极低
        const val TASK_PRIORITY_LOW = 1
        //普通
        const val TASK_PRIORITY_COMMON = 2
        //正常
        const val TASK_PRIORITY_NORMAL = 3
        //紧急
        const val TASK_PRIORITY_URGENT = 4

        //一直执行
        const val EXECUTATION_END_TYPE_ALWAYS = 0
        //其他时间
        const val EXECUTATION_END_TYPE_OTHER = 1
        //完成执行次数
        const val EXECUTATION_END_TYPE_COUNT = 2

        //每天
        const val REPEAT_TYPE_DAY = 1
        //每周
        const val REPEAT_TYPE_WEEK = 2
        //每个月
        const val REPEAT_TYPE_MONTH = 3

        //是全天
        const val IS_ALLDAY_YES = 1
        //不是全天
        const val IS_ALLDAY_NO = 2
    }

    //任务ID，新建填0
    var task_id = 0
    //任务类型 1:清扫，2:送货，3:巡逻，4:前往，5:迎宾，6:巡航，7:吸尘，8:补货
    var task_type = 0
    //任务状态 1：未开始 ，2：正在执行，3：任务异常，4：已完成，5：已取消,6：取消中 7：暂停
    //详见TaskStatus
    var task_status = 0
    //任务模式  0：一次性任务 1：循环
    var task_mode = 0
    //任务名称
    var task_name = ""
    //执行类型 1：立即执行 2：定时执行
    var executation_type = 0
    //优先级 1-极低，2-普通，3-正常，4-紧急
    var task_priority = 0
    //开始日期,2019-07-20
    var begin_date = ""
    //开始时间,09:10:32
    var begin_time = ""
    //结束日期，若为空的循环任务，则一直执行
    var end_date = ""
    //结束时间,09:50:32
    var end_time = ""
    //是否全天 1：是 2：否
    @JvmField
    var isAllday = 0
    //执行次数：仅在循环任务中有效
    var executTimes = 1
    //任务结束方式 0:一直执行，1:其他时间，2:完成执行次数
    var executEndType = 0
    //重复类型 周或月或年 1：每天 2：每周 3：每个月
    var repeat_type = 0
    //重复日期,"1,2"
    val repeat_date = ArrayList<String>()
    //地图id
    var map_id = 0
}