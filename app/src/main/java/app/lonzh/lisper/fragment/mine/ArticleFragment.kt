package app.lonzh.lisper.fragment.mine

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.databinding.FragmentTitleListBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.request.mine.ArticleRequestViewModel
import com.blankj.utilcode.util.ClickUtils
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/30 10:37 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/30 10:37 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ArticleFragment : LisperFragment<ArticleRequestViewModel, FragmentTitleListBinding>() {

    private var selectIndex = -1

    //加载更多真实的index(列表界面需要)
    private var realIndex: Int = START_INDEX

    companion object{
        //开始页数(如果起始页不是默认设置则 列表界面需要)
        const val START_INDEX = 1
    }

    override fun layoutId(): Int = R.layout.fragment_title_list

    override fun initView(savedInstanceState: Bundle?) {
        setLeftTitle(R.string.my_article)

        binding.pageRefresh.run {
            onRefresh {
                //接口页数从1开始， 如果是0不需要设置(列表界面需要)
                index = START_INDEX
                viewModel.getArticles(index)
            }

            onLoadMore {
                //(列表界面需要)
                index = realIndex
                viewModel.getArticles(index)
            }
        }
        binding.recycleView.run {
            linear().divider(R.drawable.driver_black_line).setup {
                addType<ArticleBean>(R.layout.item_my_article)

                onClick(R.id.article_list, R.id.tv_delete){
                    selectIndex = modelPosition
                    val easySwipeMenuLayout = findView<EasySwipeMenuLayout>(R.id.easy_swipe)
                    easySwipeMenuLayout.resetStatus()
                    when(it){
                        R.id.article_list -> {
                            val bundle = WebFragmentArgs(getModel<ArticleBean>().title, getModel<ArticleBean>().link,
                                getModel<ArticleBean>().author, getModel<ArticleBean>().id).toBundle()
                            nav(R.id.action_articleFragment_to_webFragment, bundle)
                        }
                        R.id.tv_delete -> {
                            viewModel.delete(getModel<ArticleBean>().id)
                        }
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
        viewModel.articlesLiveData.observe(viewLifecycleOwner){
            binding.pageRefresh.run {
                //(列表界面需要)
                if(index == START_INDEX){ // 刷新
                    realIndex = index + 1
                } else {
                    realIndex += 1
                }
                addData(it.shareArticles.datas) {
                    !it.shareArticles.over
                }
            }
        }

        viewModel.deleteLiveData.observe(viewLifecycleOwner){
            binding.recycleView.bindingAdapter.run {
                mutable.remove(getModel(selectIndex))
                notifyItemRemoved(selectIndex)
                if(models.isNullOrEmpty()){
                    showEmptyView()
                }
            }
        }
    }
}