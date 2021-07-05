package app.lonzh.lisper.fragment.mine

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.databinding.FragmentTitleListBinding
import app.lonzh.lisper.event.UnCollectEvent
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.request.mine.CollectRequestViewModel
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
 * @CreateDate:     2021/6/25 2:24 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/25 2:24 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CollectFragment : LisperFragment<CollectRequestViewModel, FragmentTitleListBinding>() {
    private var selectIndex = -1

    //加载更多真实的index
    private var realIndex: Int = PageRefreshLayout.startIndex

    override val layoutId: Int = R.layout.fragment_title_list

    override fun initView(savedInstanceState: Bundle?) {
        setLeftTitle(R.string.my_collection)

        binding.pageRefresh.run {
            onRefresh {
                viewModel.getCollect(index)
            }

            onLoadMore {
                index = realIndex
                viewModel.getCollect(index)
            }
        }

        binding.recycleView.run {
            linear().divider(R.drawable.driver_black_line).setup {
                addType<ArticleBean> {
                    when(envelopePic.isEmpty()){
                        true -> R.layout.item_home_list
                        else -> R.layout.item_article_list
                    }
                }

                onClick(R.id.article_list, R.id.iv_article_collect){
                    selectIndex = modelPosition
                    when(it){
                        R.id.article_list -> {
                            val bundle = WebFragmentArgs(getModel<ArticleBean>().title, getModel<ArticleBean>().link,
                                getModel<ArticleBean>().author, getModel<ArticleBean>().id).toBundle()
                            nav(R.id.action_collectFragment_to_webFragment, bundle)
                        }
                        R.id.iv_article_collect -> {
                            viewModel.unCollectArticle(getModel())
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

    override fun createObserver() {
        viewModel.resultLiveData.observe(viewLifecycleOwner){ list ->
            binding.pageRefresh.run {
                //(列表界面需要)
                if(index == PageRefreshLayout.startIndex){ // 刷新
                    realIndex = index + 1
                } else {
                    realIndex += 1
                }
                addData(list.datas) {
                    !list.over
                }
            }
        }

        viewModel.unCollectArticleLiveData.observe(viewLifecycleOwner){
            binding.recycleView.bindingAdapter.run {
                LiveEventBus.get<UnCollectEvent>(UnCollectEvent::class.java.simpleName).post(
                    UnCollectEvent(getModel<ArticleBean>(selectIndex).originId)
                )
                //removeAt 参数index   remove  参数model
                mutable.removeAt(selectIndex)
                notifyItemRemoved(selectIndex)
                models?.isEmpty()?.run {
                    if(this) binding.pageRefresh.showEmpty()
                }
            }
        }
    }
}