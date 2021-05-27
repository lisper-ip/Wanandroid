package app.lonzh.netlibrary.ext

import app.lonzh.netlibrary.event.ReturnCode
import app.lonzh.netlibrary.response.LpResponse

/**
 *
 * @ProjectName:    jiahe
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2020/10/1 8:51 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2020/10/1 8:51 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
val <T> LpResponse<T>.success: Boolean
    get() {
        return errorCode == ReturnCode.SUCCESS
    }