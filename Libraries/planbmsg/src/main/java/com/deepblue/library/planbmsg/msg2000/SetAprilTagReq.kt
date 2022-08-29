package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SetAprilTagReq(tagid: Int, centre_point: DoubleArray, side_points: DoubleArray) :
    Request(2022, "Apriltag下发") {
    init {
        json = Data(tagid, centre_point, side_points)
    }

    class Data(val tagid: Int, val centre_point: DoubleArray, val side_points: DoubleArray)

}