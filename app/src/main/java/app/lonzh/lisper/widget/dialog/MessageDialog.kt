package app.lonzh.lisper.widget.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import app.lonzh.baselibrary.action.AnimAction.*
import app.lonzh.baselibrary.dialog.BaseDialog
import app.lonzh.lisper.R
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.StringUtils.getString

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/5 5:19 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/5 5:19 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class MessageDialog {
    class Builder(
        context: Context,
        onCancel: (() -> Unit)? = null,
        onConfirm: (() -> Unit)? = null,
    ) : UIDialog.Builder<Builder>(context){
        private var mMessageView: TextView

        init {
            setCustomView(R.layout.layout_dialog_message)

            mMessageView = findViewById(R.id.tv_message_message)

            ClickUtils.applySingleDebouncing(arrayOf(mCancelView, mConfirmView)){
                autoDismiss()
                when(it){
                    mCancelView -> {
                        autoDismiss()
                        onCancel?.invoke()
                    }
                    mConfirmView -> {
                        autoDismiss()
                        onConfirm?.invoke()
                    }
                }
            }
        }

        fun setMessage(@StringRes id: Int): Builder {
            return setMessage(getString(id))
        }

        fun setMessage(text: CharSequence?): Builder {
            mMessageView.text = text
            return this
        }

        override fun create(): BaseDialog {
            if(StringUtils.isEmpty(mMessageView.text.toString())){
                mMessageView.visibility = View.GONE
            } else {
                mMessageView.visibility = View.VISIBLE
            }
            return super.create()
        }
    }
}