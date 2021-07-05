package app.lonzh.baselibrary.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 3:36 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 3:36 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseDBActivity<DB: ViewDataBinding>: BaseActivity() {
    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    override fun initDataBind() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }
}