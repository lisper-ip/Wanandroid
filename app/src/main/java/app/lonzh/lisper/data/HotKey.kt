package app.lonzh.lisper.data

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/1 7:00 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/1 7:00 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class HotKey(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)