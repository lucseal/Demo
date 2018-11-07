package com.samuel.demo.home.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.samuel.demo.R
import com.samuel.demo.coordinator.ScrollingActivity
import com.samuel.demo.nav.NavActivity
import com.samuel.demo.ocr.ocrBd.BdOcrMainActivity
import com.samuel.demo.ocr.ocrTx.ui.TxOcrActivity
import com.samuel.demo.ocr.ocrXf.ui.XfOcrActivity
import com.samuel.demo.speech.SpeechActivity
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

        nav.setOnClickListener {
            startActivity(NavActivity.intent(this))
        }

        speech.setOnClickListener {
            startActivity(Intent(this, SpeechActivity::class.java))
        }

    }
}
