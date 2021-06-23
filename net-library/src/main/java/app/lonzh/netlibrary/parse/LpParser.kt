package app.lonzh.netlibrary.parse

import app.lonzh.netlibrary.ext.success
import app.lonzh.netlibrary.response.LpResponse
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
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
@Parser(name = "LpResponse")
open class LpParser<T> : TypeParser<T> {

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
    @Throws(IOException::class)
    override fun onParse(response: Response): T {
        val res: LpResponse<T> = response.convertTo(LpResponse::class.java, *types)
        var t = res.data
        if(t == null && types[0] === Any::class.java){
            t = res.errorMsg as T
        }
        if(!res.success){
            throw ParseException(res.errorCode.toString(), res.errorMsg, response)
        }
        return t
    }
}