package app.lonzh.lisper.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import app.lonzh.baselibrary.manage.net.NetworkUtil
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.data.ShareEntity
import app.lonzh.lisper.databinding.FragmentWebBinding
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.fragment.base.LisperFragment
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.pedaily.yc.ycdialoglib.fragment.BottomDialogFragment

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
            setTitle(Html.fromHtml(WebFragmentArgs.fromBundle(this).title, Html.FROM_HTML_MODE_COMPACT))
            val url = WebFragmentArgs.fromBundle(this).url
            agentWeb = AgentWeb.with(this@WebFragment)
                .setAgentWebParent(binding.webCon, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(R.color.colorAccent)
                .setMainFrameErrorView(view)
                .setWebChromeClient(object : WebChromeClient(){
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        if(titleBar?.titleView?.text.isNullOrEmpty()){
                            setTitle(title)
                        }
                    }
                })
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
        binding.webTitleBar.titleView.ellipsize = TextUtils.TruncateAt.END
        super.onPause()
    }


    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        binding.webTitleBar.titleView.ellipsize = TextUtils.TruncateAt.MARQUEE
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun onRightClick(v: View) {
        BottomDialogFragment.create(childFragmentManager)
            .run {
                setViewListener {
                    val recycleView = it.findViewById<RecyclerView>(R.id.share_recycle)
                    recycleView.linear(RecyclerView.HORIZONTAL, reverseLayout = false, scrollEnabled = true).setup {
                        addType<ShareEntity>(R.layout.item_share)

                        onClick(R.id.tv_share){
                            toast(getModel<ShareEntity>().title)
                        }
                    }.models = ShareEntity.getShareEntities()
                }
                layoutRes = R.layout.layout_share_menu
                dimAmount = 0.2f
                setCancelOutside(true)
                show()
            }

    }

}