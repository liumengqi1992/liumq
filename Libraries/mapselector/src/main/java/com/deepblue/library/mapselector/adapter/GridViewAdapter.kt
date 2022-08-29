package com.deepblue.library.mapselector.adapter

import android.content.Context
import android.text.TextUtils
import android.view.*
import com.deepblue.library.mapselector.R
import com.deepblue.library.mapselector.bean.MapBean
import android.widget.*


/**
 * Created by lijuan on 2016/9/12.
 * @modify caojun
 * @date 2019/04/09
 */
class GridViewAdapter(
    context: Context, private val gridView: GridView, private val mData: List<MapBean>,
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private val curIndex: Int,
    /**
     * 每一页显示的个数
     */
    private val pageSize: Int,
    /**
     * 行数
     */
    private val numRow: Int = 2
) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     * 先判断数据集的大小是否足够显示满本页？mData.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mData.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    override fun getCount(): Int {
        return if (mData.size > (curIndex + 1) * pageSize) pageSize else mData.size - curIndex * pageSize

    }

    override fun getItem(position: Int): MapBean {
        return mData[position + curIndex * pageSize]
    }

    override fun getItemId(position: Int): Long {
        return (position + curIndex * pageSize).toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false)
            viewHolder = ViewHolder()
            viewHolder.tvLabel = convertView!!.findViewById(R.id.tvLabel)
            viewHolder.ivMap = convertView.findViewById(R.id.ivMap)
            viewHolder.vMask = convertView.findViewById(R.id.vMask)
            viewHolder.cbSelector = convertView.findViewById(R.id.cbSelector)
            viewHolder.ivLock = convertView.findViewById(R.id.ivLock)

            //ellipsize
            viewHolder.tvLabel?.ellipsize = TextUtils.TruncateAt.MIDDLE

            viewHolder.cbSelector?.setOnCheckedChangeListener { buttonView, isChecked ->
                val bean = getBean(position)
                bean.isChecked = isChecked
            }

            //TODO 点击其他控件也相当于点击CheckBox
//            viewHolder.ivMap?.setOnClickListener {
//                viewHolder.cbSelector?.isChecked = !viewHolder.cbSelector!!.isChecked
//            }
//            viewHolder.tvLabel?.setOnClickListener {
//                viewHolder.cbSelector?.isChecked = !viewHolder.cbSelector!!.isChecked
//            }

            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        convertView.layoutParams = getLayoutParams(convertView)


        val bean = getBean(position)
        viewHolder.tvLabel?.text = bean.label
        viewHolder.ivMap?.setImageBitmap(bean.bitmap)
        viewHolder.vMask?.visibility = if (bean.isLocked) View.VISIBLE else View.GONE
        viewHolder.cbSelector?.isChecked = bean.isChecked
        viewHolder.ivLock?.visibility = if (bean.isLocked) View.VISIBLE else View.GONE
        return convertView
    }

    private inner class ViewHolder {
        internal var tvLabel: TextView? = null
        internal var ivMap: ImageView? = null
        internal var vMask: View? = null
        internal var cbSelector: CheckBox? = null
        internal var ivLock: ImageView? = null
    }

    private fun getBean(position: Int): MapBean {
        val pos = position + curIndex * pageSize
        return mData[pos]
    }

    private fun getLayoutParams(convertView: View): ViewGroup.LayoutParams {
        val width = (gridView.width - (gridView.numColumns * 2)) / gridView.numColumns
        val height = (gridView.height - (numRow * 2)) / numRow
        val layoutParams = convertView.layoutParams
//        layoutParams.width = width
//        layoutParams.height = height
        //正方形
        val min = Math.min(width, height)
        layoutParams.width = min
        layoutParams.height = min
        return layoutParams
    }
}