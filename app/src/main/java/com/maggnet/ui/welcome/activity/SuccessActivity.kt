package com.maggnet.ui.welcome.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.maggnet.MaggnetApplication
import com.maggnet.R
import com.maggnet.databinding.ActivityAmountEntryBinding
import com.maggnet.databinding.ActivityScanQrcodeBinding
import com.maggnet.databinding.ActivitySuccessScreenBinding
import com.maggnet.databinding.ActivityWelcomeBinding
import com.maggnet.ui.base.BaseActivity

import com.maggnet.utils.AppLanguageUtils
import com.maggnet.utils.PreferenceManager
import com.maggnet.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SuccessActivity : BaseActivity<ActivitySuccessScreenBinding>(ActivitySuccessScreenBinding::inflate),
    View.OnClickListener {

    @Inject
    lateinit var preferenceManager: PreferenceManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}

    override fun initUserInterface() {
        viewDataBinding.apply {
            name.text=intent.getStringExtra("userName")
            totalDiscount.text=intent.getStringExtra("grandTotal")
          //  val point=intent.getStringExtra("cartTotal").toString().toDouble()-intent.getStringExtra("grandTotal").toString().toDouble()
         //   points.text="Got "+point.toString()+" Points"
            btnNext.setOnClickListener(this@SuccessActivity)

        }
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnNext ->{
                    preferenceManager.clearSharedPreferences()
                    startActivity(Intent(this,AmountEntryActivity::class.java))

                }
                else -> {}
            }
        }
    }


}