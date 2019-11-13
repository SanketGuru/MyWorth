package com.sanketguru.myworth.view.liability

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.MyWorthApp
import com.sanketguru.myworth.R
import com.sanketguru.myworth.adapter.LiabilityAdapter
import com.sanketguru.myworth.adapter.onLiabilytyClick
import com.sanketguru.myworth.data.Liability
import com.sanketguru.myworth.utils.extensions.Config.getCurrency
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_portfolio_list.*
import java.text.NumberFormat

/**
 * Created by sanket.sphere on 31-12-2018.
 */
class LiabilityListFragment : BaseFragment() {
    override val layout: Int = R.layout.fragment_portfolio_list
    private var assetAdapter = LiabilityAdapter(mutableListOf<Liability>(),"")
    private val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
    lateinit var callBack: AppCallBack
    lateinit var app: MyWorthApp
    private val numberFormater = NumberFormat.getNumberInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    //    title.text = LiabilityListFragment.title
        callBack = activity as AppCallBack
        list_asset.layoutManager = mLayoutManager
        app = activity!!.applicationContext as MyWorthApp
        getData()
//        fab.onClick {
//            callBack.addFragment(AddEditLiabilityFragment(), AddEditLiabilityFragment.tagTitle)
//        }
        fragmentManager!!.addOnBackStackChangedListener({ getData() })

    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        if (context is AppCallBack) {
//            callBack = context
//        }
//    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {

        val dd = Thread(Runnable {
            if (app != null) {
                val data = app.db.liabilityDao().getAll().toMutableList()
                activity?.runOnUiThread { displayData(data) }

            }
        })
        dd.start()
    }

    private fun displayData(likeList: MutableList<Liability>) {
        assetAdapter = LiabilityAdapter(likeList, getCurrency(activity))
        list_asset.adapter = assetAdapter
        text_total.text = "Total ${getCurrency(activity)} ${numberFormater.format(assetAdapter.totalValue())}"

        assetAdapter.clickLis = object : onLiabilytyClick {
            override fun itemClicked(folio: Liability) {
                callBack.addFragment(AddEditLiabilityFragment.newInstance(folio), AddEditLiabilityFragment.tagTitle)
            }
        }
    }

    companion object {
        const val tagTitle = "LiabilytyList"
        const val title = "Liability"
    }
}