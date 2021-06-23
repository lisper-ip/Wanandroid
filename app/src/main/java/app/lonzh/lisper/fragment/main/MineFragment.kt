package app.lonzh.lisper.fragment.main

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentMineBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.AppDataViewModel
import com.blankj.utilcode.util.ClickUtils

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
class MineFragment : LisperFragment<BaseViewModel, FragmentMineBinding>() {

    private val appDataViewModel : AppDataViewModel by activityViewModels()

    companion object{
        @JvmStatic
        fun getInstance(): MineFragment {
            return MineFragment()
        }
    }


    override fun layoutId(): Int = R.layout.fragment_mine

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = appDataViewModel

        ClickUtils.applySingleDebouncing(arrayOf(binding.tvSetting, binding.tvCoins,
            binding.tvCollection, binding.tvArticle, binding.tvWeb, binding.tvName,
            binding.ivHead, binding.tvId, binding.tvCoinNext)){
            if(!isLogin()){
                nav(R.id.action_main_fragment_to_loginFragment)
                return@applySingleDebouncing
            }
            when(it.id){
                R.id.tv_setting -> nav(R.id.action_main_fragment_to_settingFragment)
                R.id.tv_web -> {}
                R.id.tv_coins -> {}
                R.id.tv_collection -> {}
                R.id.tv_article -> {}
                else ->{}
            }

        }
    }
}