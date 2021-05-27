package app.lonzh.netlibrary.parse

import app.lonzh.netlibrary.ext.success
import app.lonzh.netlibrary.response.LpResponse
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import rxhttp.wrapper.utils.GsonUtil
import rxhttp.wrapper.utils.convert
import java.lang.reflect.Type

/**
 *
 * @Description:    自定义数据解析
 * @Author:         Lisper
 * @CreateDate:     2020/6/23 9:48 AM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/6/23 9:48 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
@Parser(name = "LpResponse", wrappers = [List::class])
open class LpParser<T> : AbstractParser<T> {

    protected constructor() : super()
    constructor(type: Type) : super(type)

    /**
     * @method
     * @description 解析方法 可根据实际返回修改
     * @date: 2020/6/23 9:59 AM
     * @author: lisper
     * @param
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    override fun onParse(response: Response): T {
        val type = ParameterizedTypeImpl[LpResponse::class.java, String::class.java]
        val res: LpResponse<String> = response.convert(type)
        var t: T? = null
        if (res.success) {
            t = GsonUtil.getObject(res.data, mType)
        }
        if (t == null && mType == String::class.java) {
            t = res.errorMsg as T
        }
        if(!res.success ||t == null){
            throw ParseException(res.errorCode.toString(), res.errorMsg, response)
        }
        return t
    }

    companion object {
        @JvmStatic
        operator fun <T> get(type: Class<T>) = LpParser<T>(type)
    }
}