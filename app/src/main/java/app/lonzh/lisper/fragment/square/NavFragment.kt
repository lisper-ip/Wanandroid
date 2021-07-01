package app.lonzh.lisper.fragment.square

import android.os.Bundle
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentNavBinding
import app.lonzh.lisper.fragment.base.LisperFragment

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/1 3:15 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/1 3:15 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class NavFragment : LisperFragment<BaseViewModel, FragmentNavBinding>() {
    companion object {
        @JvmStatic
        fun getInstance(): NavFragment {
            return NavFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_nav

    override fun initView(savedInstanceState: Bundle?) {

    }
}