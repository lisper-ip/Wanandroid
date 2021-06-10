package app.lonzh.lisper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import app.lonzh.commonlibrary.activity.BaseVmDbActivity
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.databinding.ActivityMainBinding
import app.lonzh.lisper.fragment.MainFragment
import app.lonzh.lisper.vm.AppDataViewModel

class MainActivity : BaseVmDbActivity<BaseViewModel, ActivityMainBinding>() {

    private val appDataViewModel: AppDataViewModel by viewModels()

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