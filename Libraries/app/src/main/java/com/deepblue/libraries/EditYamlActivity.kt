package com.deepblue.libraries

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.deepblue.library.utils.FileUtils
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_edit_xml.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import java.lang.StringBuilder

class EditYamlActivity : AppCompatActivity() {

    /**
     *
    controller_frequency: 3.0
    recovery_behavior_enabled: false
    clearing_rotation_allowed: false

    TrajectoryPlannerROS:
    max_vel_x: 0.5
    min_vel_x: 0.1
    max_vel_y: 0.0  # zero for a differential drive robot
    min_vel_y: 0.0
    max_vel_theta: 1.0
    min_vel_theta: -1.0
    min_in_place_vel_theta: 0.4
    escape_vel: -0.1
    acc_lim_x: 1.5
    acc_lim_y: 0.0  # zero for a differential drive robot
    acc_lim_theta: 1.2

    holonomic_robot: false
    yaw_goal_tolerance: 0.1 # about 6 degrees
    xy_goal_tolerance: 0.05  # 5 cm
    latch_xy_goal_tolerance: false
    pdist_scale: 0.4
    gdist_scale: 0.8
    meter_scoring: true

    heading_lookahead: 0.325
    heading_scoring: false
    heading_scoring_timestep: 0.8
    occdist_scale: 0.05
    oscillation_reset_dist: 0.05
    publish_cost_grid_pc: false
    prune_plan: true

    sim_time: 1.0
    sim_granularity: 0.05
    angular_sim_granularity: 0.1
    vx_samples: 8
    vy_samples: 0  # zero for a differential drive robot
    vtheta_samples: 20
    dwa: true
    simple_attractor: false

    local_costmap:
    global_frame: map
    robot_base_frame: base_footprint
    update_frequency: 3.0
    publish_frequency: 1.0
    static_map: true
    rolling_window: false
    width: 6.0
    height: 6.0
    resolution: 0.01
    transform_tolerance: 1.0

    global_costmap:
    global_frame: map
    robot_base_frame: base_footprint
    update_frequency: 1.0
    publish_frequency: 1.0
    inflation_radius: 0.03
    static_map: true
    rolling_window: false
    resolution: 0.05
    transform_tolerance: 1.0
    map_type: costmap

    cleaning_costmap:
    global_frame: map
    robot_base_frame: base_footprint
    update_frequency: 1.0
    publish_frequency: 1.0
    static_map: true
    rolling_window: false
    resolution: 0.05
    transform_tolerance: 1.0
    inflation_radius: 0.12 #0.15
    map_type: costmap

    cleaning_plan_nodehandle:
    size_of_cell: 5
    grid_covered_value_pi: 70
    grid_covered_value_0: 140
    grid_covered_value_others: 210

    obstacle_range: 2.5
    raytrace_range: 3.0
    footprint: [[0.175, 0.175], [0.175, -0.175], [-0.175, -0.175], [-0.175, 0.175]]
    footprint_inflation: 0.01
    robot_radius: 0.25
    max_obstacle_height: 0.6
    min_obstacle_height: 0.0
    observation_sources: scan

    obstacle_layer:
    track_unknown_space: true



     */

    private val list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_xml)

        btnSave.setOnClickListener {
            doSave()
        }

        val booleans = resources.getStringArray(R.array.yaml_booleans)

        list.addAll(FileUtils.readAssets(this, "sample.yaml"))
        for (i in 0 until list.size) {
            val tableRow = View.inflate(this, R.layout.table_row_xml, null)
            val tvTag = tableRow.findViewById<TextView>(R.id.tvTag)
            val etValue = tableRow.findViewById<EditText>(R.id.etValue)

            val text = list[i]
            if (text.isEmpty()) {
                tlRoot.addView(tableRow)
                continue
            }

            val indexColon = text.indexOf(':')
            if (indexColon < 0) {
                tlRoot.addView(tableRow)
                continue
            }

            val tag = text.substring(0, indexColon)
            val afterColon = text.substring(indexColon + 1)
            val indexSharp = text.indexOf('#', indexColon)
            var value = afterColon
            if (indexSharp > indexColon) {
                value = text.substring(indexColon + 1, indexSharp)
            }

            tvTag.text = tag
            etValue.setText(value.trim())
            etValue.isEnabled = value.isNotEmpty()

            val textValue = etValue.text.toString()
            if (textValue in booleans) {
                etValue.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        AlertDialog.Builder(this@EditYamlActivity)
                            .setItems(R.array.yaml_booleans) { dialog, which ->
                                etValue.setText(booleans[which])
                                etValue.setSelection(booleans[which].length)
                                etValue.requestFocus()
                            }
                            .create().show()
                    }
                    true
                }
            }

            tlRoot.addView(tableRow)
        }
    }

    override fun onPause() {
        super.onPause()
        doSave()
    }

    private fun doSave() {
        if (list.size != tlRoot.childCount) {
            return
        }
        for (i in 0 until tlRoot.childCount) {
            val tableRow = tlRoot.getChildAt(i)
            val text = list[i]

            val indexColon = text.indexOf(':')
            if (indexColon < 0) {
                continue
            }

            val etValue = tableRow.findViewById<EditText>(R.id.etValue)

            list[i] = text.substring(0, indexColon + 1) + " " + etValue.text.toString()
        }

        val sb = StringBuilder()
        for (i in 0 until list.size) {
            sb.append(list[i])
            if (i < list.size - 1) {
                sb.append("\n")
            }
        }
        alert {
            message = sb.toString()
            cancelButton {  }
            okButton {
                toast(sb.toString())
            }
        }.show()
    }
}