package com.deepblue.library.robotmsg.bean

class Settings {

    var sensorsettings = ArrayList<SensorSetting>()
    var robotsettings = RobotSetting()

    /*
    传感器参数
     */
    class SensorSetting {
        var id: Int = 0
        var type: String = ""
        var pos = Pos()
    }

    class Pos() {
        var rx: Double = 0.0
        var ry: Double = 0.0
        var rz: Double = 0.0
        var tx: Double = 0.0
        var ty: Double = 0.0
        var tz: Double = 0.0
    }

    class RobotSetting {
        var robotshape = RobotShape()
        var bypassobstacle = BypassObstacle()
        var robotspeed = RobotSpeed()
        var robotwheel = RobotWheel()
        var laser = Laser()
    }

    /**
     *避障距离
     */
    class BypassObstacle {
        var bypassobstacledistance: Double = 0.0
        var bypassobstacleduration: Int = 0
    }

    /*
    最大速度
     */
    class RobotSpeed {
        var maxlinarspeed: Double = 0.0
    }

    /*

     */
    class RobotWheel {
        var wheelgauge: Double = 0.0
        var wheeldiameter: Double = 0.0
    }

    class Laser {
        var lasermaxangle: Double = 0.0
        var laserminangle: Double = 0.0
        var laserplacement = Laserplacement()
    }

    class Laserplacement {
        var selected: String = "downside"
    }


    /**
     * 机器人坐标
     */
    class RobotShape {
        var bottomleftcornercoordinate = Coordinate()
        var bottomrightcornercoordinate = Coordinate()
        var topleftcornercoordinate = Coordinate()
        var toprightcornercoordinate = Coordinate()
        var selected: String = ""
        var radius: Double = 0.0
    }

    class Coordinate() {
        var x: Double = 0.0
        var y: Double = 0.0

    }
}