package com.sanketguru.myworth.view.folio

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.MyWorthApp
import com.sanketguru.myworth.R
import com.sanketguru.myworth.adapter.PortFolioAdapter
import com.sanketguru.myworth.adapter.onPortfolioClick
import com.sanketguru.myworth.data.PortFolio
import com.sanketguru.myworth.data.PortFolioValue
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.BaseFragment
import com.sanketguru.myworth.view.asset.AssetListFragment
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.NumberFormat

/**
 * Created by sanket.sphere on 12-12-2018.
 */
class PortFolioListFragment : BaseFragment() {
    override val layout: Int = R.layout.fragment_portfolio_list
    private var assetAdapter = PortFolioAdapter(mutableListOf<PortFolioValue>())
    private val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
    lateinit var callBack: AppCallBack
    lateinit var app: MyWorthApp
    private  val numberFormater= NumberFormat.getNumberInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_asset.layoutManager = mLayoutManager
        app = activity!!.applicationContext as MyWorthApp
        getData()

        callBack = activity as AppCallBack
//        fab.onClick {
//            callBack.addFragment(AddEditFolioFragment(), AddEditFolioFragment.tagTitle)
//        }
        fragmentManager!!.addOnBackStackChangedListener({ getData() })

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {

        val dd = Thread(Runnable {
            if (app != null) {
                val data = app.db.portfolioDao().loadAllByIdsValueTotal().toMutableList()
                activity?.runOnUiThread { displayData(data) }

            }
        })
        dd.start()
    }

    private fun displayData(likeList: MutableList<PortFolioValue>) {
        assetAdapter = PortFolioAdapter(likeList)
        list_asset.adapter = assetAdapter
        text_total.text =  "Total ₹ ${ numberFormater.format(assetAdapter.totalValue())}"

        assetAdapter.clickLis = object : onPortfolioClick {
            override fun itemClicked(folio: PortFolio) {
                callBack.addFragment(AssetListFragment.newInstance(folio), AssetListFragment.tagTitle)
            }
        }
    }

    companion object {
        const val tagTitle = "PortFolioList"
    }
}