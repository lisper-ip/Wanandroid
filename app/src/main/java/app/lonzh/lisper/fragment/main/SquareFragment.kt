package app.lonzh.lisper.fragment.main

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.Children
import app.lonzh.lisper.data.Tab
import app.lonzh.lisper.databinding.FragmentSquareBinding
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.request.main.SquareRequestViewModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.flexbox.FlexboxLayoutManager

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
class SquareFragment : LisperFragment<SquareRequestViewModel, FragmentSquareBinding>() {

    companion object {
        @JvmStatic
        fun getInstance(): SquareFragment {
            return SquareFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_square

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        binding.squareRefresh.run {
            onRefresh {
                requestData()
            }

            onLoading {

            }
        }
        binding.squareRecycle.linear().setup {
            addType<Tab>(R.layout.item_square)

            onCreate {
                val flexRecycle = findView<RecyclerView>(R.id.flexbox_recycle)
                flexRecycle.layoutManager = FlexboxLayoutManager(mActivity)
                flexRecycle.setup {
                    addType<Children>(R.layout.item_square_child)
                }
            }

            onBind {
                val flexRecycle = findView<RecyclerView>(R.id.flexbox_recycle)
                flexRecycle.models = getModel<Tab>().children
            }
        }
    }

    override fun lazyLoad() {
        binding.squareRefresh.run {
            onLoading {
                findViewById<TextView>(R.id.tv_msg).text = "拼命加载中..."
            }
            showLoading(refresh = false) //显示加载页 不显示刷新
        }
    }

    private fun requestData() {
        viewModel.getSquareList()
    }

    override fun finishRefreshOrLoadMore() {
        binding.squareRefresh.finishRefresh()
        binding.squareRefresh.finishLoadMore()
    }

    override fun showEmptyView() {
        binding.squareRefresh.showEmpty()
    }

    override fun showErrorMsg(msg: String) {
        binding.squareRefresh.showError()
    }

    override fun createObserver() {
        viewModel.squareLiveData.observe(viewLifecycleOwner) {
            binding.squareRefresh.run {
                addData(it) {
                    false
                }
            }
        }
    }

}