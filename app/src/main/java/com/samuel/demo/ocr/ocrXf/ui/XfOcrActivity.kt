package com.samuel.demo.ocr.ocrXf.ui


import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.samuel.demo.R
import com.samuel.demo.Unique
import com.samuel.demo.base.BaseActivity
import com.samuel.demo.ocr.bean.TxOcrItem
import com.samuel.demo.utils.BitmapUtils
import com.samuel.demo.utils.FileTrans
import com.samuel.demo.utils.LogUtils
import com.samuel.demo.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_ocr.*
import com.samuel.demo.ocr.ocrXf.vm.XfOcrViewModel
import com.samuel.demo.security.Md5
import org.json.JSONObject
import java.util.*

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午3:00
 */
class XfOcrActivity : BaseActivity() {
    private lateinit var xfOcrVM: XfOcrViewModel
    private var filePath = ""
    private var fileBitmap: Bitmap? = null
    private lateinit var drawPaint: Paint

    companion object {
        private const val TAG = "ocr_ocr"
        private const val REQUEST_CODE = 100

        fun intent(context: Context): Intent {
            val intent = Intent(context, XfOcrActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr)

        xfOcrVM = createViewModel(XfOcrViewModel::class.java)
        initPaint()

        openAlbum.setOnClickListener {
            openAlbum()
        }

        requestOcr.setOnClickListener {
            requestOcrApi()
        }

    }

    private fun initPaint() {
        drawPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        drawPaint.strokeWidth = 5f
        drawPaint.color = Color.RED
        drawPaint.style = Paint.Style.STROKE
        drawPaint.textSize = 20f
    }

    private fun openAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> {
                handleImage(data)
            }
        }
    }

    private fun handleImage(data: Intent?) {
        data ?: return
        LogUtils.d(data.toString(), TAG)
        releaseBitmap()

        filePath = data.data.path
        image_path.text = filePath
        fileBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data.data))
        image.setImageBitmap(fileBitmap)
    }

    private fun requestOcrApi() {
        if (filePath.isEmpty() || fileBitmap == null) {
            Toast.makeText(this, "请选择一张图片", Toast.LENGTH_SHORT).show()
            return
        }

        showLoadDialog()

        ocrResult.text = ""

        fileBitmap = BitmapUtils.bitmap2Gray(fileBitmap!!)
        val imageStr = FileTrans.bitmap2base64(fileBitmap!!)

        xfOcrVM.requestOcr(Unique.XF_OCR_HAND_WRITE, xfSignHeader(), imageStr)
                .subscribe({
                    hideLoadDialog()
                    ocrSuccess(it)
                }, {
                    hideLoadDialog()
                    ToastUtils.show(it.message)
                })
    }

    private fun ocrSuccess(result: Any) {
        LogUtils.d(result.toString(), TAG)
//        var resultStr = ""
//        val items = result.items
//        for (i in items.indices) {
//            val item = items[i]
//            resultStr += "${i + 1}：${item.itemstring}\n      坐标：[${item.itemcoord.x}, ${item.itemcoord.y}, ${item.itemcoord.width}, ${item.itemcoord.height}]\n\n"
//        }
//        ocrResult.text = resultStr
//
//        drawCoord(items)
    }

    private fun drawCoord(items: List<TxOcrItem>) {
        fileBitmap ?: return

        val copyBitmap = fileBitmap!!.copy(Bitmap.Config.RGB_565, true)
        val canvas = Canvas(copyBitmap)
        val rectList = mutableListOf<Rect>()
        items.forEach {
            val rect = Rect()
            rect.left = it.itemcoord.x
            rect.top = it.itemcoord.y
            rect.right = it.itemcoord.x + it.itemcoord.width
            rect.bottom = it.itemcoord.y + it.itemcoord.height
            rectList.add(rect)
        }

        for (i in rectList.indices) {
            val rect = rectList[i]
            val num = i + 1
            drawPaint.style = Paint.Style.STROKE
            canvas.drawRect(rect, drawPaint)
            drawPaint.style = Paint.Style.FILL
            val textSize = rect.height().toFloat()
            drawPaint.textSize = textSize
            canvas.drawText(num.toString(), rect.left.toFloat(), rect.bottom - textSize / 5f, drawPaint)
        }

        releaseBitmap()
        image.setImageBitmap(copyBitmap)
    }

    override fun onDestroy() {
        releaseBitmap()
        super.onDestroy()
    }

    private fun releaseBitmap() {
        image.setImageDrawable(null)
        if (fileBitmap != null && !fileBitmap!!.isRecycled) {
            fileBitmap?.recycle()
            fileBitmap = null
        }
    }

    private fun xfSignHeader(): HashMap<String, String> {
        val headerMap = HashMap<String, String>()
        val appid = Unique.XF_APP_ID
        val apiKey = Unique.XF_API_KEY
        val curTime = (System.currentTimeMillis() / 1000).toString()
        val json = JSONObject()
                .put("language", "cn|en")
                .put("location", true)
                .toString().toByteArray()
        val params = Base64.encodeToString(json, Base64.NO_WRAP)
        val checkSum = Md5.encode(apiKey + curTime + params)
        headerMap["X-Appid"] = appid
        headerMap["X-CurTime"] = curTime
        headerMap["X-Param"] = params
        headerMap["X-CheckSum"] = checkSum

        return headerMap
    }
}