package app.lonzh.commonlibrary.vm

import androidx.lifecycle.ViewModel
import app.lonzh.commonlibrary.event.SingleLiveEvent
import app.lonzh.commonlibrary.exception.AppException
import app.lonzh.netlibrary.config.RequestConfig

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 4:57 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 4:57 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class BaseViewModel : ViewModel(){
    /**
     * 请求开始
     */
    private var showStartEvent: SingleLiveEvent<Void>? = null
    /**
     * 显示Loading对话框
     */
    private var showLoadingEvent: SingleLiveEvent<String>? = null
    /**
     * 显示错误信息
     */
    private var showErrorEvent: SingleLiveEvent<String>? = null
    /**
     * 请求结束
     */
    private var showFinishEvent: SingleLiveEvent<String>? = null

    /**以下是替换页面状态布局 多用于列表**/
    /**
     * 显示Loading布局
     */
    private var showLoadingViewEvent: SingleLiveEvent<String>? = null
    /**
     * 显示空布局
     */
    private var showEmptyViewEvent: SingleLiveEvent<Void>? = null
    /**
     * 显示错误布局
     */
    private var showErrorViewEvent: SingleLiveEvent<String>? = null

    /**
     * 针对同一请求在没有返回值之前能不能再次请求
     */
    var tagList = mutableListOf<String>()

    fun getShowStartEvent(): SingleLiveEvent<Void>{
        showStartEvent = createLiveData(showStartEvent)
        return showStartEvent as SingleLiveEvent<Void>
    }
    fun getShowLoadingEvent(): SingleLiveEvent<String>{
        showLoadingEvent = createLiveData(showLoadingEvent)
        return showLoadingEvent as SingleLiveEvent<String>
    }
    fun getShowErrorEvent(): SingleLiveEvent<String>{
        showErrorEvent = createLiveData(showErrorEvent)
        return showErrorEvent as SingleLiveEvent<String>
    }
    fun getShowFinishEvent(): SingleLiveEvent<String>{
        showFinishEvent = createLiveData(showFinishEvent)
        return showFinishEvent as SingleLiveEvent<String>
    }

    fun getShowLoadingViewEvent(): SingleLiveEvent<String>{
        showLoadingViewEvent = createLiveData(showLoadingViewEvent)
        return showLoadingViewEvent as SingleLiveEvent<String>
    }
    fun getShowEmptyViewEvent(): SingleLiveEvent<Void>{
        showEmptyViewEvent = createLiveData(showEmptyViewEvent)
        return showEmptyViewEvent as SingleLiveEvent<Void>
    }
    fun getShowErrorViewEvent(): SingleLiveEvent<String>{
        showErrorViewEvent = createLiveData(showErrorViewEvent)
        return showErrorViewEvent as SingleLiveEvent<String>
    }

    private fun <T> createLiveData(liveData: SingleLiveEvent<T>?): SingleLiveEvent<T> {
        var mLiveData = liveData
        if(mLiveData == null){
            mLiveData = SingleLiveEvent()
        }
        return mLiveData
    }
    /**
     * @method
     * @description 检查请求配置参数TAG
     * @date: 2020/6/23 10:00 AM
     * @author: lisper
     * @param
     * @return
     */
    fun checkRequestConfig(requestConfig: RequestConfig?): Boolean{
        return if(requestConfig == null){
            true
        }else{
            !(requestConfig.tag.isNotEmpty() && tagList.contains(requestConfig.tag))
        }
    }
}