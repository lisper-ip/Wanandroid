package app.lonzh.lisper.fragment.tab

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.databinding.FragmentListBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.main.TabFragment
import app.lonzh.lisper.vm.request.tab.TabItemRequestViewModel
import app.lonzh.netlibrary.response.PageList
import com.blankj.utilcode.util.ClickUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.logcat.LogCat

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
    companion object{
        @JvmStatic
        fun getInstance(): TabItemFragment {
            return TabItemFragment()
        }

        const val TAB_ID = "tab_id"
    }

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        binding.pageRefresh.run {
            onRefresh {
                index = 1
                getArticleList(index)
            }

            onLoadMore {
                getArticleList(index)
            }
        }
        binding.recycleView.linear().divider(R.drawable.driver_black_line).setup {
            arguments?.let {
                when(it.getString(TabFragment.TAB_TYPE)){
                    TabFragment.TAB_PROJECT -> addType<ArticleBean>(R.layout.item_article_list)
                    TabFragment.TAB_WXARTICLE -> addType<ArticleBean>(R.layout.item_wxarticle_list)
                    else ->{}
                }
            }

            onClick(R.id.article_list, R.id.iv_article_collect){
                when(it){
                    R.id.article_list -> {}
                    R.id.iv_article_collect -> {
                        collectArticle()
                    }
                    else ->{}
                }
            }
        }

        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
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
    }

    override fun lazyLoad() {
        binding.pageRefresh.autoRefresh()
    }

    private fun collectArticle(){
        if(isLogin()){
            LogCat.e("请求收藏")
        } else {
            nav(R.id.action_main_fragment_to_loginFragment)
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
    }

    private fun handlerList(list: PageList<ArticleBean>){
        binding.pageRefresh.run {
            addData(list.datas) {
                index < list.pageCount
            }
        }
    }

    /**
     * 当前Fragment绑定的视图布局
     */
    override fun layoutId(): Int = R.layout.fragment_list
}