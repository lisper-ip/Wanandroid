package app.lonzh.lisper.fragment.mine

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentSettingBinding
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.utils.MMKVUtil
import app.lonzh.lisper.vm.AppDataViewModel
import app.lonzh.lisper.vm.request.mine.SettingRequestViewModel
import com.blankj.utilcode.util.ClickUtils
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cookie.ICookieJar


/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/23 2:59 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/23 2:59 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SettingFragment : LisperFragment<SettingRequestViewModel, FragmentSettingBinding>() {

    private val appDataViewModel : AppDataViewModel by activityViewModels()
    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        ClickUtils.applySingleDebouncing(binding.tvLogout){
            viewModel.logout()
        }
    }

    /**
     * 当前Fragment绑定的视图布局
     */
    override fun layoutId(): Int = R.layout.fragment_setting

    override fun createObserver() {
        viewModel.resultLiveData.observe(viewLifecycleOwner){
            dismissLoading()
            MMKVUtil.clearUser()
            val cookie = RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar
            cookie.removeAllCookie()
            appDataViewModel.userInfo.value = null
            back()
        }
    }
}