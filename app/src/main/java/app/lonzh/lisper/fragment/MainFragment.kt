package app.lonzh.lisper.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentMainBinding
import app.lonzh.lisper.ext.doSelected
import app.lonzh.lisper.ext.initFragment
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.main.*

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 3:53 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 3:53 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class MainFragment: LisperFragment<BaseViewModel, FragmentMainBinding>() {

    private val fragmentList = arrayListOf<Fragment>()

    init {
        fragmentList.apply {
            add(HomeFragment.getInstance())
            add(ProjectFragment.getInstance())
            add(SquareFragment.getInstance())
            add(OfficialAccountFragment.getInstance())
            add(MineFragment.getInstance())
        }
    }
    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        binding.mainViewpager.apply {
            initFragment(childFragmentManager, fragmentList).run {
                offscreenPageLimit = fragmentList.size
            }
            doSelected {
                binding.bottomNav.menu.getItem(it).isChecked = true
            }
        }
        binding.bottomNav.run {
            itemIconTintList = null
            setOnNavigationItemSelectedListener { item ->
                when(item.itemId){
                    R.id.menu_home -> binding.mainViewpager.setCurrentItem(0, false)
                    R.id.menu_project -> binding.mainViewpager.setCurrentItem(1, false)
                    R.id.menu_square -> binding.mainViewpager.setCurrentItem(2, false)
                    R.id.menu_official_account -> binding.mainViewpager.setCurrentItem(3, false)
                    R.id.menu_mine -> binding.mainViewpager.setCurrentItem(4, false)
                    else ->{}
                }
                true
            }
        }
    }

    override fun isStatusBarEnabled(): Boolean {
        return !super.isStatusBarEnabled()
    }

    /**
     * 当前Fragment绑定的视图布局
     */
    override fun layoutId(): Int = R.layout.fragment_main
}