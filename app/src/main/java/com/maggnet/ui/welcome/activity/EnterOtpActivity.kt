package com.maggnet.ui.welcome.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.viewModels
import com.maggnet.R
import com.maggnet.databinding.ActivityEnterOtpBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.ui.login.fragment.Otp.SendOtpViewModel
import com.maggnet.ui.login.fragment.Otp.VerifyOtpViewModel
import com.maggnet.ui.login.fragment.forgot.ForgotPasswordNavigator
import com.maggnet.ui.register.fragment.verification.OtpVerificationNavigator
import com.maggnet.ui.register.fragment.verification.OtpVerificationViewModel
import com.maggnet.utils.AppLanguageUtils
import com.maggnet.utils.PreferenceManager
import com.maggnet.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class EnterOtpActivity : BaseActivity<ActivityEnterOtpBinding>(ActivityEnterOtpBinding::inflate),
    View.OnClickListener,
    ForgotPasswordNavigator, OtpVerificationNavigator {

    @Inject
    lateinit var preferenceManager: PreferenceManager
    private val verifyOtpViewModel:VerifyOtpViewModel by viewModels()
    private val sendOtpViewModel: SendOtpViewModel by viewModels()
    private val otpVerificationFragmentViewModel: OtpVerificationViewModel by viewModels()

    var click =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyOtpViewModel.setNavigator(this@EnterOtpActivity)
        otpVerificationFragmentViewModel.setNavigator(this)
        sendOtpViewModel.setNavigator(this@EnterOtpActivity)
        setOTPWaitingCounter()
        setEventListeners()
    }

    override fun initUserInterface() {
        viewDataBinding.apply {
            mobileNumber.text=intent.getStringExtra("phone_no").toString()
            btnConfirm.setOnClickListener(this@EnterOtpActivity)
            resendcode.setOnClickListener(this@EnterOtpActivity)
            backArrow.setOnClickListener(this@EnterOtpActivity)
        }
    }

    private fun setEventListeners() {
        setTextChangeListeners()
    }

    private fun setTextChangeListeners() {
        setOTPChar1TextChangeListener()
        setOTPChar2TextChangeListener()
        setOTPChar3TextChangeListener()
        setOTPChar4TextChangeListener()
        setOTPChar5TextChangeListener()
    }

    private fun setOTPChar1TextChangeListener() {
        viewDataBinding.edtOtpChar1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* To provide focus to next OTP char field in sequence. */
                if (viewDataBinding.edtOtpChar1.text.toString().length == 1) {
                    viewDataBinding.edtOtpChar1.clearFocus()
                    viewDataBinding.edtOtpChar2.requestFocus()
                    viewDataBinding.edtOtpChar2.isCursorVisible = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setOTPChar2TextChangeListener() {
        viewDataBinding.edtOtpChar2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* To provide focus to next OTP char field in sequence. */
                if (viewDataBinding.edtOtpChar2.text.toString().length == 1) {
                    viewDataBinding.edtOtpChar2.clearFocus()
                    viewDataBinding.edtOtpChar3.requestFocus()
                    viewDataBinding.edtOtpChar3.isCursorVisible = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setOTPChar3TextChangeListener() {
        viewDataBinding.edtOtpChar3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* To provide focus to next OTP char field in sequence. */
                if (viewDataBinding.edtOtpChar3.text.toString().length == 1) {
                    viewDataBinding.edtOtpChar3.clearFocus()
                    viewDataBinding.edtOtpChar4.requestFocus()
                    viewDataBinding.edtOtpChar4.isCursorVisible = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setOTPChar4TextChangeListener() {
        viewDataBinding.edtOtpChar4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* To provide focus to next OTP char field in sequence. */
                if (viewDataBinding.edtOtpChar4.text.toString().length == 1) {
                    viewDataBinding.edtOtpChar4.clearFocus()
                    viewDataBinding.edtOtpChar5.requestFocus()
                    viewDataBinding.edtOtpChar5.isCursorVisible = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setOTPChar5TextChangeListener() {
        viewDataBinding.edtOtpChar5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* To provide focus to next OTP char field in sequence. */
                if (viewDataBinding.edtOtpChar5.text.toString().length == 1) {
                    viewDataBinding.edtOtpChar5.clearFocus()
                    viewDataBinding.edtOtpChar6.requestFocus()
                    viewDataBinding.edtOtpChar6.isCursorVisible = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    /* OTP waiting counter of 3*60 secs and provides option to the user for Resend OTP after 3 min*/
    private fun setOTPWaitingCounter() {
        object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewDataBinding.code.visibility = View.VISIBLE
                viewDataBinding.resendcode.visibility = View.GONE
                viewDataBinding.code.text = "${millisUntilFinished / 1000} sec"
                viewDataBinding.code.isClickable = false
            }

            override fun onFinish() {
                viewDataBinding.code.visibility = View.GONE
                viewDataBinding.resendcode.visibility = View.VISIBLE
                /*viewDataBinding.code.text = getString(R.string.resend_code)
                viewDataBinding.code.isClickable = true*/
            }
        }.start()
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnConfirm ->{
                     val _otpCharsArray : Array<String?>
                    = arrayOf(
                         viewDataBinding.edtOtpChar1.text.toString(),
                         viewDataBinding.edtOtpChar2.text.toString(),
                         viewDataBinding.edtOtpChar3.text.toString(),
                         viewDataBinding.edtOtpChar4.text.toString(),
                         viewDataBinding.edtOtpChar5.text.toString(),
                         viewDataBinding.edtOtpChar6.text.toString()
                    )

                    val otp = viewDataBinding.edtOtpChar1.text.toString()+
                            viewDataBinding.edtOtpChar2.text.toString()+
                    viewDataBinding.edtOtpChar3.text.toString()+
                    viewDataBinding.edtOtpChar4.text.toString()+
                    viewDataBinding.edtOtpChar5.text.toString()+
                    viewDataBinding.edtOtpChar6.text.toString()

                    if(validateStringArray(_otpCharsArray)){
                        verifyOtpViewModel.callVerifyOtpApi(intent.getStringExtra("country_code").toString(),intent.getStringExtra("phone_no").toString(),otp)
                    }else{
                        Toast.makeText(this,"Enter valid OTP",Toast.LENGTH_LONG).show()
                    }
                }
                R.id.resendcode->{
                    sendOtpViewModel.callSendOtpApi(intent.getStringExtra("country_code").toString(),intent.getStringExtra("phone_no").toString())
                }
                R.id.backArrow->{
                    finish()
                }
                else -> {}
            }
        }
    }

    fun validateStringArray(stringArray: Array<String?>): Boolean {
        for (string in stringArray) {
            if (string.isNullOrBlank()) {
                return false
            }
        }
        return true
    }

    override fun moveToLoginScreen(message: String) {
        if(message.equals("Verified")){
            otpVerificationFragmentViewModel.callCheckRegisteredUserApi(
                intent.getStringExtra("phone_no").toString(), intent.getStringExtra("country_code").toString()
            )
        }else if(message.equals("OTP sent successfully")){
            setOTPWaitingCounter()
        }

    }

    override fun updateWrongOtpStatus() {

    }

    override fun registerUser() {
    }

    override fun loginUser(userId: String) {
        startActivity(Intent(this,CouponActivity::class.java)
            .putExtra("userid",userId)
            .putExtra("amount",intent.getStringExtra("amount").toString())
            .putExtra("country_code",intent.getStringExtra("country_code").toString()))
    }

    override fun setProgressVisibility(visibility: Int) {
        viewDataBinding.progressBar.visibility=visibility
    }


    override fun prepareAlert(title: Int, messageResourceId: Int, message: String) {
        Toast.makeText(this@EnterOtpActivity,message,Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        viewDataBinding.progressBar.visibility=GONE
    }
}