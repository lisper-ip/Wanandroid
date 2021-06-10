package app.lonzh.lisper.fragment.main

import android.os.Bundle
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentSquareBinding
import app.lonzh.lisper.fragment.base.LisperFragment

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
class SquareFragment : LisperFragment<BaseViewModel, FragmentSquareBinding>() {

    companion object{
        @JvmStatic
        fun getInstance(): SquareFragment {
            return SquareFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_square

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {

    }
}