package app.lonzh.lisper.widget.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import app.lonzh.baselibrary.action.AnimAction
import app.lonzh.baselibrary.dialog.BaseDialog
import app.lonzh.lisper.R
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.StringUtils.getString

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/5 5:10 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/5 5:10 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class UIDialog {
    @Suppress("UNCHECKED_CAST")
    open class Builder<B : Builder<B>>(context: Context) : BaseDialog.Builder<B>(context){
        private var mAutoDismiss = true
        private var mContainerLayout: ViewGroup
        private var mTitleView: TextView
        protected var mCancelView: TextView
        private var mLineView: View
        protected var mConfirmView: TextView

        init {
            setContentView(R.layout.layout_dialog_ui)
            setAnimStyle(AnimAction.ANIM_SCALE)
            setGravity(Gravity.CENTER)
            setBackgroundDimAmount(0.3f)

            mContainerLayout = findViewById(R.id.ll_ui_container)
            mTitleView = findViewById(R.id.tv_ui_title)
            mCancelView = findViewById(R.id.tv_ui_cancel)
            mLineView = findViewById(R.id.v_ui_line)
            mConfirmView = findViewById(R.id.tv_ui_confirm)
        }

        fun setCustomView(@LayoutRes id: Int): B {
            return setCustomView(
                LayoutInflater.from(context).inflate(id, mContainerLayout, false)
            )
        }

        fun setCustomView(view: View?): B {
            mContainerLayout.addView(view, 1)
            return this as B
        }

        fun setTitle(@StringRes id: Int): B {
            return setTitle(getString(id))
        }

        fun setTitle(text: CharSequence?): B {
            mTitleView.text = text
            return this as B
        }

        fun setCancel(@StringRes id: Int): B {
            return setCancel(getString(id))
        }

        fun setCancel(text: CharSequence?): B {
            mCancelView.text = text
            mLineView.visibility =
                if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
            return this as B
        }

        fun setConfirm(@StringRes id: Int): B {
            return setConfirm(getString(id))
        }

        fun setConfirm(text: CharSequence?): B {
            mConfirmView.text = text
            return this as B
        }

        fun setAutoDismiss(dismiss: Boolean): B {
            mAutoDismiss = dismiss
            return this as B
        }

        fun autoDismiss() {
            if (mAutoDismiss) {
                dismiss()
            }
        }
    }
}