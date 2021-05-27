package app.lonzh.lisper

import android.os.Bundle
import androidx.fragment.app.Fragment
import app.lonzh.commonlibrary.activity.BaseVmDbActivity
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.databinding.ActivityMainBinding
import app.lonzh.lisper.fragment.MainFragment

class MainActivity : BaseVmDbActivity<BaseViewModel, ActivityMainBinding>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun onBackPressed() {
        //获取hostFragment
        val mainNavFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        //获取当前fragment
        val fragment = mainNavFragment?.childFragmentManager?.primaryNavigationFragment
        if (fragment is MainFragment) {
            moveTaskToBack(false)
            postDelayed({
                finish()
            }, 300)
        } else {
            super.onBackPressed()
        }
    }
}