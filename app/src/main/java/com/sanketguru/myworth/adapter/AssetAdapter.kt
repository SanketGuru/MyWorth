package com.sanketguru.myworth.adapter

import android.view.View
import androidx.preference.PreferenceManager
import com.sanketguru.myworth.R
import com.sanketguru.myworth.data.Asset
import com.sanketguru.myworth.data.PortFolio
import com.sanketguru.myworth.data.PortFolioValue
import kotlinx.android.synthetic.main.item_asset.view.*
import java.text.NumberFormat


/**
 * Created by sanket.sphere on 29-11-2018.
 */
class AssetAdapter constructor(likeList: MutableList<Asset>, val currency: String) : AbstractAdapter<Asset>(likeList, R.layout.item_asset) {
//   val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
    // val name = sharedPreferences.getString("signature", "")

    lateinit var clickLis: onItemClick

    override fun View.bind(position: Int, item: Asset) {
        //  text_no.text = "No.: ${1 + position}"
        text_name.text = item.name
        if (item.description?.length ?: 0 > 1) {
            text_desc.text = item.description
            text_desc.visibility = View.VISIBLE
        } else {
            text_desc.visibility = View.GONE
        }
        text_value.text = "${this@AssetAdapter.currency}  ${numberFormater.format(item.value)}"
        text_percent.text = "${"%.2f".format(getPercent(item.value))} %"
    }

    fun totalValue(): Double {
        var total = 0.0
        itemList.forEach { total += it.value }
        return total
    }

    override fun onItemClick(itemView: View, position: Int) {
        if (clickLis != null) {
            clickLis.itemClicked(itemList[position])
        }

    }

    private fun getPercent(value: Double): Double = (value / totalValue()) * 100

}

interface onItemClick {
    fun itemClicked(asset: Asset)
}

internal val numberFormater = NumberFormat.getNumberInstance()
