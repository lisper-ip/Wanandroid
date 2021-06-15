package app.lonzh.lisper.data

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/8 7:14 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/8 7:14 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class Tab(
    val children: List<Children>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class Children(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)