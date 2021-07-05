package app.lonzh.lisper.fragment.mine

import android.os.Bundle
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
import app.lonzh.lisper.widget.dialog.MessageDialog
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ClickUtils
import com.jeremyliao.liveeventbus.LiveEventBus
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

    private val appDataViewModel: AppDataViewModel by activityViewModels()

    private val systemStateViewModel: SystemStateViewModel by viewModels()

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        setLeftTitle(R.string.setting)

        systemStateViewModel.run {
            version.set("v${AppUtils.getAppVersionName()}")
            cache.set(DataCleanManager.getTotalCacheSize(requireActivity()))
        }

        binding.vm = systemStateViewModel

        ClickUtils.applySingleDebouncing(
            arrayOf(
                binding.tvLogout,
                binding.tvProject,
                binding.tvClearCache,
                binding.tvCacheNext
            )
        ) {
            when (it) {
                binding.tvLogout -> {
                    activity?.let { act ->
                        MessageDialog.Builder(act, onConfirm = {
                            viewModel.logout()
                        })
                            .setTitle(getString(R.string.logout))
                            .create()
                            .show()
                    }
                }
                binding.tvProject -> {
                    val bundle = WebFragmentArgs(
                        getString(R.string.tab_project),
                        systemStateViewModel.webUrl.get()!!,
                        "",
                        -1
                    ).toBundle()
                    nav(R.id.action_settingFragment_to_webFragment, bundle)
                }
                binding.tvClearCache,
                binding.tvCacheNext -> {
                    activity?.let { act ->
                        MessageDialog.Builder(act, onConfirm = {
                            DataCleanManager.clearAllCache(requireActivity())
                            systemStateViewModel.cache.set(
                                DataCleanManager.getTotalCacheSize(
                                    requireActivity()
                                )
                            )
                        })
                            .setTitle(getString(R.string.clear_cache))
                            .create()
                            .show()
                    }
                }
            }
        }
    }

    /**
     * 当前Fragment绑定的视图布局
     */
    override val layoutId: Int = R.layout.fragment_setting

    override fun createObserver() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
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