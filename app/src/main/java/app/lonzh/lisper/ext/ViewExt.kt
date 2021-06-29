package app.lonzh.lisper.ext

import android.os.Parcelable
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.hjq.toast.ToastUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 4:55 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 4:55 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
/**
 * ViewPager于fragment绑定
 * 通过BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT支持懒加载
 */
fun ViewPager.initFragment(
    manager: FragmentManager,
    fragments: MutableList<Fragment>
): ViewPager {
    //设置适配器
    adapter = object : FragmentStatePagerAdapter(
        manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount() = fragments.size

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun saveState(): Parcelable? {
            return null
        }
    }
    return this
}

/**
 * ViewPager选中
 */
fun ViewPager.doSelected(selected: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            selected.invoke(position)
        }

    })
}

/**
 * edit 监听
 */
fun EditText.keyBoardSearch(onClick: () -> Unit){
    setOnEditorActionListener { _, actionId, _ ->
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            onClick()
        } else {
            ToastUtils.show("请输入关键字")
            return@setOnEditorActionListener false
        }
        return@setOnEditorActionListener true
    }
}