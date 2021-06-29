package app.lonzh.lisper.data

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/29 5:47 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/29 5:47 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class Coin(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)