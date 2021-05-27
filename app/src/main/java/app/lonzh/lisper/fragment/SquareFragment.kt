package app.lonzh.lisper.fragment

import android.os.Bundle
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentHomeBinding
import app.lonzh.lisper.databinding.FragmentSquareBinding

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
class SquareFragment : BaseVmDbFragment<BaseViewModel, FragmentSquareBinding>() {

    companion object{
        @JvmStatic
        fun getInstance(): SquareFragment{
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