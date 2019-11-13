package com.sanketguru.myworth.adapter

import android.view.View
import com.sanketguru.myworth.R
import com.sanketguru.myworth.data.PortFolio
import com.sanketguru.myworth.data.PortFolioValue
import kotlinx.android.synthetic.main.item_asset.view.*

/**
 * Created by sanket.sphere on 31-12-2018.
 */
class PortFolioAdapter constructor(likeList: MutableList<PortFolioValue>, private val currency:String) : AbstractAdapter<PortFolioValue>(likeList, R.layout.item_asset) {
    lateinit   var clickLis:onPortfolioClick
    override fun View.bind(position: Int, item: PortFolioValue) {
        //  text_no.text = "No.: ${1 + position}"
        text_name.text = item.name
        if (item.desc?.length ?: 0 > 1) {
            text_desc.text = item.desc
            text_desc.visibility = View.VISIBLE
        } else {
            text_desc.visibility = View.GONE
        }
        text_value.text = "$currency ${numberFormater.format(item.value)}"
        text_percent.text = "${"%.2f".format(getPercent(item.value))} %"
    }

    fun totalValue(): Double {
        var total = 0.0
        itemList.forEach { total += it.value }
        return total
    }

    override fun onItemClick(itemView: View, position: Int) {
        if(clickLis!=null){
            val folio= PortFolio(itemList[position].uid,itemList[position].name,itemList[position].desc)
            clickLis.itemClicked(folio)
        }

    }
    private fun getPercent(value: Double): Double = (value / totalValue()) * 100

}
interface onPortfolioClick{
    fun itemClicked(folio: PortFolio)
}