package app.lonzh.lisper.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentSquareBinding
import app.lonzh.lisper.ext.doSelected
import app.lonzh.lisper.ext.initFragment
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.square.NavFragment
import app.lonzh.lisper.fragment.square.SystemFragment
import app.lonzh.lisper.vm.request.main.SquareRequestViewModel
import com.blankj.utilcode.util.StringUtils.getStringArray
import com.flyco.tablayout.listener.OnTabSelectListener

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
class SquareFragment : LisperFragment<SquareRequestViewModel, FragmentSquareBinding>() {

    companion object {
        @JvmStatic
        fun getInstance(): SquareFragment {
            return SquareFragment()
        }
    }

    private val fragmentList = arrayListOf<Fragment>()

    init {
        fragmentList.apply {
            add(SystemFragment.getInstance())
            add(NavFragment.getInstance())
        }
    }

    override fun layoutId(): Int = R.layout.fragment_square

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        val titles = getStringArray(R.array.square_title)
        binding.segmentTab.run {
            setTabData(titles)
            setOnTabSelectListener(object : OnTabSelectListener{
                override fun onTabSelect(position: Int) {
                    binding.squareViewpager.currentItem = position
                }

                override fun onTabReselect(position: Int) {
                }
            })
            binding.segmentTab.currentTab = 0
        }
        binding.squareViewpager.apply {
            initFragment(childFragmentManager, fragmentList).run {
                offscreenPageLimit = fragmentList.size
            }
            doSelected {
                binding.segmentTab.currentTab = it
            }
        }
    }
}