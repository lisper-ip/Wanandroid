package app.lonzh.lisper.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.lonzh.commonlibrary.R
import com.blankj.utilcode.util.StringUtils


/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/28/21 10:12 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/28/21 10:12 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class LoadingDialog(context: Context, content: String, var canNotCancel: Boolean) : Dialog(context, R.style.Loading) {

    companion object{
        private var loadingDialog: LoadingDialog? = null

        fun show(context: Context){
            show(context, "")
        }

        fun show(context: Context, msg: String){
            show(context, msg, false)
        }

        fun show(context: Context, msg: String, cancel: Boolean){
            if(context is AppCompatActivity){
                if(context.isFinishing){
                    return
                }
            }
            if(loadingDialog != null && loadingDialog!!.isShowing){
                return
            }
            loadingDialog = LoadingDialog(context, msg, cancel)
            loadingDialog!!.show()
        }

        fun dismiss(context: Context){
            try {
                if(context is AppCompatActivity){
                    if(context.isFinishing){
                        loadingDialog = null
                        return
                    }
                }
                loadingDialog?.let {
                    if(it.isShowing){
                        val loadingContext = it.context
                        if(loadingContext is AppCompatActivity){
                            if(loadingContext.isFinishing){
                                loadingDialog = null
                                return
                            }
                        }
                        it.dismiss()
                        loadingDialog = null
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            } finally {
                loadingDialog = null
            }
        }
    }

    init {
        setContentView(R.layout.layout_loading)
        val tvMsg = findViewById<TextView>(R.id.tv_msg)
        if(StringUtils.isEmpty(content)){
            tvMsg.visibility = View.GONE
        } else {
            tvMsg.visibility = View.VISIBLE
            tvMsg.text = content
        }
        window?.run {
            attributes.gravity = Gravity.CENTER
        }
    }

    override fun onStart() {
        super.onStart()
        window?.run {
            decorView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onStop() {
        super.onStop()
        window?.run {
            decorView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(canNotCancel){
                dismiss()
                return true
            }

        }
        return super.onKeyDown(keyCode, event)
    }
}