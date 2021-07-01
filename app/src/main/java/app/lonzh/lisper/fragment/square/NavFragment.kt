package app.lonzh.lisper.fragment.square

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.NavBean
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.data.Tab
import app.lonzh.lisper.databinding.FragmentNavBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.request.square.NavRequestViewModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.flexbox.FlexboxLayoutManager

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/1 3:15 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/1 3:15 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class NavFragment : LisperFragment<NavRequestViewModel, FragmentNavBinding>() {
    companion object {
        @JvmStatic
        fun getInstance(): NavFragment {
            return NavFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_nav

    override fun initView(savedInstanceState: Bundle?) {
        binding.pageRefresh.run {
            onRefresh {
                viewModel.getNav()
            }
        }

        binding.recycleView.linear().setup {
            addType<NavBean>(R.layout.item_nav)

            onCreate {
                val flexRecycle = findView<RecyclerView>(R.id.flexbox_recycle)
                flexRecycle.layoutManager = FlexboxLayoutManager(mActivity)
                flexRecycle.setup {
                    addType<ArticleBean>(R.layout.item_nav_child)

                    onClick(R.id.tv_child_title){
                        val bundle = WebFragmentArgs(getModel<ArticleBean>().title, getModel<ArticleBean>().link).toBundle()
                        nav(R.id.action_main_fragment_to_webFragment, bundle)
                    }
                }
            }

            onBind {
                val flexRecycle = findView<RecyclerView>(R.id.flexbox_recycle)
                flexRecycle.models = getModel<NavBean>().articles
            }
        }
    }

    override fun lazyLoad() {
        binding.pageRefresh.showLoading(tag  = StateData(-1, getString(R.string.lisper_request)), refresh = false)
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
        viewModel.navLiveData.observe(viewLifecycleOwner){
            binding.pageRefresh.run {
                addData(it) {
                    false
                }
            }
        }
    }
}