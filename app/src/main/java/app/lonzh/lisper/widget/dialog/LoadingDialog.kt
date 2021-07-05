package app.lonzh.lisper.widget.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import app.lonzh.baselibrary.action.AnimAction
import app.lonzh.baselibrary.dialog.BaseDialog
import app.lonzh.lisper.R
import com.blankj.utilcode.util.StringUtils.getString

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/5 4:24 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/5 4:24 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class LoadingDialog {
    class Builder(context: Context) : BaseDialog.Builder<Builder>(context){
        private var mMessageView: TextView

        init {
            setContentView(R.layout.layout_loading)
            setAnimStyle(AnimAction.ANIM_TOAST)
            setBackgroundDimEnabled(false)
            setCancelable(false)

            mMessageView = findViewById(R.id.tv_msg)
        }

        fun setMessage(@StringRes id: Int): Builder {
            return setMessage(getString(id))
        }

        fun setMessage(text: CharSequence?): Builder {
            mMessageView.text = text
            mMessageView.visibility = if (text == null) View.GONE else View.VISIBLE
            return this
        }
    }
}