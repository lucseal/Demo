package com.samuel.demo.speech

import android.Manifest.permission.INTERNET
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.samuel.demo.R
import com.samuel.demo.base.BaseActivity
import android.Manifest.permission.RECORD_AUDIO
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import kotlinx.android.synthetic.main.activity_speech.*
import com.samuel.demo.utils.LogUtils


/**
 * @Description:
 * @author sunyao
 * @date 2018/11/1 8:34 PM
 */

class SpeechActivity : BaseActivity() {
    private val speechKey = "be1ced5a7fb64c44b1daca98dac10f6b"
    private val serviceRegion = "westus"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_speech)

        val requestCode = 5
        ActivityCompat.requestPermissions(this, arrayOf(RECORD_AUDIO, INTERNET), requestCode)


        requestSpeech.setOnClickListener {
            try {
                val config = SpeechConfig.fromSubscription(speechKey, serviceRegion)!!

                val reco = SpeechRecognizer(config)

                val task = reco.recognizeOnceAsync()!!

                // Note: this will block the UI thread, so eventually, you want to
                //        register for the event (see full samples)
                val result = task.get()!!

                if (result.reason === ResultReason.RecognizedSpeech) {
                    rcgResult.text = result.toString()
                } else {
                    rcgResult.text = "Error recognizing. Did you update the subscription info?" + System.lineSeparator() + result.toString()
                }

                reco.close()
            } catch (ex: Exception) {
                LogUtils.e("SpeechSDKDemo", "unexpected " + ex.message)
                assert(false)
            }

        }
    }
}