package app.lonzh.baselibrary.action

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 10:53 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 10:53 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface TitleBarAction : OnTitleBarListener {
    val titleBar: TitleBar?

    /**
     * 左项被点击
     *
     * @param v     被点击的左项View
     */
    override fun onLeftClick(v: View) {}

    /**
     * 标题被点击
     *
     * @param v     被点击的标题View
     */
    override fun onTitleClick(v: View) {}

    /**
     * 右项被点击
     *
     * @param v     被点击的右项View
     */
    override fun onRightClick(v: View) {}

    /**
     * 设置标题栏的标题
     */
    fun setTitle(@StringRes id: Int) {
        if (titleBar != null) {
            setTitle(titleBar!!.resources.getString(id))
        }
    }

    /**
     * 设置标题栏的标题
     */
    fun setTitle(title: CharSequence?) {
        if (titleBar != null) {
            titleBar!!.title = title
        }
    }

    /**
     * 设置标题栏的左标题
     */
    fun setLeftTitle(id: Int) {
        if (titleBar != null) {
            titleBar!!.setLeftTitle(id)
        }
    }

    fun setLeftTitle(text: CharSequence?) {
        if (titleBar != null) {
            titleBar!!.leftTitle = text
        }
    }

    fun getLeftTitle(): CharSequence? {
        return if (titleBar != null) {
            titleBar!!.leftTitle
        } else ""
    }

    /**
     * 设置标题栏的右标题
     */
    fun setRightTitle(id: Int) {
        if (titleBar != null) {
            titleBar!!.setRightTitle(id)
        }
    }

    fun setRightTitle(text: CharSequence?) {
        if (titleBar != null) {
            titleBar!!.rightTitle = text
        }
    }

    fun getRightTitle(): CharSequence? {
        return if (titleBar != null) {
            titleBar!!.rightTitle
        } else ""
    }

    /**
     * 设置标题栏的左图标
     */
    fun setLeftIcon(id: Int) {
        if (titleBar != null) {
            titleBar!!.setLeftIcon(id)
        }
    }

    fun setLeftIcon(drawable: Drawable?) {
        if (titleBar != null) {
            titleBar!!.leftIcon = drawable
        }
    }

    fun getLeftIcon(): Drawable? {
        return if (titleBar != null) {
            titleBar!!.leftIcon
        } else null
    }

    /**
     * 设置标题栏的右图标
     */
    fun setRightIcon(id: Int) {
        if (titleBar != null) {
            titleBar!!.setRightIcon(id)
        }
    }

    fun setRightIcon(drawable: Drawable?) {
        if (titleBar != null) {
            titleBar!!.rightIcon = drawable
        }
    }

    fun getRightIcon(): Drawable? {
        return if (titleBar != null) {
            titleBar!!.rightIcon
        } else null
    }

    /**
     * 递归获取 ViewGroup 中的 TitleBar 对象
     */
    fun findTitleBar(group: ViewGroup): TitleBar? {
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is TitleBar) {
                return view
            } else if (view is ViewGroup) {
                val titleBar = findTitleBar(view)
                if (titleBar != null) {
                    return titleBar
                }
            }
        }
        return null
    }
}