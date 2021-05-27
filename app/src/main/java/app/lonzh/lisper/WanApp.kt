package app.lonzh.lisper

import android.app.Application
import app.lonzh.commonlibrary.common.CommonApp
import app.lonzh.lisper.startup.CommonMainStartUp
import app.lonzh.lisper.startup.CommonThreadStartUp
import com.rousetime.android_startup.StartupManager

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 11:47 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 11:47 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class WanApp : CommonApp() {

    companion object{
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        StartupManager.Builder()
            .addStartup(CommonMainStartUp())
            .addStartup(CommonThreadStartUp())
            .build(this)
            .start()
            .await()
    }
}