package app.lonzh.lisper.ext

import android.app.Application
import app.lonzh.lisper.WanApp

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/27/21 5:41 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/27/21 5:41 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
val appContext: Application by lazy { WanApp.app }