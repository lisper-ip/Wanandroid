package app.lonzh.baselibrary.action;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/**
 * @ProjectName: lisper
 * @Description: 接口最好用java编写 特别是包含默认实现的方法
 * @Author: Lisper
 * @CreateDate: 2021/7/5 3:42 下午
 * @UpdateUser: Lisper：
 * @UpdateDate: 2021/7/5 3:42 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface HandlerAction {
    Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 获取 Handler
     */
    default Handler getHandler() {
        return HANDLER;
    }

    /**
     * 延迟执行
     */
    default boolean post(Runnable r) {
        return postDelayed(r, 0);
    }

    /**
     * 延迟一段时间执行
     */
    default boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    default boolean postAtTime(Runnable r, long uptimeMillis) {
        // 发送和当前对象相关的消息回调
        return HANDLER.postAtTime(r, this, uptimeMillis);
    }

    /**
     * 移除全部消息回调
     */
    default void removeCallbacks() {
        // 移除和当前对象相关的消息回调
        HANDLER.removeCallbacksAndMessages(this);
    }
}
