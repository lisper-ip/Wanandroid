package app.lonzh.lisper.fragment.square

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.lisper.R
import app.lonzh.lisper.data.Children
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.data.Tab
import app.lonzh.lisper.databinding.FragmentSingleListBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.vm.request.square.SystemRequestViewModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.flexbox.FlexboxLayoutManager

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/1 3:20 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/1 3:20 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SystemFragment : LisperFragment<SystemRequestViewModel, FragmentSingleListBinding>() {

    companion object {
        @JvmStatic
        fun getInstance(): SystemFragment {
            return SystemFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_single_list

    override fun initView(savedInstanceState: Bundle?) {
        binding.pageRefresh.run {
            onRefresh {
                viewModel.getSystemList()
            }
        }
        binding.recycleView.linear().setup {
            addType<Tab>(R.layout.item_square)

            onCreate {
                val flexRecycle = findView<RecyclerView>(R.id.flexbox_recycle)
                flexRecycle.layoutManager = FlexboxLayoutManager(mActivity)
                flexRecycle.setup {
                    addType<Children>(R.layout.item_square_child)

                    onClick(R.id.tv_child_title){
                        val bundle = SystemItemFragmentArgs(getModel<Children>().name, getModel<Children>().id).toBundle()
                        nav(R.id.action_main_fragment_to_systemItemFragment, bundle)
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
        viewModel.systemLiveData.observe(viewLifecycleOwner) {
            binding.pageRefresh.run {
                addData(it) {
                    false
                }
            }
        }
    }
}
