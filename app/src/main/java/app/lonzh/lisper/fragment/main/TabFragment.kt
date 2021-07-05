package app.lonzh.lisper.fragment.main

import android.os.Bundle
import app.lonzh.lisper.R
import app.lonzh.lisper.adapter.TabFragmentAdapter
import app.lonzh.lisper.data.Tab
import app.lonzh.lisper.databinding.FragmentTabBinding
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.tab.TabItemFragment
import app.lonzh.lisper.vm.request.main.TabRequestViewModel
import com.flyco.tablayout.listener.OnTabSelectListener

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
class TabFragment : LisperFragment<TabRequestViewModel, FragmentTabBinding>() {
    private lateinit var tabFragmentAdapter: TabFragmentAdapter<TabItemFragment>

    companion object{
        @JvmStatic
        fun getInstance(): TabFragment {
            return TabFragment()
        }
        const val TAB_TYPE = "tab_type"
        const val TAB_PROJECT = "project"
        const val TAB_WXARTICLE = "wxarticle"
    }

    override val layoutId: Int = R.layout.fragment_tab

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        tabFragmentAdapter = TabFragmentAdapter(childFragmentManager)
    }

    override fun lazyLoad() {
        arguments?.let {
            when(it.getString(TAB_TYPE)){
                TAB_PROJECT -> viewModel.getProjectTab()
                TAB_WXARTICLE -> viewModel.getOfficialAccountTab()
            }
        }
    }

    override fun createObserver() {
        arguments?.let {
            when(it.getString(TAB_TYPE)){
                TAB_PROJECT -> viewModel.projectTabLiveData.observe(viewLifecycleOwner){ list ->
                    handlerTabList(list)
                }
                TAB_WXARTICLE -> viewModel.officialAccountTabLiveData.observe(viewLifecycleOwner){ list ->
                    handlerTabList(list)
                }
            }
        }
    }

    private fun handlerTabList(tabList : List<Tab>){
        val titles = arrayOfNulls<String>(tabList.size)
        tabList.forEachIndexed { index, tab ->
            val title = tab.name.let { name ->
                if(name.contains("&amp;")){
                    name.replace("&amp;", "&")
                } else{
                    name
                }
            }
            titles[index] = title
            arguments?.let {
                tabFragmentAdapter.addFragment(TabItemFragment.getInstance().apply {
                    val bundle = Bundle()
                    bundle.putString(TAB_TYPE, it.getString(TAB_TYPE))
                    bundle.putString(TabItemFragment.TAB_ID, tab.id.toString())
                    arguments = bundle
                })
            }
        }
        binding.viewPager.run {
            adapter = tabFragmentAdapter
            offscreenPageLimit = titles.size
            binding.tabLayout.setViewPager(this, titles)
            binding.tabLayout.setOnTabSelectListener(object : OnTabSelectListener{
                override fun onTabSelect(position: Int) {

                }

                override fun onTabReselect(position: Int) {
                    tabFragmentAdapter.getFragments()[position].repeatFresh()
                }

            })
        }
    }
}