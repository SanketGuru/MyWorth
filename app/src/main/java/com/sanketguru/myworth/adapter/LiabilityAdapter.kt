package com.sanketguru.myworth.adapter

import android.view.View
import com.sanketguru.myworth.R
import com.sanketguru.myworth.data.Liability
import kotlinx.android.synthetic.main.item_liabilyty.view.*


/**
 * Created by sanket.sphere on 31-12-2018.
 */
class LiabilityAdapter constructor(likeList: MutableList<Liability>,private val currency :String) : AbstractAdapter<Liability>(likeList, R.layout.item_liabilyty) {
    lateinit   var clickLis:onLiabilytyClick
    override fun View.bind(position: Int, item: Liability) {
        //  text_no.text = "No.: ${1 + position}"
        text_name.text = item.name
        if (item.desc?.length ?: 0 > 1) {
            text_desc.text = item.desc
            text_desc.visibility = View.VISIBLE
        } else {
            text_desc.visibility = View.GONE
        }
        if (item.isEmi()) {
            text_emi.text = "${numberFormater.format(item.emi)} per month ${item.noOfEmi} emi"
            text_emi.visibility = View.VISIBLE
        } else {
            text_emi.visibility = View.GONE
        }
        text_value.text = "${currency} ${numberFormater.format(item.value())}"
        text_percent.text = "${"%.2f".format(getPercent(item.value()))} %"
    }

    fun totalValue(): Double {
        var total = 0.0
        itemList.forEach { total += it.value() }
        return total
    }

    override fun onItemClick(itemView: View, position: Int) {
        if(clickLis!=null){
            clickLis.itemClicked(itemList[position])
        }

    }
    private fun getPercent(value: Double): Double = (value / totalValue()) * 100

}
interface onLiabilytyClick{
    fun itemClicked(folio: Liability)
}