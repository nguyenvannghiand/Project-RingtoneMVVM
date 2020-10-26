package dev.jai.billgenerator.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.github.inflationx.viewpump.ViewPumpContextWrapper


abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {
    protected lateinit var mBinding: DB
    protected lateinit var mViewModel: VM

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutRes())

    }

    override fun attachBaseContext(newBase: Context?) {
        if (null != newBase) {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }
}