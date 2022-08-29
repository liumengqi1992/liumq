package com.deepblue.libraries

import android.graphics.Point
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.deepblue.library.bezier.*
import com.deepblue.library.bezier.utils.ViewUtils
import com.deepblue.library.robotmsg.bean.Path
import com.socks.library.KLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.doAsync


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        batteryView.setOnClickListener {
            batteryView.isCharging = !batteryView.isCharging
        }

        batteryViewV.setOnClickListener {
            batteryViewV.isCharging = !batteryViewV.isCharging
        }

//        val info = InfoRes.Info()
//        info.navi_version = "navi_version"
//        info.hardware_version = "hardware_version"
//        info.netprotocol_version = "netprotocol_version"
//        info.birthplace = "birthplace"
//        info.birthday = "birthday"
//        info.shape = "rectangle"
//        info.height = 10
//        info.length = 20
//        info.width = 30
//        info.serial_number = "serial_number"
//        info.model = "model"
//        val infores = InfoRes()
//        infores.type = 11000
//        infores.name = "robot_status_info_res"
//        infores.json = info

//        val req = infores.toString()
//        KLog.json(req)
//
//        val res = Response.fromJson(req, InfoRes::class.java)
//        KLog.d(res)

//        val task = TaskRes.Task()
//        task.task_id = 123
//        task.task_state = TaskRes.TaskInitState//任务状态
//        task.finished_step = 1//已经完成步骤数目
//        task.total_step = 3//总的步骤数目
//        task.waypoints = arrayListOf("x0", "y0")
//        val taskres = TaskRes()
//        taskres.type = 11006
//        taskres.name = "robot_status_task_res"
//        taskres.json = task

//        val req = taskres.toString()
//
//        try {
//            val res = Response.fromJson(req, TaskRes::class.java)
////            val data = Response.fromJson(res!!.json.toString(), TaskRes.Task::class.java)
//            val task = res?.getTask()
//            KLog.d(task?.waypoints)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        val maps = GetAllMapsRes.Maps()
//        maps.maps.add("map0")
//        maps.maps.add("map1")
//        val mapsRes = GetAllMapsRes()
//        mapsRes.json = maps
//
//        val resJson = mapsRes.toString()
//        KLog.json(resJson)
//        val res = Response.fromJson(resJson, GetAllMapsRes::class.java)
//        val json = res?.getJson()
//        KLog.d(json!!.maps)


//        val getAllTasksReq = GetAllTasksReq("mapname")

//        val pointArray = arrayListOf(
//            GetScanRes.Point(1.0, 2.0),
//            GetScanRes.Point(2.0, 3.0)
//        )
//        val points = GetScanRes.Points()
//        points.scan_points = pointArray
//        val getScanRes = GetScanRes()
//        getScanRes.json = points
//
//
//        val req = getScanRes.toString()
//        KLog.json(req)
//
//        val res = Response.fromJson(req, GetScanRes::class.java)
//        val json = res!!.getJson()
//        for (i in 0 until json!!.scan_points.size) {
//            KLog.d("$i", "${json.scan_points[i].x} : ${json.scan_points[i].y}")
//        }

        val poses = Path.Pos()
        val path = Path()
        path.action = ""
        path.nav_type = "nav_type"
        path.type = "type"
        path.poses = arrayListOf(poses, poses)

//        val getTaskPathRes = GetTaskPathRes()
//        val paths = arrayListOf(path, path)
//        val data = GetTaskPathRes.Data()
//        data.data = paths
//        getTaskPathRes.json = data
//        val req = getTaskPathRes.toString()
//        KLog.json(req)
////        val nullReq = JsonUtils.markNullValue(req)
////        KLog.json(JsonUtils.markNullValue(nullReq))
//        val res = JsonUtils.fromJson(req, GetTaskPathRes::class.java)
//        val json = res!!.getJson()
//        for (i in 0 until json!!.data.size) {
//            KLog.d("$i", "nav_type: ${json.data[i].nav_type}")
//            KLog.d("$i", "type: ${json.data[i].type}")
//            for (j in 0 until json.data[i].poses.size) {
//                KLog.d("$j", "json.data[i].poses: ${json.data[i].poses[j].x}")
//                KLog.d("$j", "json.data[i].poses: ${json.data[i].poses[j].y}")
//            }
//        }

        val bazierView = BazierView(this)
        val polygon = PolygonView(this)
        val circle = CircleView(this)
        val rectangle = RectangleView(this)
        val rotateRectangle = RotateRectangleView(this)
        val oval = OvalView(this)
        val singlePoint = SinglePointView(this)

