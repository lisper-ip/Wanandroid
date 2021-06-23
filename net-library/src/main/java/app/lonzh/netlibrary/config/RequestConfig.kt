package app.lonzh.netlibrary.config

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 5:33 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 5:33 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class RequestConfig {
    //是否需要请求还没有返回， 再次请求，当设置值以后连接发送请求时，上次值没有返回之前将不会再次请求
    var tag: String = ""
    //是否显示网络加载框
    var showLoading: Boolean = true
    //网络加载框上的文本
    var loadingMessage: String = ""

    fun setTag(tag: String) = apply {
        this.tag = tag
    }

    fun isShowLoading(isShow: Boolean) = apply {
        this.showLoading = isShow
    }

    fun loadingMessage(msg: String) = apply {
        this.loadingMessage = msg
    }
}