package app.lonzh.lisper.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/8 3:00 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/8 3:00 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
@Parcelize
open class UserInfo(
    val admin: Boolean,
    val chapterTops: List<Int>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) : Parcelable