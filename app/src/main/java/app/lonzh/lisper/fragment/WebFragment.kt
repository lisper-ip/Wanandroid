package app.lonzh.lisper.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import app.lonzh.baselibrary.manage.net.NetworkUtil
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentWebBinding
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.fragment.base.LisperFragment
import com.just.agentweb.AgentWeb

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/28 10:33 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/28 10:33 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class WebFragment : LisperFragment<BaseViewModel, FragmentWebBinding>() {

    private var agentWeb: AgentWeb? = null

    override fun layoutId(): Int = R.layout.fragment_web

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("InflateParams")
    override fun initView(savedInstanceState: Bundle?) {
        arguments?.run {
            val view = LayoutInflater.from(activity).inflate(R.layout.layout_view_state, null)
            view?.run {
                findViewById<ImageView>(R.id.iv_anim).setImageResource(R.drawable.ic_error)
                if(!NetworkUtil.isNetworkAvailable(activity)){
                    findViewById<TextView>(R.id.tv_msg).text = getString(R.string.network_error)
                } else {
                    findViewById<TextView>(R.id.tv_msg).text = "页面出现错误!"
                }
            }
            binding.webTitleBar.titleView.ellipsize = TextUtils.TruncateAt.END
            setTitle(Html.fromHtml(WebFragmentArgs.fromBundle(this).title, Html.FROM_HTML_MODE_COMPACT))
            val url = WebFragmentArgs.fromBundle(this).url
            agentWeb = AgentWeb.with(this@WebFragment)
                .setAgentWebParent(binding.webCon, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(R.color.colorAccent)
                .setMainFrameErrorView(view)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(url)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(!agentWeb!!.back()){
                    back()
                }
            }
        })
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }


    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}