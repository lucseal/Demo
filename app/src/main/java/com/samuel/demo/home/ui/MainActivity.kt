package com.samuel.demo.home.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.samuel.demo.R
import com.samuel.demo.coordinator.CoorActivity
import com.samuel.demo.coordinator.ScrollingActivity
import com.samuel.demo.ocrBd.BdOcrMainActivity
import com.samuel.demo.ocrTx.ui.TxOcrActivity
import com.samuel.demo.ocrXf.ui.XfOcrActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toTxOcr.setOnClickListener {
            startActivity(TxOcrActivity.intent(this))
        }

        toBdOcr.setOnClickListener {
            startActivity(Intent(this, BdOcrMainActivity::class.java))
        }

        toXfOcr.setOnClickListener {
            startActivity(XfOcrActivity.intent(this))
        }

        toCoor.setOnClickListener {
            startActivity(ScrollingActivity.intent(this))
        }

    }
}
