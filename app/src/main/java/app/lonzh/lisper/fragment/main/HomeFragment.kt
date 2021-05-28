package app.lonzh.lisper.fragment.main

import android.os.Bundle
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.lisper.R
import app.lonzh.lisper.adapter.HomeBannerAdapter
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.BannerBean
import app.lonzh.lisper.data.HomeBanner
import app.lonzh.lisper.databinding.FragmentHomeBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.vm.request.main.HomeRequestViewModel
import com.blankj.utilcode.util.ClickUtils
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.logcat.LogCat
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator

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
class HomeFragment : BaseVmDbFragment<HomeRequestViewModel, FragmentHomeBinding>() {

    private val homeBanner: HomeBanner by lazy{ HomeBanner(null) }

    companion object{
        @JvmStatic
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_home

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        ClickUtils.applySingleDebouncing(binding.tvHomeSearch) {
            toast("跳转搜索")
        }

        binding.homeRecycle.linear().divider(R.drawable.driver_black_line).setup {
            addType<ArticleBean>(R.layout.item_home_list)
            addType<HomeBanner>(R.layout.item_home_banner)

            onBind {
                when(itemViewType){
                    R.layout.item_home_banner ->{
                        val banner = findView<Banner<BannerBean, HomeBannerAdapter>>(R.id.home_banner)
                        banner.addBannerLifecycleObserver(viewLifecycleOwner)
                            .setAdapter(HomeBannerAdapter(getModel<HomeBanner>().list))
                            .setIndicator(CircleIndicator(mActivity))
                            .setOnBannerListener { data, _ ->
                                LogCat.e((data as BannerBean).url)
                            }
                    }
                }
            }

            onClick(R.id.home_list, R.id.iv_home_collect){
                when(it){
                    R.id.home_list -> {}
                    R.id.iv_home_collect -> {
                        nav(R.id.action_main_fragment_to_loginFragment)
                    }
                    else ->{}
                }
            }
        }

        ClickUtils.applySingleDebouncing(binding.ivAdd){
            nav(R.id.action_main_fragment_to_publishFragment)
        }

        binding.pageRefresh.run {
            onRefresh {
                viewModel.getHomeBanner()
                viewModel.getHomeArticles(index)
            }

            onLoadMore {
                viewModel.getHomeArticles(index)
            }
        }
    }

    override fun lazyLoad() {
        binding.pageRefresh.autoRefresh()
    }

    override fun createObserver() {
        viewModel.homeBannerLiveData.observe(viewLifecycleOwner, {
            homeBanner.list = it
            if(binding.homeRecycle.bindingAdapter.headerCount != 0){
                binding.homeRecycle.bindingAdapter.clearHeader(false)
            }
            binding.homeRecycle.bindingAdapter.addHeader(homeBanner)
        })
        viewModel.homeArticlesLiveData.observe(viewLifecycleOwner, {
            binding.pageRefresh.run {
                addData(it.datas) {
                    index < it.pageCount
                }
            }
        })
    }
}