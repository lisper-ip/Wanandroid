package app.lonzh.lisper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/8 7:05 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/8 7:05 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class TabFragmentAdapter<F: Fragment>(manager: FragmentManager,
                         behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) : FragmentPagerAdapter(manager, behavior) {
    private var fragmentsSet: ArrayList<F> = arrayListOf()

    fun addFragment(fragment: F){
        fragmentsSet.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsSet[position]
    }

    override fun getCount(): Int {
        return fragmentsSet.size
    }

    fun getFragments() : List<F>{
        return fragmentsSet
    }
}