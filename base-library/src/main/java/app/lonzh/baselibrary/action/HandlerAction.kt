package app.lonzh.baselibrary.action

import android.os.Handler
import android.os.Looper
import android.os.SystemClock

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 10:36 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 10:36 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface HandlerAction {

    companion object {
        val HANDLER = Handler(Looper.getMainLooper())
    }

    /**
     * 获取handler
     */
    val handler: Handler?
        get() = HANDLER

    /**
     * 指定时间执行
     */
    fun postAtTime(r: Runnable, updateMillis: Long): Boolean{
        return HANDLER.postAtTime(r, this, updateMillis)
    }

    /**
     * 延迟一段时间执行
     */
    fun postDelayed(r: Runnable, delayMillis: Long): Boolean {
        var dm = delayMillis
        if(dm < 0){
            dm = 0
        }
        return postAtTime(r,  SystemClock.uptimeMillis() + dm)
    }

    /**
     * 延迟执行
     */
    fun post(r: Runnable): Boolean {
        return postDelayed(r, 0)
    }

    /**
     * 移除回调
     */
    fun removeCallbacks(){
        handler?.removeCallbacksAndMessages(this)
    }
}