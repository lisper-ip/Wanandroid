package app.lonzh.lisper.fragment.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.R
import app.lonzh.lisper.adapter.HomeBannerAdapter
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.BannerBean
import app.lonzh.lisper.data.HomeBanner
import app.lonzh.lisper.databinding.FragmentHomeBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.base.LisperFragment
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
class HomeFragment : LisperFragment<HomeRequestViewModel, FragmentHomeBinding>() {

    private var selectIndex = -1

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
                selectIndex = modelPosition
                when(it){
                    R.id.home_list -> {}
                    R.id.iv_home_collect -> {
                        collectArticle(getModel())
                    }
                    else ->{}
                }
            }
        }

        ClickUtils.applySingleDebouncing(arrayOf(binding.ivAdd, binding.btnFloat)){
            when(it.id){
                R.id.iv_add -> nav(R.id.action_main_fragment_to_publishFragment)
                R.id.btn_float -> binding.homeRecycle.smoothScrollToPosition(0)
                else ->{}
            }

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

        binding.homeRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.btnFloat.visibility == View.VISIBLE){
                    binding.btnFloat.hide()
                }else if (dy < 0 && binding.btnFloat.visibility != View.VISIBLE){
                    binding.btnFloat.show()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(-1)){
                    //到达顶部
                    binding.btnFloat.hide()
                }
            }
        })
    }

    private fun collectArticle(articleBean: ArticleBean){
        if(!isLogin()){
            nav(R.id.action_main_fragment_to_loginFragment)
            return
        }
        if(articleBean.collect){
            viewModel.unCollectArticle(articleBean)
        } else {
            viewModel.collectArticle(articleBean)
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
        viewModel.collectArticleLiveData.observe(viewLifecycleOwner){
            val index = selectIndex.plus(binding.homeRecycle.bindingAdapter.headerCount)
            val articleBean = binding.homeRecycle.bindingAdapter.getModel<ArticleBean>(index)
            articleBean.collect = !articleBean.collect
            binding.homeRecycle.bindingAdapter.notifyItemChanged(selectIndex.plus(binding.homeRecycle.bindingAdapter.headerCount))
        }

        viewModel.unCollectArticleLiveData.observe(viewLifecycleOwner){
            val index = selectIndex.plus(binding.homeRecycle.bindingAdapter.headerCount)
            val articleBean = binding.homeRecycle.bindingAdapter.getModel<ArticleBean>(index)
            articleBean.collect = !articleBean.collect
            binding.homeRecycle.bindingAdapter.notifyItemChanged(index)
        }
    }
}