package app.lonzh.commonlibrary.common

import android.content.Context
import androidx.multidex.MultiDex
import app.lonzh.baselibrary.common.BaseApp

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 2:23 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 2:23 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class CommonApp : BaseApp() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}