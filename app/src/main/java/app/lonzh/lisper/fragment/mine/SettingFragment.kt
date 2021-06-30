package app.lonzh.lisper.fragment.mine

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentSettingBinding
import app.lonzh.lisper.event.LoginEvent
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.utils.DataCleanManager
import app.lonzh.lisper.utils.MMKVUtil
import app.lonzh.lisper.vm.AppDataViewModel
import app.lonzh.lisper.vm.request.mine.SettingRequestViewModel
import app.lonzh.lisper.vm.state.mine.SystemStateViewModel
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ClickUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.pedaily.yc.ycdialoglib.fragment.CustomDialogFragment
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

    private val systemStateViewModel: SystemStateViewModel by viewModels()
    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        setLeftTitle(R.string.setting)

        systemStateViewModel.run {
            version.set("V${AppUtils.getAppVersionName()}")
            cache.set(DataCleanManager.getTotalCacheSize(requireActivity()))
        }

        binding.vm = systemStateViewModel

        ClickUtils.applySingleDebouncing(arrayOf(binding.tvLogout, binding.tvProject, binding.tvClearCache, binding.tvCacheNext)){
            when(it){
                binding.tvLogout -> {
                    CustomDialogFragment.create(childFragmentManager)
                        .setTitle(getString(R.string.logout))
                        .setCancelContent(getString(R.string.cancel))
                        .setOkContent(getString(R.string.confirm))
                        .setOkColor(ContextCompat.getColor(requireActivity(), R.color.red_text))
                        .setCancelListener {
                            CustomDialogFragment.dismissDialogFragment()
                        }
                        .setOkListener {
                            CustomDialogFragment.dismissDialogFragment()
                            viewModel.logout()
                        }
                        .setDimAmount(0.2f)
                        .setCancelOutside(true)
                        .show()
                }
                binding.tvProject -> {
                    val bundle = WebFragmentArgs(getString(R.string.tab_project), systemStateViewModel.webUrl.get()!!, "", -1).toBundle()
                    nav(R.id.action_settingFragment_to_webFragment, bundle)
                }
                binding.tvClearCache,
                binding.tvCacheNext ->{
                    CustomDialogFragment.create(childFragmentManager)
                        .setTitle(getString(R.string.clear_cache))
                        .setCancelContent(getString(R.string.cancel))
                        .setOkColor(ContextCompat.getColor(requireActivity(), R.color.red_text))
                        .setCancelListener {
                            CustomDialogFragment.dismissDialogFragment()
                        }
                        .setOkListener {
                            CustomDialogFragment.dismissDialogFragment()
                            DataCleanManager.clearAllCache(requireActivity())
                            systemStateViewModel.cache.set(DataCleanManager.getTotalCacheSize(requireActivity()))
                        }
                        .setDimAmount(0.2f)
                        .setCancelOutside(true)
                        .show()
                }
            }
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
            LiveEventBus.get<LoginEvent>(LoginEvent::class.java.simpleName).post(LoginEvent(false))
            back()
        }
    }
}