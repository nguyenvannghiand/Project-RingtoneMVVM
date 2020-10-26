package dev.jai.billgenerator.ui.activity.main

import android.os.Bundle
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dev.jai.billgenerator.R
import dev.jai.billgenerator.databinding.ActivityMainBinding
import dev.jai.billgenerator.data.model.Basket
import dev.jai.billgenerator.ui.base.BaseActivity
import dev.jai.billgenerator.ui.fragment.bill.BillFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    lateinit var mActivityMainBinding: ActivityMainBinding
    lateinit var mDrawer: DrawerLayout
    lateinit var navController: NavController
    lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        mViewModel = MainViewModel()
        mBinding.viewModel = mViewModel
        showBasket()
        setUp()

        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    fun showBasket() {
        mViewModel.title.set(getString(R.string.title_bucket));

    }

    fun showBill(items: MutableList<Basket>) {
        val fragment = BillFragment()
        val bundle = Bundle()
        bundle.putParcelableArrayList(BillFragment.PARAM_LIST, items.toCollection(ArrayList()))
        fragment.arguments = bundle
        mViewModel.title.set(getString(R.string.title_bill));
    }

    fun setUp() {

    }
}
