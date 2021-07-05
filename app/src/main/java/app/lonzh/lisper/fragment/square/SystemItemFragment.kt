package app.lonzh.lisper.fragment.square

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.baselibrary.util.Constant
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.databinding.FragmentTitleListBinding
import app.lonzh.lisper.event.LoginEvent
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.request.square.SystemItemRequestViewModel
import com.blankj.utilcode.util.ClickUtils
import com.drake.brv.PageRefreshLayout.Companion.startIndex
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
 * @CreateDate:     2021/6/28 4:22 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/28 4:22 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SystemItemFragment : LisperFragment<SystemItemRequestViewModel, FragmentTitleListBinding>() {

    private var selectIndex = -1
    //加载更多真实的index(列表界面需要)
    private var realIndex: Int = startIndex

    override val layoutId: Int = R.layout.fragment_title_list

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.run {
            setTitle(SystemItemFragmentArgs.fromBundle(this).title)
            val cid = SystemItemFragmentArgs.fromBundle(this).id

            binding.pageRefresh.run {
                onRefresh {
                    viewModel.getArticles(index, cid)
                }

                onLoadMore {
                    index = realIndex
                    viewModel.getArticles(index, cid)
                }
            }

            binding.recycleView.run {
                linear().divider(R.drawable.driver_black_line).setup {
                    addType<ArticleBean>(R.layout.item_home_list)

                    onClick(R.id.article_list, R.id.iv_article_collect){
                        selectIndex = modelPosition
                        when(it){
                            R.id.article_list -> {
                                val bundle = WebFragmentArgs(getModel<ArticleBean>().title, getModel<ArticleBean>().link,
                                    getModel<ArticleBean>().author, getModel<ArticleBean>().id).toBundle()
                                nav(R.id.action_systemItemFragment_to_webFragment, bundle)
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
    }

    override fun lazyLoad() {
        binding.pageRefresh.showLoading(tag  = StateData(-1, getString(R.string.lisper_request)), refresh = false)
    }

    private fun collectArticle(articleBean: ArticleBean){
        if(!isLogin()){
            nav(R.id.action_systemItemFragment_to_loginFragment)
            return
        }
        if(articleBean.collect){
            viewModel.unCollectArticle(articleBean)
        } else {
            viewModel.collectArticle(articleBean)
        }
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
        viewModel.articlesLiveData.observe(viewLifecycleOwner){
            binding.pageRefresh.run {
                if(index == startIndex){ // 刷新
                    realIndex = index + 1
                } else {
                    realIndex += 1
                }
                addData(it.datas) {
                    !it.over
                }
            }
        }

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
    }
}