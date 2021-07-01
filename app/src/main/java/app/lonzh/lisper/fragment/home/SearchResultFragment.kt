package app.lonzh.lisper.fragment.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.baselibrary.util.Constant
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.HomeBanner
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.databinding.FragmentSearchResultBinding
import app.lonzh.lisper.event.LoginEvent
import app.lonzh.lisper.event.SearchEvent
import app.lonzh.lisper.event.UnCollectEvent
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.request.home.SearchResultRequestViewModel
import com.blankj.utilcode.util.ClickUtils
import com.drake.brv.PageRefreshLayout
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/29 3:21 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/29 3:21 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SearchResultFragment : LisperFragment<SearchResultRequestViewModel, FragmentSearchResultBinding>() {

    private var selectIndex = -1

    //加载更多真实的index
    private var realIndex: Int = PageRefreshLayout.startIndex

    override fun layoutId(): Int = R.layout.fragment_search_result

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            binding.tvSearch.text = SearchFragmentArgs.fromBundle(it).keyword
        }

        binding.pageRefresh.run {
            onRefresh {
                arguments?.let {
                    viewModel.search(index, SearchFragmentArgs.fromBundle(it).keyword)
                }
            }

            onLoadMore {
                index = realIndex
                arguments?.let {
                    viewModel.search(index, SearchFragmentArgs.fromBundle(it).keyword)
                }
            }
        }

        binding.recycleView.run {
            linear().divider(R.drawable.driver_black_line).setup {
                addType<ArticleBean>(R.layout.item_home_list)
                addType<HomeBanner>(R.layout.item_home_banner)

                onClick(R.id.article_list, R.id.iv_article_collect){
                    selectIndex = modelPosition
                    when(it){
                        R.id.article_list -> {
                            val bundle = WebFragmentArgs(getModel<ArticleBean>().title, getModel<ArticleBean>().link,
                                getModel<ArticleBean>().author, getModel<ArticleBean>().id).toBundle()
                            nav(R.id.action_searchResultFragment_to_webFragment, bundle)
                        }
                        R.id.iv_article_collect -> {
                            collectArticle(getModel())
                        }
                        else ->{}
                    }
                }
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
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

        ClickUtils.applySingleDebouncing(arrayOf(binding.btnFloat, binding.tvSearch)){
            when(it){
                binding.btnFloat -> binding.recycleView.smoothScrollToPosition(0)
                binding.tvSearch -> back()
                else ->{}
            }
        }
    }

    private fun collectArticle(articleBean: ArticleBean){
        if(!isLogin()){
            nav(R.id.action_searchResultFragment_to_loginFragment)
            return
        }
        if(articleBean.collect){
            viewModel.unCollectArticle(articleBean)
        } else {
            viewModel.collectArticle(articleBean)
        }
    }

    override fun lazyLoad() {
        binding.pageRefresh.showLoading(tag  = StateData(-1, getString(R.string.lisper_request)), refresh = false)
        LiveEventBus.get<SearchEvent>(SearchEvent::class.java.simpleName).post(SearchEvent())
    }

    override fun showEmptyView() {
        binding.pageRefresh.showEmpty()
    }

    override fun showErrorView(msg: String) {
        binding.pageRefresh.run {
            if(loaded){
                finish(success = false, hasMore = true)
                toast(msg)
            } else {
                showError(StateData(R.drawable.ic_error, msg))
            }
        }
    }

    override fun createObserver() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, {
            binding.pageRefresh.run {
                if(index == PageRefreshLayout.startIndex){ // 刷新
                    realIndex = index + 1
                } else {
                    realIndex += 1
                }
                addData(it.datas) {
                    !it.over
                }
            }
        })
        viewModel.collectArticleLiveData.observe(viewLifecycleOwner){
            binding.recycleView.bindingAdapter.getModel<ArticleBean>(selectIndex).run {
                collect = !collect
                notifyChange()
            }
        }

        viewModel.unCollectArticleLiveData.observe(viewLifecycleOwner){
            binding.recycleView.bindingAdapter.getModel<ArticleBean>(selectIndex).run {
                collect = !collect
                notifyChange()
            }
        }

        LiveEventBus.get<LoginEvent>(LoginEvent::class.java.simpleName).observe(viewLifecycleOwner){
            postDelayed({
                binding.pageRefresh.refresh()
            }, Constant.RELAY_LOAD)
        }

        LiveEventBus.get<UnCollectEvent>(UnCollectEvent::class.java.simpleName).observe(viewLifecycleOwner){ event ->
            binding.recycleView.bindingAdapter.run {
                models?.mapIndexed{ _, it ->
                    if(it is ArticleBean){
                        if(it.id == event.id){
                            it.collect = false
                            it.notifyChange()
                        }
                    }
                }
            }
        }
    }
}