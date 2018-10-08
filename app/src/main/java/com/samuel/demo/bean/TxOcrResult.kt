package com.samuel.demo.bean

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午7:27
 */

data class TxOcrResult(val session_id: String, val items: List<TxOcrItem>)

data class TxOcrItem(val itemstring: String, val itemcoord: TxOcrCoord, val words: List<TxOcrWord>)
data class TxOcrCoord(val x: Int, val y: Int, val width: Int, val height: Int)
data class TxOcrWord(val character: String, val confidence: Float)