package app.lonzh.netlibrary.response

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 11:24 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 11:24 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class LpResponse<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
)