package app.lonzh.lisper.fragment.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import app.lonzh.baselibrary.util.Constant
import app.lonzh.lisper.R
import app.lonzh.lisper.data.HotWeb
import app.lonzh.lisper.databinding.FragmentSearchBinding
import app.lonzh.lisper.event.SearchEvent
import app.lonzh.lisper.ext.keyBoardSearch
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.WebFragmentArgs
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.utils.MMKVUtil
import app.lonzh.lisper.vm.request.home.SearchRequestViewModel
import app.lonzh.lisper.vm.state.home.SearchStateViewModel
import app.lonzh.lisper.widget.dialog.MessageDialog
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.flexbox.FlexboxLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus

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

    override val layoutId: Int = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = searchStateViewModel

        showSoftInput(binding.edtSearch)

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

        binding.hotWebRecycle.run {
            layoutManager = FlexboxLayoutManager(activity)
            setup {
                addType<HotWeb>(R.layout.item_hot_web)

                onClick(R.id.tv_search_flag) {
                    val bundle = WebFragmentArgs("", getModel<HotWeb>().link,
                        "", getModel<HotWeb>().id).toBundle()
                    hideSoftInputAfter {
                        nav(R.id.action_searchFragment_to_webFragment, bundle)
                    }
                }
            }
        }

        ClickUtils.applySingleDebouncing(binding.tvClear) {
            hideSoftInputAfter {
                activity?.let { act ->
                    MessageDialog.Builder(act, onConfirm = {
                        MMKVUtil.setSetObject(MMKVUtil.HISTORY, mutableSetOf())
                        searchStateViewModel.run {
                            hasHistory.set(false)
                            history.set(mutableListOf())
                            binding.recycleView.models = mutableListOf()
                        }
                    })
                        .setTitle(getString(R.string.clear_history_title))
                        .create()
                        .show()
                }
            }
        }
    }

    override fun lazyLoad() {
        viewModel.getHotKey()
        viewModel.getHotWeb()
    }

    private fun goSearch(k: String) {
        hideSoftInputAfter {
            nav(R.id.action_searchFragment_to_searchResultFragment, SearchFragmentArgs(k).toBundle())
        }
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

        viewModel.hotWebLiveData.observe(viewLifecycleOwner){
            searchStateViewModel.hasWeb.set(!it.isNullOrEmpty())
            binding.hotWebRecycle.models = it
        }
    }
}