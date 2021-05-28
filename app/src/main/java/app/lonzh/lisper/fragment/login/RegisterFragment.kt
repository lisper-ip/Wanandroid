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
import app.lonzh.lisper.databinding.FragmentRegisterBinding
import app.lonzh.lisper.ext.back
import app.lonzh.lisper.vm.request.login.RegisterRequestViewModel
import app.lonzh.lisper.vm.state.login.RegisterStateViewModel
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.StringUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    注册
 * @Author:         Lisper
 * @CreateDate:     5/28/21 5:19 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/28/21 5:19 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class RegisterFragment : BaseVmDbFragment<RegisterRequestViewModel, FragmentRegisterBinding>() {

    private val registerStateViewModel: RegisterStateViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_register

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = registerStateViewModel

        ClickUtils.applySingleDebouncing(
            arrayOf(
                binding.tvRegister, binding.tvBack,
                binding.ivClear, binding.ivAgainPasswordEye,
                binding.ivPasswordEye
            )
        ) {
            when (it.id) {
                R.id.tv_register -> viewModel.register(registerStateViewModel)
                R.id.tv_back -> back()
                R.id.iv_clear -> registerStateViewModel.account.set("")
                R.id.iv_password_eye -> {
                    val method = binding.edtPassword.transformationMethod
                    if (method == HideReturnsTransformationMethod.getInstance()) {
                        binding.edtPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                    } else {
                        binding.edtPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                    }
                    binding.ivPasswordEye.isSelected = !binding.ivPasswordEye.isSelected
                    Selection.setSelection(
                        binding.edtPassword.text,
                        binding.edtPassword.text.length
                    )
                }
                R.id.iv_again_password_eye -> {
                    val method = binding.edtAgainPassword.transformationMethod
                    if (method == HideReturnsTransformationMethod.getInstance()) {
                        binding.edtAgainPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                    } else {
                        binding.edtAgainPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                    }
                    binding.ivAgainPasswordEye.isSelected = !binding.ivAgainPasswordEye.isSelected
                    Selection.setSelection(
                        binding.edtAgainPassword.text,
                        binding.edtAgainPassword.text.length
                    )
                }
                else -> {
                }
            }
        }
        binding.edtAccount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(StringUtils.isEmpty(registerStateViewModel.account.get())){
                    binding.ivClear.visibility = View.GONE
                } else {
                    binding.ivClear.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun createObserver() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            toast(getString(R.string.register_success))
            back()
        }
    }
}