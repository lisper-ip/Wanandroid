package app.lonzh.lisper.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.lisper.R
import app.lonzh.lisper.WanApp
import app.lonzh.lisper.databinding.FragmentPublishBinding
import app.lonzh.lisper.vm.request.PublishRequestViewModel
import app.lonzh.lisper.vm.state.PublishStateViewModel
import com.blankj.utilcode.util.ClickUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/27/21 4:44 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/27/21 4:44 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class PublishFragment : BaseVmDbFragment<PublishRequestViewModel, FragmentPublishBinding>() {

    val publishStateViewModel: PublishStateViewModel by viewModels()

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
            findNavController().popBackStack()
        })
    }
}