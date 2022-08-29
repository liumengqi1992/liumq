package com.deepblue.library.planbmsg.bean

class Sensor {

    /**
     * ultran_data : [{"index":0,"status":0,"range":1.5},{"index":2,"status":0,"range":1.5}]
     * toflink_data : [{"index":0,"status":0,"range":1.5},{"index":1,"status":0,"range":1.5}]
     * cilff_data : [{"index":0,"status":0,"range":1.5}]
     * lidar_data : [{"index":0,"status":0,"range":1.5}]
     * odometer : 9.12
     * yaw : {"status":0,"roll":0.0069,"pitch":0.0139,"yaw":0.221}
     * crash_status : {"index":1,"status":0,"data":false}
     */
    var yaw: YawBean? = null
    var crash_status: CrashStatusBean? = null
    var ultran_data: List<UltranDataBean>? = null
    var toflink_data: List<ToflinkDataBean>? = null
    var cilff_data: List<CilffDataBean>? = null
    var lidar_data: List<LidarDataBean>? = null
    var odometer:Odometer?=null
    var uvlamp_data:List<UvlampStatus>?=null
    var uvlamp_door_data:List<UvlampStatus>?=null
    var human_detection_data:List<Humandetection>?=null

    class Odometer{
        var data=0.0
        var status=0

    }

    class YawBean {
        /**
         * status : 0
         * roll : 0.0069
         * pitch : 0.0139
         * yaw : 0.221
         */
        var status = 0
        var roll = 0.0
        var pitch = 0.0
        var yaw = 0.0
    }

    class CrashStatusBean {
        /**
         * index : 1
         * status : 0
         * data : false
         */
        var index = 0
        var status = 0
        var data = false
    }

    class UltranDataBean {
        /**
         * index : 0
         * status : 0
         * range : 1.5
         */
        var index = 0
        var status = 0
        var range = 0.0
    }

    class ToflinkDataBean {
        /**
         * index : 0
         * status : 0
         * range : 1.5
         */
        var index = 0
        var status = 0
        var range = 0.0
    }

    class CilffDataBean {
        /**
         * index : 0
         * status : 0
         * range : 1.5
         */
        var index = 0
        var status = 0
        var range = 0.0
    }

    class LidarDataBean {
        /**
         * index : 0
         * status : 0
         * range : 1.5
         */
        var index = 0
        var status = 0
        var range = 0.0
    }

    class UvlampStatus{
        var index:Int=0
        var status:Int=0
        var data:Boolean=false
    }

    class Humandetection{
        var index:Int=0
        var status:Int=0
        var range:Double=0.0
        var data:Boolean=false
    }
}