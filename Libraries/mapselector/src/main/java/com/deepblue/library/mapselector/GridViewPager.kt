package com.deepblue.library.mapselector

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.deepblue.library.mapselector.adapter.GridViewAdapter
import com.deepblue.library.mapselector.adapter.ViewPagerAdapter
import com.deepblue.library.mapselector.bean.MapBean
import com.deepblue.library.mapselector.listener.GridItemClickListener
import com.deepblue.library.mapselector.listener.GridItemLongClickListener
import java.util.ArrayList
import android.widget.GridView
import org.jetbrains.anko.doAsync


/**
 * 地图GridView+Viewpager
 * @author caojun
 * @data 2019/04/08
 */

class GridViewPager : RelativeLayout {
    private var hasCustomOval = false
    private var inflater: LayoutInflater? = null
    private var mPager: ViewPager? = null
    private var mLlDot: LinearLayout? = null
    private var mData = ArrayList<MapBean>()

    private var mPagerList = ArrayList<GridView>()
    private var gridItemClickListener: GridItemClickListener? = null
    private var gridItemLongClickListener: GridItemLongClickListener? = null

    /**
     * 总的页数 计算得出
     */
    var pageCount: Int = 0
        private set

    /**
     * 每一页显示的个数 可设置
     */
    private var pageSize = 8

    /**
     * 当前显示的是第几页
     */
    var curIndex = 0
        private set

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        inflater = LayoutInflater.from(context)
        val view = inflater!!.inflate(R.layout.grid_view_pager, this)
        mPager = view.findViewById(R.id.viewPager)
        mLlDot = view.findViewById(R.id.ll_dot)
    }

    /**
     * necessary 必须作为最后一步
     *
     * @param list
     * @return
     */
    fun init(list: List<MapBean>): GridViewPager {
        doAsync {
            mData.clear()
            mData.addAll(list)
            //总的页数=总数/每页数量，并取整
            pageCount = Math.ceil(mData.size * 1.0 / pageSize).toInt()
            mPagerList.clear()

            for (i in 0 until pageCount) {
                //每个页面都是inflate出一个新实例
                val gridView = inflater!!.inflate(R.layout.gridview, mPager, false) as GridView
                gridView.adapter = GridViewAdapter(context, gridView, mData, i, pageSize)

                gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, pos, id ->
                    if (gridItemClickListener == null) return@OnItemClickListener
                    val position = pos + curIndex * pageSize
                    gridItemClickListener?.click(pos, position, mData[position].label)
                }
                //true if the callback consumed the long click, false otherwise
                gridView.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, pos, id ->
                    if (gridItemLongClickListener == null)
                        false
                    else {
                        val position = pos + curIndex * pageSize
                        gridItemLongClickListener?.click(pos, position, mData[position].label)
                        true
                    }
                }

                mPagerList.add(gridView)
            }
            //设置适配器
            mPager?.adapter = ViewPagerAdapter(mPagerList)
            //设置圆点
            if (!hasCustomOval) setOvalLayout()
        }
        return this
    }

    /**
     * optional 设置自定义圆点
     */
    fun setOvalLayout(view: View, listener: ViewPager.OnPageChangeListener) {
        hasCustomOval = true
        mLlDot?.removeAllViews()
        mLlDot?.addView(view)
        mPager?.addOnPageChangeListener(listener)
    }

    /**
     * 设置圆点
     */
    private fun setOvalLayout() {
        for (i in 0 until pageCount) {
            mLlDot!!.addView(inflater!!.inflate(R.layout.dot, null))
        }
        // 默认显示第一页
        mLlDot?.getChildAt(0)?.findViewById<View>(R.id.v_dot)?.setBackgroundResource(R.drawable.dot_selected)
        mPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                // 取消圆点选中
                mLlDot?.getChildAt(curIndex)?.findViewById<View>(R.id.v_dot)?.setBackgroundResource(R.drawable.dot_normal)
                // 圆点选中
                mLlDot?.getChildAt(position)?.findViewById<View>(R.id.v_dot)?.setBackgroundResource(R.drawable.dot_selected)
                curIndex = position
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

            override fun onPageScrollStateChanged(arg0: Int) {}
        })
    }

    /**
     * optional 设置单元点击事件
     *
     * @param listener
     * @return
     */
    fun setGridItemClickListener(listener: GridItemClickListener): GridViewPager {
        gridItemClickListener = listener
        return this
    }

    /**
     * optional 设置单元长按事件
     *
     * @param listener
     * @return
     */
    fun setGridItemLongClickListener(listener: GridItemLongClickListener): GridViewPager {
        gridItemLongClickListener = listener
        return this
    }

    fun getmPagerList(): List<GridView>? {
        return mPagerList
    }

    fun getPageSize(): Int {
        return pageSize
    }

    fun setPageSize(pageSize: Int): GridViewPager {
        this.pageSize = pageSize
        return this
    }
}
