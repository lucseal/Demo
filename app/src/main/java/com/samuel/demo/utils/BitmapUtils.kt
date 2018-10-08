package com.samuel.demo.utils

import android.graphics.*
import android.graphics.Bitmap


/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午8:45
 */
object BitmapUtils {

    fun bitmap2Gray(bmSrc: Bitmap): Bitmap {
        // 得到图片的长和宽
        val width = bmSrc.width
        val height = bmSrc.height
        // 创建目标灰度图像
        val bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        // 创建画布
        val c = Canvas(bmpGray)
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val f = ColorMatrixColorFilter(cm)
        paint.colorFilter = f
        c.drawBitmap(bmSrc, 0f, 0f, paint)
        return bmpGray
    }

    fun lineGrey(image: Bitmap): Bitmap {
        //得到图像的宽度和长度
        val width = image.width
        val height = image.height
        //创建线性拉升灰度图像
        var linegray: Bitmap? = null
        linegray = image.copy(Bitmap.Config.ARGB_8888, true)
        //依次循环对图像的像素进行处理
        for (i in 0 until width) {
            for (j in 0 until height) {
                //得到每点的像素值
                val col = image.getPixel(i, j)
                val alpha = col and -0x1000000
                var red = col and 0x00FF0000 shr 16
                var green = col and 0x0000FF00 shr 8
                var blue = col and 0x000000FF
                // 增加了图像的亮度
                red = (1.1 * red + 30).toInt()
                green = (1.1 * green + 30).toInt()
                blue = (1.1 * blue + 30).toInt()
                //对图像像素越界进行处理
                if (red >= 255) {
                    red = 255
                }

                if (green >= 255) {
                    green = 255
                }

                if (blue >= 255) {
                    blue = 255
                }
                // 新的ARGB
                val newColor = alpha or (red shl 16) or (green shl 8) or blue
                //设置新图像的RGB值
                linegray!!.setPixel(i, j, newColor)
            }
        }
        return linegray
    }

    // 该函数实现对图像进行二值化处理
    fun gray2Binary(graymap: Bitmap): Bitmap {
        //得到图形的宽度和长度
        val width = graymap.width
        val height = graymap.height
        //创建二值化图像
        var binarymap: Bitmap? = null
        binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true)
        //依次循环，对图像的像素进行处理
        for (i in 0 until width) {
            for (j in 0 until height) {
                //得到当前像素的值
                val col = binarymap!!.getPixel(i, j)
                //得到alpha通道的值
                val alpha = col and -0x1000000
                //得到图像的像素RGB的值
                val red = col and 0x00FF0000 shr 16
                val green = col and 0x0000FF00 shr 8
                val blue = col and 0x000000FF
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                var gray = (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
                //对图像进行二值化处理
                gray = if (gray <= 95) 0 else 255
                // 新的ARGB
                val newColor = alpha or (gray shl 16) or (gray shl 8) or gray
                //设置新图像的当前像素值
                binarymap.setPixel(i, j, newColor)
            }
        }
        return binarymap
    }
}