//        degreeWheel.visibility = View.GONE

        val popWindow = AnyPosPopupWindow(this)

        //贝赛尔曲线
        btnBazier.setOnClickListener {
            bazierView.init(object : ClickListener {
                override fun onClick(point: Point) {
                    KLog.d("bazierView.onClick", "${point.x} : ${point.y}")
                }

                override fun onLongClick(point: Point) {
                    KLog.d("bazierView.onLongClick", "${point.x} : ${point.y}")
//                    bazierView.editable = false
                    KLog.d("bazierView.onLongClick", "${popWindow.width} : ${popWindow.height}")
                    popWindow.showAtLocation(root, point.x, point.y)
                }

                override fun onTouched(point: Point) {
                    KLog.d("bazierView.onTouched", "${point.x} : ${point.y}")
                }

                override fun onClickEmpty(point: Point) {
                    bazierView.addPoint(point)
                }

                override fun onLongClickEmpty(point: Point) {
                    KLog.d("bazierView.onLongClickEmpty", "${point.x} : ${point.y}")
//                    bazierView.editable = false
                    KLog.d("bazierView.onLongClickEmpty", "${popWindow.width} : ${popWindow.height}")
                    popWindow.showAtLocation(root, point.x, point.y)
                }
            })
            bazierView.setButtons(btnPrevious, btnNext)
            ViewUtils.setEditable(root)
            root.addView(bazierView)
        }
        btnBazier.setOnLongClickListener {
            try {
                root.removeView(bazierView)
            } catch (e: Exception) {
            }
            true
        }

        //多边形
        btnPolygon.setOnClickListener {
            polygon.init(object : ClickListener {
                override fun onClick(point: Point) {
                    KLog.d("polygon.onClick", "${point.x} : ${point.y}")
                    polygon.clickPoint(point)
                }

                override fun onLongClick(point: Point) {
                    KLog.d("polygon.onLongClick", "${point.x} : ${point.y}")
                    polygon.editable = false
                }

                override fun onTouched(point: Point) {
                    KLog.d("polygon.onTouched", "${point.x} : ${point.y}")
                }

                override fun onClickEmpty(point: Point) {
                    KLog.d("polygon.onClickEmpty", "${point.x} : ${point.y}")
                    polygon.addPoint(point)
                }

                override fun onLongClickEmpty(point: Point) {
                    KLog.d("polygon.onLongClickEmpty", "${point.x} : ${point.y}")
                    polygon.editable = false
                }
            })
            polygon.setButtons(btnPrevious, btnNext)
            ViewUtils.setEditable(root)
            root.addView(polygon, 0)
        }
        btnPolygon.setOnLongClickListener {
            try {
                root.removeView(polygon)
            } catch (e: Exception) {
            }
            true
        }

        //圆
        btnCircle.setOnClickListener {
            circle.init(object : ClickListener {
                override fun onClick(point: Point) {
                    KLog.d("circle.onClick", "${point.x} : ${point.y}")
                }

                override fun onLongClick(point: Point) {
                    KLog.d("circle.onLongClick", "${point.x} : ${point.y}")
                    circle.editable = false
                }

                override fun onTouched(point: Point) {
                    KLog.d("circle.onTouched", "${point.x} : ${point.y}")
                }

                override fun onClickEmpty(point: Point) {
                    circle.addPoint(point)
                }

                override fun onLongClickEmpty(point: Point) {
                    KLog.d("circle.onLongClickEmpty", "${point.x} : ${point.y}")
                    circle.editable = false
                }
            })
            circle.setButtons(btnPrevious, btnNext)
            ViewUtils.setEditable(root)
            root.addView(circle)
        }
        btnCircle.setOnLongClickListener {
            try {
                root.removeView(circle)
            } catch (e: Exception) {
            }
            true
        }

        //矩形
        btnRectangle.setOnClickListener {
            val start = Point(50, 50)
            val end = Point(200, 300)
            rectangle.init(object : ClickListener {
                override fun onClick(point: Point) {
                    KLog.d("rectangle.onClick", "${point.x} : ${point.y}")
                }

                override fun onLongClick(point: Point) {
                    KLog.d("rectangle.onLongClick", "${point.x} : ${point.y}")
                    rectangle.editable = false
                }

                override fun onTouched(point: Point) {
                    KLog.d("rectangle.onTouched", "${point.x} : ${point.y}")
                }

                override fun onClickEmpty(point: Point) {
                    //TODO
                }

                override fun onLongClickEmpty(point: Point) {
                    KLog.d("rectangle.onLongClickEmpty", "${point.x} : ${point.y}")
                    rectangle.editable = false
                }
            }, start, end)
            rectangle.setButtons(btnPrevious, btnNext)
            ViewUtils.setEditable(root)
            root.addView(rectangle)
        }
        btnRectangle.setOnLongClickListener {
            try {
                root.removeView(rectangle)
            } catch (e: Exception) {
            }
            true
        }

        //旋转矩形
        btnRotateRectangle.setOnClickListener {
            val start = Point(50, 50)
            rotateRectangle.init(object : ClickListener {
                override fun onClick(point: Point) {
                    KLog.d("rotateRectangle.onClick", "${point.x} : ${point.y}")
                }

                override fun onLongClick(point: Point) {
                    KLog.d("rotateRectangle.onLongClick", "${point.x} : ${point.y}")
                    rotateRectangle.editable = false
                }

                override fun onTouched(point: Point) {
                    KLog.d("rotateRectangle.onTouched", "${point.x} : ${point.y}")
                }

                override fun onClickEmpty(point: Point) {
                    //TODO
                }

                override fun onLongClickEmpty(point: Point) {
                    KLog.d("rotateRectangle.onLongClickEmpty", "${point.x} : ${point.y}")
                    rotateRectangle.editable = false
                }
            }, start, 200, 300)
            rotateRectangle.setButtons(btnPrevious, btnNext)
            ViewUtils.setEditable(root)
            root.addView(rotateRectangle)
        }
        btnRotateRectangle.setOnLongClickListener {
            try {
                root.removeView(rotateRectangle)
            } catch (e: Exception) {
            }
            true
        }

        //椭圆
        btnOval.setOnClickListener {
            val start = Point(50, 50)
            val end = Point(200, 300)
            oval.init(object : ClickListener {
                override fun onClick(point: Point) {
                    KLog.d("oval.onClick", "${point.x} : ${point.y}")
                }

                override fun onLongClick(point: Point) {
                    KLog.d("oval.onLongClick", "${point.x} : ${point.y}")
                    oval.editable = false
                }

                override fun onTouched(point: Point) {
                    KLog.d("oval.onTouched", "${point.x} : ${point.y}")
                }

                override fun onClickEmpty(point: Point) {
                    //TODO
                }

                override fun onLongClickEmpty(point: Point) {
                    KLog.d("oval.onLongClickEmpty", "${point.x} : ${point.y}")
                    oval.editable = false
                }
            }, start, end)
            oval.setButtons(btnPrevious, btnNext)
            ViewUtils.setEditable(root)
            root.addView(oval)
        }
        btnOval.setOnLongClickListener {
            try {
                root.removeView(oval)
            } catch (e: Exception) {
            }
            true
        }

        //单点
        btnSinglePoint.setOnClickListener {
            singlePoint.init(object : ClickListener {
                override fun onClick(point: Point) {
                    KLog.d("bazierView.onClick", "${point.x} : ${point.y}")
                }

                override fun onLongClick(point: Point) {
                    KLog.d("bazierView.onLongClick", "${point.x} : ${point.y}")
                    singlePoint.editable = false
                }

                override fun onTouched(point: Point) {
                    KLog.d("bazierView.onTouched", "${point.x} : ${point.y}")
                }

                override fun onClickEmpty(point: Point) {
                    singlePoint.addPoint(point)
                }

                override fun onLongClickEmpty(point: Point) {
                    KLog.d("singlePoint.onLongClickEmpty", "${point.x} : ${point.y}")
                    singlePoint.editable = false
                }
            }/*, Point(200, 200)*/)
            singlePoint.setButtons(btnPrevious, btnNext)
            ViewUtils.setEditable(root)
            root.addView(singlePoint)

//            degreeWheel.visibility = View.VISIBLE
//            degreeWheel.setOnItemSelectedListener { picker, data, position ->
//                val text = data as String
//                val degree = Integer.valueOf(text.substring(0, text.length - 1))
//                singlePoint.degree = degree
//            }
        }
        btnSinglePoint.setOnLongClickListener {
            try {
                root.removeView(singlePoint)
            } catch (e: Exception) {
            }
            true
        }

        doAsync {
            var power = 0
            while(true) {
                batteryView.setPowers(power ++, 20, 40)
//                batteryViewV.power = power ++
                if (power > 100) {
                    power = 0
                }
                Thread.sleep(100)
            }
        }

        //MapSelector
