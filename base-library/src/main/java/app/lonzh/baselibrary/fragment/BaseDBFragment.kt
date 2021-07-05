package app.lonzh.baselibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 4:27 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 4:27 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseDBFragment<DB: ViewDataBinding> : BaseFragment() {
    //该类绑定的ViewDataBinding
    lateinit var binding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
}