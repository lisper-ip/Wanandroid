package app.lonzh.lisper.constant

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 2:40 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 2:40 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
object UrlConstant {
    @Domain
    const val testUrl = "https://www.wanandroid.com/"

    @JvmField
    @DefaultDomain
    var baseUrl = testUrl
}