//        mapSelector
//            //设置每一页的容量
//            .setPageSize(8)
//            .setGridItemClickListener(object : GridItemClickListener {
//                override fun click(pos: Int, position: Int, str: String) {
//                    KLog.d("setGridItemClickListener", "$position/$str")
//                }
//            })
//            .setGridItemLongClickListener(object : GridItemLongClickListener {
//                override fun click(pos: Int, position: Int, str: String) {
//                    KLog.d("setGridItemLongClickListener", "$position/$str")
//                }
//            })
//            //传入String的List 必须作为最后一步
//            .init(initData())

//        batteryView.setOnClickListener {
//
//            val dialogListener = object : DialogListener {
//                override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//                    val builder = AlertDialog.Builder(this@MainActivity)
//                    builder.setMessage("你进入了无网的异次元中")
//                    return builder.create()
//                }
//
//                override fun onCancel() {
//                    KLog.d()
//                }
//            }
//
//            val blurDialogFragment = DialogFragment.newInstance(dialogListener)
//            blurDialogFragment.show(supportFragmentManager, "test")
//        }

//        progressImageView.startRotate()
//        progressImageView.setOnClickListener {
//            progressImageView.animError()
//        }
    }

//    private fun initData(): List<MapBean> {
//        val mData = ArrayList<MapBean>()
//        for (i in 0 until 20) {
//            val bean = MapBean(
//                "labellabellabel$i",
//                BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher),
//                false,
//                i % 2 == 0
//            )
//            mData.add(bean)
//        }
//        return mData
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
