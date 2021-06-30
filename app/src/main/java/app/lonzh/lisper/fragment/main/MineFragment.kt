package app.lonzh.lisper.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import app.lonzh.baselibrary.util.Constant
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.databinding.FragmentMineBinding
import app.lonzh.lisper.event.LoginEvent
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.AppDataViewModel
import app.lonzh.lisper.vm.request.main.MineRequestViewModel
import app.lonzh.lisper.vm.state.main.MineStateViewModel
import app.lonzh.netlibrary.constant.UrlConstant
import com.blankj.utilcode.util.ClickUtils
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 4:48 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 4:48 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class MineFragment : LisperFragment<MineRequestViewModel, FragmentMineBinding>() {

    private val appDataViewModel : AppDataViewModel by activityViewModels()

    private val mineStateViewModel: MineStateViewModel by viewModels()

    companion object{
        @JvmStatic
        fun getInstance(): MineFragment {
            return MineFragment()
        }
    }


    override fun layoutId(): Int = R.layout.fragment_mine

    override fun lazyLoad() {
        if(isLogin()){
            viewModel.getUserInfo()
        }
    }

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = appDataViewModel
        binding.coinVm = mineStateViewModel

        ClickUtils.applySingleDebouncing(arrayOf(binding.tvSetting, binding.tvCoins,
            binding.tvCollection, binding.tvArticle, binding.tvWeb, binding.tvName,
            binding.ivHead, binding.tvId, binding.tvCoinNext)){
            if(!isLogin()){
                nav(R.id.action_main_fragment_to_loginFragment)
                return@applySingleDebouncing
            }
            when(it.id){
                R.id.tv_setting -> nav(R.id.action_main_fragment_to_settingFragment)
                R.id.tv_web -> {
                    val bundle = WebFragmentArgs(getString(R.string.wan_android_web), UrlConstant.baseUrl, "", -1).toBundle()
                    nav(R.id.action_main_fragment_to_webFragment, bundle)
                }
                R.id.tv_coin_next,
                R.id.tv_coins -> nav(R.id.action_main_fragment_to_coinFragment)
                R.id.tv_collection -> nav(R.id.action_main_fragment_to_collectFragment)
                R.id.tv_article -> nav(R.id.action_main_fragment_to_articleFragment)
                else ->{}
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        viewModel.userInfoLiveData.observe(viewLifecycleOwner){
            mineStateViewModel.coinInfo.set(it.coinCount)
        }

        LiveEventBus.get<LoginEvent>(LoginEvent::class.java.simpleName).observe(viewLifecycleOwner){
            if(it.isLogin){
                postDelayed({
                    lazyLoad()
                }, Constant.RELAY_LOAD)
            } else {
                mineStateViewModel.coinInfo.set(0)
            }
        }
    }
}