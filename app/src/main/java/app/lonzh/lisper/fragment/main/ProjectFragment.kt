package app.lonzh.lisper.fragment.main

import android.os.Bundle
import app.lonzh.lisper.R
import app.lonzh.lisper.adapter.TabFragmentAdapter
import app.lonzh.lisper.databinding.FragmentProjectBinding
import app.lonzh.lisper.fragment.base.LisperFragment
import app.lonzh.lisper.fragment.tab.TabFragment
import app.lonzh.lisper.vm.request.main.ProjectRequestViewModel
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
class ProjectFragment : LisperFragment<ProjectRequestViewModel, FragmentProjectBinding>() {
    private lateinit var tabFragmentAdapter: TabFragmentAdapter<TabFragment>

    companion object{
        @JvmStatic
        fun getInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_project

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        tabFragmentAdapter = TabFragmentAdapter(childFragmentManager)
    }

    override fun lazyLoad() {
        viewModel.getProjectTab()
    }

    override fun createObserver() {
        viewModel.projectTabLiveData.observe(viewLifecycleOwner){
            val titles = arrayOfNulls<String>(it.size)
            it.forEachIndexed { index, tab ->
                val title = tab.name.let { name ->
                    if(name.contains("&amp;")){
                        name.replace("&amp;", "&")
                    } else{
                        name
                    }
                }
                titles[index] = title
                val listFragment = TabFragment.getInstance().apply {
                    val bundle = Bundle();
                    bundle.putString(TabFragment.TAB_ID, tab.id.toString())
                    arguments = bundle
                }
                tabFragmentAdapter.addFragment(listFragment)
            }
            binding.viewpager.run {
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
}