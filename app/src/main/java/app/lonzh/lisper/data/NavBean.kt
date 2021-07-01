package app.lonzh.lisper.data

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/1 4:14 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/1 4:14 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class NavBean(
    val articles: List<ArticleBean>,
    val cid: Int,
    val name: String
)