package com.maggnet.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.maggnet.R
import com.maggnet.utils.PreferenceManager
import com.maggnet.utils.ThemeUtil.makeStatusBarTransparent
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding>(private val inflate: Inflate<VB>) : AppCompatActivity() {

    //region VARIABLES

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val viewDataBinding: VB
        get() = _binding as VB



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        _binding = inflate.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        initUserInterface()
        makeStatusBarTransparent()
    }

    //region UTIL
    protected abstract fun initUserInterface()

}