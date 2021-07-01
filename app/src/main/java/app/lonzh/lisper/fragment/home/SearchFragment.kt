package app.lonzh.lisper.fragment.home

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import app.lonzh.baselibrary.util.Constant
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentSearchBinding
import app.lonzh.lisper.event.SearchEvent
import app.lonzh.lisper.ext.keyBoardSearch
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.utils.MMKVUtil
import app.lonzh.lisper.vm.request.home.SearchRequestViewModel
import app.lonzh.lisper.vm.state.home.SearchStateViewModel
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.flexbox.FlexboxLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.pedaily.yc.ycdialoglib.fragment.CustomDialogFragment

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/29 10:27 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/29 10:27 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SearchFragment : LisperFragment<SearchRequestViewModel, FragmentSearchBinding>() {

    private val searchStateViewModel: SearchStateViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = searchStateViewModel

        postDelayed({
            KeyboardUtils.showSoftInput(binding.edtSearch)
        }, Constant.RELAY_LOAD)

        binding.edtSearch.run {
            keyBoardSearch {
                searchStateViewModel.keyword.get()?.let { k ->
                    val history = MMKVUtil.getSetObject(MMKVUtil.HISTORY)
                    history?.add(k)
                    MMKVUtil.setSetObject(MMKVUtil.HISTORY, history)
                    goSearch(k)
                }
            }
        }

        binding.recycleView.run {
            layoutManager = FlexboxLayoutManager(activity)
            setup {
                addType<String>(R.layout.item_search_flag)

                onClick(R.id.tv_search_flag) {
                    goSearch(getModel())
                }
            }.models = searchStateViewModel.history.get()
        }

        binding.hotRecycle.run {
            layoutManager = FlexboxLayoutManager(activity)
            setup {
                addType<String>(R.layout.item_search_flag)

                onClick(R.id.tv_search_flag) {
                    goSearch(getModel())
                }
            }
        }

        ClickUtils.applySingleDebouncing(binding.tvClear) {
            CustomDialogFragment.create(childFragmentManager)
                .setTitle(getString(R.string.clear_history_title))
                .setCancelContent(getString(R.string.cancel))
                .setOkContent(getString(R.string.confirm))
                .setOkColor(ContextCompat.getColor(requireActivity(), R.color.red_text))
                .setCancelListener {
                    CustomDialogFragment.dismissDialogFragment()
                }
                .setOkListener {
                    CustomDialogFragment.dismissDialogFragment()
                    MMKVUtil.setSetObject(MMKVUtil.HISTORY, mutableSetOf())
                    searchStateViewModel.run {
                        hasHistory.set(false)
                        history.set(mutableListOf())
                        binding.recycleView.models = mutableListOf()
                    }
                }
                .setDimAmount(0.2f)
                .setCancelOutside(true)
                .show()
        }
    }

    override fun lazyLoad() {
        viewModel.getHotKey()
    }

    private fun goSearch(k: String) {
        nav(R.id.action_searchFragment_to_searchResultFragment, SearchFragmentArgs(k).toBundle())
        hideSoftInput()
    }

    override fun onDestroyView() {
        hideSoftInput()
        super.onDestroyView()
    }

    private fun hideSoftInput() {
        postDelayed({
            KeyboardUtils.hideSoftInput(binding.edtSearch)
        }, Constant.RELAY_LOAD)
    }

    override fun createObserver() {
        LiveEventBus.get<SearchEvent>(SearchEvent::class.java.simpleName)
            .observe(viewLifecycleOwner) {
                searchStateViewModel.run {
                    val histories = mutableListOf<String>()
                    val result = MMKVUtil.getSetObject(MMKVUtil.HISTORY)
                    result?.map {
                        histories.add(it)
                    }
                    keyword.set("")
                    hasHistory.set(histories.isNotEmpty())
                    binding.recycleView.models = histories
                }
            }

        viewModel.hotLiveData.observe(viewLifecycleOwner) {
            searchStateViewModel.hasHot.set(!it.isNullOrEmpty())
            val result = mutableListOf<String>()
            it.map { key ->
                result.add(key.name)
            }
            binding.hotRecycle.models = result
        }
    }
}