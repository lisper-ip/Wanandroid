package app.lonzh.lisper.startup

import android.content.Context
import app.lonzh.lisper.BuildConfig
import com.jeremyliao.liveeventbus.LiveEventBus
import com.rousetime.android_startup.AndroidStartup

/**
 *
 * @ProjectName:    lisper
 * @Description:    子线程初始化
 * @Author:         Lisper
 * @CreateDate:     5/19/21 2:18 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 2:18 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CommonThreadStartUp : AndroidStartup<String>(){
    /**
     * Return true call the create function on main thread otherwise false.
     */
    override fun callCreateOnMainThread(): Boolean = false

    /**
     * Contains all of the necessary operations to initialize the component.
     * and returns an instance of `T`
     *
     * @param [context]
     */
    override fun create(context: Context): String? {
        LiveEventBus.config().enableLogger(BuildConfig.DEBUG)
            .lifecycleObserverAlwaysActive(false)
        return javaClass.simpleName
    }

    /**
     * Return true block the main thread until the startup completed otherwise false.
     *
     * Note: If the function [callCreateOnMainThread] return true, main thread default block.
     */
    override fun waitOnMainThread(): Boolean = false
}