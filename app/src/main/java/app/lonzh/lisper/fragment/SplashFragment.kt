package app.lonzh.lisper.fragment

import android.Manifest
import android.os.Bundle
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentSplashBinding
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.fragment.base.LisperFragment
import com.permissionx.guolindev.PermissionX

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/19/21 6:41 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 6:41 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SplashFragment : LisperFragment<BaseViewModel, FragmentSplashBinding>() {

    override fun layoutId(): Int = R.layout.fragment_splash

    /**
     * 初始化view
     */
    override fun initView(savedInstanceState: Bundle?) {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .request { _, _, _ ->
                postDelayed({
                    nav(R.id.action_splashFragment_to_main_fragment)
                }, 2000)
                binding.ivSplash.playAnimation()
            }
    }

}