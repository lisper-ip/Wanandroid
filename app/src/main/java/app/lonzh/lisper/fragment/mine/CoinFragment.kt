package app.lonzh.lisper.fragment.mine

import android.os.Bundle
import app.lonzh.lisper.R
import app.lonzh.lisper.data.Coin
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.databinding.FragmentCoinBinding
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.tab.TabItemFragment
import app.lonzh.lisper.vm.request.mine.CoinRequestViewModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/29 5:45 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/29 5:45 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CoinFragment : LisperFragment<CoinRequestViewModel, FragmentCoinBinding>(){

    //加载更多真实的index(列表界面需要)
    private var realIndex: Int = START_INDEX

    companion object{
        //开始页数(如果起始页不是默认设置则 列表界面需要)
        const val START_INDEX = 1
    }

    override fun layoutId(): Int = R.layout.fragment_coin

    override fun initView(savedInstanceState: Bundle?) {
        setLeftTitle(R.string.my_coin)

        binding.pageRefresh.run {
            onRefresh {
                //接口页数从1开始， 如果是0不需要设置(列表界面需要)
                index = TabItemFragment.START_INDEX
                viewModel.getCoin(index)
            }

            onLoadMore {
                //(列表界面需要)
                index = realIndex
                viewModel.getCoin(index)
            }
        }
        binding.recycleView.run {
            linear().divider(R.drawable.driver_black_line).setup {
                addType<Coin>(R.layout.item_coin)
            }
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
        viewModel.coinLiveData.observe(viewLifecycleOwner){ list ->
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
    }
}