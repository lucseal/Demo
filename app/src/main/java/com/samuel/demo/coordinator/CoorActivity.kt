package com.samuel.demo.coordinator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.samuel.demo.R
import com.samuel.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_coor.*

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/27 上午11:24
 */
class CoorActivity : BaseActivity() {


    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, CoorActivity::class.java)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coor)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val strList = mutableListOf<String>()

        (0..100).forEach {
            strList.add("item $it")
        }

        recyclerView.adapter = TempAdatper(strList)
    }


    private class TempAdatper(val strList: List<String>) : RecyclerView.Adapter<TempVH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempVH {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
            return TempVH(view)
        }

        override fun getItemCount(): Int {
            return strList.size
        }

        override fun onBindViewHolder(holder: TempVH, position: Int) {
            holder.textview.text = strList[position]
        }

    }

    private class TempVH(view: View) : RecyclerView.ViewHolder(view) {
        val textview: TextView

        init {
            textview = view.findViewById(R.id.textview)
        }
    }

}