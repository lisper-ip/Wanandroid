package app.lonzh.commonlibrary.ext

import app.lonzh.baselibrary.manage.net.NetHelper
import app.lonzh.commonlibrary.R
import app.lonzh.commonlibrary.exception.AppException
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import org.json.JSONObject
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.lang.IllegalArgumentException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 *
 * @Description:    网络异常扩展类
 * @Author:         Lisper
 * @CreateDate:     2020/6/23 9:47 AM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/6/23 9:47 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */

val Throwable.errorCode: String
    get() {
        val errorCode = when (this) {
            is HttpStatusCodeException -> {//请求失败异常
                try {
                    val json = JSONObject(result)
                    json.getString("code")
                }catch (e: JSONException){
                    statusCode.toString()
                }
            }
            is ParseException -> {  // ParseException异常表明请求成功，但是数据不正确
                this.errorCode
            }
            else -> {
                "-1"
            }
        }
        return try {
            errorCode
        } catch (e: Exception) {
            "-1"
        }
    }

val Throwable.errorMsg: String?
    get() {
        var errorMsg = handleNetworkException(this)  //网络异常
        if (this is HttpStatusCodeException) {               //请求失败异常
            errorMsg = try {
                JSONObject(result).getString("msg")
            }catch (e : JSONException){
                "未知错误"
            }
        } else if (this is JsonSyntaxException) {  //请求成功，但Json语法异常,导致解析失败
            errorMsg = "数据解析失败,请稍后再试"
        } else if (this is ParseException) {       // ParseException异常表明请求成功，但是数据不正确
            errorMsg = this.message ?: errorCode   //errorMsg为空，显示errorCode
        } else if (this is AppException){
            errorMsg = this.errorMsg
        } else if(this is IllegalArgumentException){
            errorMsg = this.message
        }
        return errorMsg
    }

//处理网络异常
private fun <T> handleNetworkException(throwable: T): String? {
    val stringId =
        if (throwable is UnknownHostException) { //网络异常
            if (!NetHelper.isNetWorkConnected(appContext)) R.string.network_error else R.string.notify_no_network
        } else if (throwable is SocketTimeoutException || throwable is TimeoutException) {
            R.string.time_out_please_try_again_later  //前者是通过OkHttpClient设置的超时引发的异常，后者是对单个请求调用timeout方法引发的超时异常
        } else if (throwable is ConnectException) {
            R.string.network_service_exception  //连接异常
        } else {
            -1
        }
    return if (stringId == -1) null else appContext.getString(stringId)
}