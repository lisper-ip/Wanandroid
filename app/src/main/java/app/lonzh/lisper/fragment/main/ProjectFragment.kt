package app.lonzh.lisper.fragment.main

import android.os.Bundle
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentProjectBinding

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
class ProjectFragment : BaseVmDbFragment<BaseViewModel, FragmentProjectBinding>() {

    companion object{
        @JvmStatic
        fun getInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_project

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {

    }
}