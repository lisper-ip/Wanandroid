package app.lonzh.lisper.fragment.tab

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.baselibrary.util.Constant
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.databinding.FragmentListBinding
import app.lonzh.lisper.event.LoginEvent
import app.lonzh.lisper.event.UnCollectEvent
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.main.TabFragment
import app.lonzh.lisper.vm.request.tab.TabItemRequestViewModel
import app.lonzh.netlibrary.response.PageList
import com.blankj.utilcode.util.ClickUtils
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
 * @CreateDate:     2021/6/8 7:09 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/8 7:09 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class TabItemFragment : LisperFragment<TabItemRequestViewModel, FragmentListBinding>() {

    private var selectIndex = -1

    //加载更多真实的index(列表界面需要)
    private var realIndex: Int = START_INDEX

    companion object{
        @JvmStatic
        fun getInstance(): TabItemFragment {
            return TabItemFragment()
        }

        const val TAB_ID = "tab_id"

        //开始页数(如果起始页不是默认设置则 列表界面需要)
        const val START_INDEX = 1
    }

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        binding.pageRefresh.run {
            onRefresh {
                //接口页数从1开始， 如果是0不需要设置(列表界面需要)
                index = START_INDEX
                getArticleList(index)
            }

            onLoadMore {
                //(列表界面需要)
                index = realIndex
                getArticleList(index)
            }
        }
        binding.recycleView.run {
            linear().divider(R.drawable.driver_black_line).setup {
                arguments?.let {
                    when(it.getString(TabFragment.TAB_TYPE)){
                        TabFragment.TAB_PROJECT -> addType<ArticleBean>(R.layout.item_article_list)
                        TabFragment.TAB_WXARTICLE -> addType<ArticleBean>(R.layout.item_home_list)
                        else ->{}
                    }
                }

                onClick(R.id.article_list, R.id.iv_article_collect){
                    selectIndex = modelPosition
                    when(it){
                        R.id.article_list -> {
                            val bundle = WebFragmentArgs(getModel<ArticleBean>().title, getModel<ArticleBean>().link,
                                getModel<ArticleBean>().author, getModel<ArticleBean>().id).toBundle()
                            nav(R.id.action_main_fragment_to_webFragment, bundle)
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

        ClickUtils.applySingleDebouncing(binding.btnFloat){
            binding.recycleView.smoothScrollToPosition(0)
        }
    }

    private fun getArticleList(index: Int){
        arguments?.let {
            val tabId = it.getString(TAB_ID)
            when(it.getString(TabFragment.TAB_TYPE)){
                TabFragment.TAB_PROJECT -> viewModel.getArticleList(tabId, index)
                TabFragment.TAB_WXARTICLE -> viewModel.getWxArticleList(tabId, index)
                else ->{}
            }
        }
    }

    open fun repeatFresh(){
        binding.pageRefresh.autoRefresh()
        binding.recycleView.scrollToPosition(0)
        binding.btnFloat.hide()
    }

    override fun lazyLoad() {
        binding.pageRefresh.showLoading(tag  = StateData(-1, getString(R.string.lisper_request)), refresh = false)
    }

    //(列表界面需要)
    override fun showEmptyView() {
        binding.pageRefresh.showEmpty()
    }

    //(列表界面需要)
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

    override fun createObserver() {
        arguments?.let {
            when(it.getString(TabFragment.TAB_TYPE)){
                TabFragment.TAB_PROJECT -> viewModel.articleListLiveData.observe(viewLifecycleOwner){ list ->
                    handlerList(list)
                }
                TabFragment.TAB_WXARTICLE -> viewModel.wxArticleListLiveData.observe(viewLifecycleOwner){ list ->
                    handlerList(list)
                }
                else ->{}
            }
        }

        viewModel.collectArticleLiveData.observe(viewLifecycleOwner){
            val index = selectIndex.plus(binding.recycleView.bindingAdapter.headerCount)
            val articleBean = binding.recycleView.bindingAdapter.getModel<ArticleBean>(index)
            articleBean.collect = !articleBean.collect
            binding.recycleView.bindingAdapter.notifyItemChanged(selectIndex.plus(binding.recycleView.bindingAdapter.headerCount))
        }

        viewModel.unCollectArticleLiveData.observe(viewLifecycleOwner){
            val index = selectIndex.plus(binding.recycleView.bindingAdapter.headerCount)
            val articleBean = binding.recycleView.bindingAdapter.getModel<ArticleBean>(index)
            articleBean.collect = !articleBean.collect
            binding.recycleView.bindingAdapter.notifyItemChanged(index)
        }

        LiveEventBus.get<LoginEvent>(LoginEvent::class.java.simpleName).observe(viewLifecycleOwner){
            postDelayed({
                binding.pageRefresh.refresh()
            }, Constant.RELAY_LOAD)
        }

        LiveEventBus.get<UnCollectEvent>(UnCollectEvent::class.java.simpleName).observe(viewLifecycleOwner){ event ->
            binding.recycleView.bindingAdapter.run {
                var unCollectIndex = -1
                models?.mapIndexed{ index, it ->
                    if(it is ArticleBean){
                        if(it.id == event.id){
                            it.collect = false
                            unCollectIndex = index
                        }
                    }
                }
                if(unCollectIndex >= 0){
                    notifyItemChanged(unCollectIndex)
                }
            }
        }
    }

    private fun handlerList(list: PageList<ArticleBean>){
        binding.pageRefresh.run {
            //(列表界面需要)
            if(index == START_INDEX){ // 刷新
                realIndex = index + 1
            } else {
                realIndex += 1
            }
            addData(list.datas) {
                !list.over
            }
        }
    }

    /**
     * 当前Fragment绑定的视图布局
     */
    override fun layoutId(): Int = R.layout.fragment_list
}