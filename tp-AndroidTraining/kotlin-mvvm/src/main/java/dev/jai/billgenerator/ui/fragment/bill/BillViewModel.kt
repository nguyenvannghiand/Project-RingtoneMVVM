package dev.jai.billgenerator.ui.fragment.bill

import androidx.databinding.ObservableField
import dev.jai.billgenerator.ui.base.BaseViewModel

class BillViewModel : BaseViewModel() {
    val  totalPrice =ObservableField<String>()
    val  salesTax =ObservableField<String>()
    val adapter = BillAdapter()
}