package com.samuel.demo.data

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午2:44
 */

data class BaseResponse<T>(val code: String, val data: T, val message: String) {
    fun isSuccess() = true
}