package app.lonzh.lisper.fragment.main

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.R
import app.lonzh.lisper.data.Children
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.data.Tab
import app.lonzh.lisper.databinding.FragmentSquareBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.square.SystemFragmentArgs
import app.lonzh.lisper.vm.request.main.SquareRequestViewModel
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
                viewModel.getSquareList()
            }
        }
        binding.squareRecycle.linear().setup {
            addType<Tab>(R.layout.item_square)

            onCreate {
                val flexRecycle = findView<RecyclerView>(R.id.flexbox_recycle)
                flexRecycle.layoutManager = FlexboxLayoutManager(mActivity)
                flexRecycle.setup {
                    addType<Children>(R.layout.item_square_child)

                    onClick(R.id.tv_child_title){
                        val bundle = SystemFragmentArgs(getModel<Children>().name, getModel<Children>().id).toBundle()
                        nav(R.id.action_main_fragment_to_systemFragment, bundle)
                    }
                }
            }

            onBind {
                val flexRecycle = findView<RecyclerView>(R.id.flexbox_recycle)
                flexRecycle.models = getModel<Tab>().children
            }
        }
    }

    override fun lazyLoad() {
        binding.squareRefresh.showLoading(tag  = StateData(-1, getString(R.string.lisper_request)), refresh = false)
    }

    override fun showEmptyView() {
        binding.squareRefresh.showEmpty()
    }

    override fun showErrorView(msg: String) {
        binding.squareRefresh.run {
            if(loaded){
                finish(success = false, hasMore = true)
                toast(msg)
            } else {
                showError(StateData(R.drawable.ic_error, msg))
            }
        }
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