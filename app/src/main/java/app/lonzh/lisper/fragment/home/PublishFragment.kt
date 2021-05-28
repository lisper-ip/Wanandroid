package app.lonzh.lisper.fragment.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentPublishBinding
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.vm.request.home.PublishRequestViewModel
import app.lonzh.lisper.vm.state.home.PublishStateViewModel
import com.blankj.utilcode.util.ClickUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    发布文章
 * @Author:         Lisper
 * @CreateDate:     5/27/21 4:44 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/27/21 4:44 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class PublishFragment : BaseVmDbFragment<PublishRequestViewModel, FragmentPublishBinding>() {

    private val publishStateViewModel: PublishStateViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_publish

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        binding.stateViewModel = publishStateViewModel

        ClickUtils.applySingleDebouncing(binding.tvShare){
            viewModel.shareArticle(publishStateViewModel)
        }
    }

    override fun createObserver() {
        viewModel.resultLiveData.observe(viewLifecycleOwner, {
            toast(getString(R.string.publish_success))
            back()
        })
    }
}