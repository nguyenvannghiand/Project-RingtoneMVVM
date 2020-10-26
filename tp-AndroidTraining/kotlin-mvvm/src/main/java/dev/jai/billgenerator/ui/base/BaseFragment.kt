package dev.jai.billgenerator.ui.base

import androidx.lifecycle.ViewModel
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : androidx.fragment.app.Fragment() {
    protected lateinit var mBinding: DB
    protected lateinit var mViewModel: VM

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return mBinding.root
    }

}