package app.lonzh.lisper.widget.dialog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.baselibrary.action.AnimAction.ANIM_BOTTOM
import app.lonzh.baselibrary.dialog.BaseDialog
import app.lonzh.lisper.R
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.StringUtils.getString
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.hjq.toast.ToastUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/5 6:17 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/5 6:17 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class BottomDialog {
    class Builder(
        context: Context, block: ((recycleView: RecyclerView) -> Unit)? = null
    ) : BaseDialog.Builder<Builder>(context) {

        private var recyclerView: RecyclerView
        private var title: TextView

        init {
            setContentView(R.layout.layout_dialog_recycleview)
            setAnimStyle(ANIM_BOTTOM)
            title = findViewById(R.id.title)
            recyclerView = findViewById(R.id.recycle)
            block?.invoke(recyclerView)
        }

        fun setTitle(@StringRes id: Int): Builder {
            title.text = getString(id)
            return this
        }

        override fun create(): BaseDialog {
            if(StringUtils.isEmpty(title.text.toString())){
                title.visibility = View.GONE
            } else {
                title.visibility = View.VISIBLE
            }
            return super.create()
        }
    }
}