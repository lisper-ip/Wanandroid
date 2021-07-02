package app.lonzh.lisper.data

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/2 2:14 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/2 2:14 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class HotWeb(
    val category: String,
    val icon: String,
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)