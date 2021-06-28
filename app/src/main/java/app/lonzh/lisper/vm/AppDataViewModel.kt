package app.lonzh.lisper.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.lonzh.lisper.data.UserInfo
import app.lonzh.lisper.utils.MMKVUtil

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/8 3:05 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/8 3:05 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class AppDataViewModel : ViewModel(){
    /**
     * 用户登录信息
     */
    val userInfo by lazy { MutableLiveData<UserInfo>() }

    init {
        val user = MMKVUtil.getObject(MMKVUtil.USER, UserInfo::class.java)
        user?.let {
            userInfo.value = user
        }
    }
}