package com.maggnet.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.maggnet.MaggnetApplication
import com.maggnet.ui.extensions.empty
import com.maggnet.utils.DialogManager
import javax.inject.Inject

abstract class BaseDialogFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    DialogFragment() {


    @Inject
    lateinit var dialogManager: DialogManager


    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val viewDataBinding: VB
        get() = _binding as VB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(layoutInflater)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserInterface(view)
    }

    //endregion

    //region UTIL
    protected abstract fun initUserInterface(view: View?)

    open fun displayAlert(
        title: Int = 0,
        messageResourceId: Int = 0,
        message: String = String.empty
    ) {
        try {
            dialogManager.singleButtonDialog(
                context = requireContext(),
                title = MaggnetApplication.instance.getString(title),
                message = if (messageResourceId == 0) message else MaggnetApplication.instance.getString(
                    messageResourceId
                ),
                cancelable = true
            )
        } catch (e: Exception) {
        }
    }

}