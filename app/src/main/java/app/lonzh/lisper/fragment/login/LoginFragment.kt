package app.lonzh.lisper.fragment.login

import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.fragment.app.viewModels
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.lisper.R
import app.lonzh.lisper.databinding.FragmentLoginBinding
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.ext.nav
import app.lonzh.lisper.vm.request.login.LoginRequestViewModel
import app.lonzh.lisper.vm.state.login.LoginStateViewModel
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.StringUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    登录
 * @Author:         Lisper
 * @CreateDate:     5/28/21 5:19 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/28/21 5:19 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class LoginFragment : BaseVmDbFragment<LoginRequestViewModel, FragmentLoginBinding>() {

    private val loginStateViewModel: LoginStateViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = loginStateViewModel

        ClickUtils.applySingleDebouncing(
            arrayOf(
                binding.tvLogin,
                binding.tvGoRegister,
                binding.tvLoginBack,
                binding.ivClear,
                binding.ivEye
            )
        ) {
            when (it.id) {
                R.id.tv_go_register -> nav(R.id.action_loginFragment_to_registerFragment)
                R.id.tv_login_back -> back()
                R.id.iv_clear -> loginStateViewModel.account.set("")
                R.id.iv_eye -> {
                    val method = binding.edtPassword.transformationMethod
                    if (method == HideReturnsTransformationMethod.getInstance()) {
                        binding.edtPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                    } else {
                        binding.edtPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                    }
                    binding.ivEye.isSelected = !binding.ivEye.isSelected
                    Selection.setSelection(binding.edtPassword.text, binding.edtPassword.text.length)
                }
                R.id.tv_login -> viewModel.login(loginStateViewModel)
                else -> {
                }
            }
        }

        binding.edtAccount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(StringUtils.isEmpty(loginStateViewModel.account.get())){
                    binding.ivClear.visibility = View.GONE
                } else {
                    binding.ivClear.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun createObserver() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            toast(getString(R.string.login_success))
            back()
        }
    }
}