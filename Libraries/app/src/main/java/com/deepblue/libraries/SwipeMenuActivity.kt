package com.deepblue.libraries

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.deepblue.library.adapter.bean.AdapterItem
import com.deepblue.library.swipemenu.*
import com.socks.library.KLog
import kotlinx.android.synthetic.main.activity_swipemenulist.*
import java.util.ArrayList

class SwipeMenuActivity : AppCompatActivity() {

    private val list = ArrayList<DemoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipemenulist)

        for (i in 0..19) {
            val dm = DemoModel()
            dm.content = "content: $i"
            list.add(dm)
        }
        val swipeMenuAdapter = singleType(list)

        swipeMenuListView.adapter = swipeMenuAdapter

        val creator = object : SwipeMenuCreator {

            override fun create(menu: SwipeMenu) {
                // create "open" item
                val openItem = SwipeMenuItem(
                    applicationContext
                )
                // set item background
                openItem.background = ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE))
                // set item width
                openItem.width = 180
                // set item title
                openItem.title = "Open"
                // set item title fontsize
                openItem.titleSize = 24
                // set item title font color
                openItem.titleColor = Color.WHITE
                // add to menu
                menu.addMenuItem(openItem)

                // create "delete" item
                val deleteItem = SwipeMenuItem(
                    applicationContext
                )
                // set item background
                deleteItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25))
                // set item width
                deleteItem.width = 180
                // set a icon
                deleteItem.setIcon(android.R.drawable.ic_delete)
                // add to menu
                menu.addMenuItem(deleteItem)
            }
        }
        // set creator
        swipeMenuListView.setMenuCreator(creator)

        // step 2. listener item click event
        swipeMenuListView.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener {
            override fun onMenuItemClick(position: Int, menu: SwipeMenu, index: Int): Boolean {
                val item = list[position]
                KLog.d("onMenuItemClick", "$position : $index")
                when (index) {
                    0 -> {
                        // open
                    }
                    1 -> {
                        // delete
                    }
                }
                return false
            }
        })
    }

    private fun singleType(data: List<DemoModel>): BaseSwipeListAdapter<DemoModel> {
        return object : BaseSwipeListAdapter<DemoModel>(data, 1) {

            override fun createItem(type: Any): AdapterItem<*> {
                // 如果就一种，那么直接return一种类型的item即可。
                return TextItem()
            }
        }
    }
}