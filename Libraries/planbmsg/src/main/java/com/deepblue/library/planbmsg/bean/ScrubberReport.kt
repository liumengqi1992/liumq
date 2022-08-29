package com.deepblue.library.planbmsg.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 洗地机任务报告
 */
@SuppressLint("ParcelCreator")// 用于处理 Lint 的错误提示
@Parcelize
open class ScrubberReport : Parcelable {

    companion object {
        const val TASK_STATUS_FAIL = 1
        const val TASK_STATUS_STOP = 2
        const val TASK_STATUS_DONE = 3
    }

    //机器人名称
    var name = ""
    //机器人型号
    var model = ""
    //机器人ID
    var id = ""
    //操作用户
    var operater = ""
    //地图名称
    var map_id = 0
    //任务名称
    var task_name = ""
    var task_id = 0
    //任务状态。
    //1- 任务失败
    //2- 任务停止
    //3- 任务完成
    var task_status = 0
    //任务开始时间
    var start_time = ""
    //任务结束时间
    var end_time = ""
    //任务时长，单位秒
    var cost_time = 0.0
    //任务总面积，单位平方米
    var task_area = 0.0
    //实际清洗面积，单位平方米
    var clean_area = 0.0
    //任务完成比例
    var percent = 0
    //任务循环次数
    var total_times = 0
    var finish_times = 0
    var clean_mode = 0
    var distance=0.0
    var message: String? = null
    var error_code: String? = null
}