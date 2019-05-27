package com.sanketguru.myworth.view.asset

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.MyWorthApp
import com.sanketguru.myworth.R
import com.sanketguru.myworth.adapter.AssetAdapter
import com.sanketguru.myworth.adapter.onItemClick
import com.sanketguru.myworth.data.Asset
import com.sanketguru.myworth.data.PortFolio
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.BaseFragment
import com.sanketguru.myworth.view.folio.AddEditFolioFragment
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.NumberFormat

/**
 * A placeholder fragment containing a simple view.
 */
class AssetListFragment : BaseFragment() {


    var assetAdapter = AssetAdapter(mutableListOf<Asset>())
    private val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
    lateinit var callBack: AppCallBack
    override val layout: Int = R.layout.fragment_main
    private val numberFormater = NumberFormat.getNumberInstance()
    private lateinit var portFolio: PortFolio


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            portFolio = arguments!!.getParcelable<PortFolio>(ARG_PARAM1)


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_asset.layoutManager = mLayoutManager
        text_folio_name.text = portFolio.name
        btn_folio_name.onClick {
            callBack.addFragment(
                    AddEditFolioFragment.newInstance(portFolio)
                    , AddEditFolioFragment.tagTitle
            )
        }
        getData()

        callBack = activity as AppCallBack
        fab.onClick {
            callBack.addFragment(AddEditAssetFragment.newInstance(portFolio.uid), AddEditAssetFragment.tagTitle)
        }
        fragmentManager!!.addOnBackStackChangedListener({ getData() })
    }

    override fun onResume() {
        super.onResume()
        getData()
    }


    private fun getData() {
        val ddAc = activity
        if (ddAc != null) {
            val app = ddAc.applicationContext as MyWorthApp
            val dd = Thread(Runnable {
                val data = app.db.assetDao().getAssetInPortFolio(portFolio.uid).toMutableList()
                ddAc.runOnUiThread { displayData(data) }
            })
            dd.start()
        }

    }

    private fun displayData(likeList: MutableList<Asset>) {
        assetAdapter = AssetAdapter(likeList)
        list_asset.adapter = assetAdapter


        text_total.text = "Total ₹ ${numberFormater.format(assetAdapter.totalValue())}"

        assetAdapter.clickLis = object : onItemClick {
            override fun itemClicked(asset: Asset) {
                callBack.addFragment(
                        ViewAssetFragment.newInstance(asset)
                        , ViewAssetFragment.tagTitle
                )
            }
        }
    }


    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        const val tagTitle = "AssetList"
        fun newInstance(folio: PortFolio): AssetListFragment {
            val fragment = AssetListFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, folio)
            fragment.arguments = args
            return fragment
        }
    }


}

