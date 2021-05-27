package app.lonzh.lisper.data

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/21/21 11:20 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/21/21 11:20 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
//{
//		"desc": "扔物线",
//		"id": 29,
//		"imagePath": "https://wanandroid.com/blogimgs/31c2394c-b07c-4699-afd1-95aa7a3bebc6.png",
//		"isVisible": 1,
//		"order": 0,
//		"title": "View 嵌套太深会卡？来用 Jetpack Compose，随便套&mdash;&mdash;Compose 的 Intrinsic Measurement",
//		"type": 0,
//		"url": "https://www.bilibili.com/video/BV1ZA41137gr"
//	},
data class HomeBanner(
    var list: MutableList<BannerBean>?
)

data class BannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)