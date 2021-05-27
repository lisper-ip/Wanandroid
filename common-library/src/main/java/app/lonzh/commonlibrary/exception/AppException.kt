package app.lonzh.commonlibrary.exception

import app.lonzh.commonlibrary.R
import app.lonzh.commonlibrary.ext.appContext

/**
 *
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2020/9/4 12:10 PM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/9/4 12:10 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class AppException(var errCode: String, error: String?, errorLog: String? = "") : Exception(error) {
    var errorMsg: String = error ?: appContext.getString(R.string.unknown_mistake)
    private var errorLog: String?

    init {
        this.errorLog = errorLog?:this.errorMsg
    }
}