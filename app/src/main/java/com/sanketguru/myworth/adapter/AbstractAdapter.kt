package com.sanketguru.myworth.adapter


/**
 * Created by Sanket Gurav on 1/5/2018.
 */

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sanketguru.myworth.utils.extensions.inflate


abstract class AbstractAdapter<ITEM> constructor(protected var itemList: MutableList<ITEM>,
                                                 private val layoutResId: Int)
    : androidx.recyclerview.widget.RecyclerView.Adapter<AbstractAdapter.Holder>() {

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = parent inflate layoutResId
        val viewHolder = Holder(view)
        val itemView = viewHolder.itemView
        itemView.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition
            if (adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                onItemClick(itemView, adapterPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AbstractAdapter.Holder, position: Int) {
        val item = itemList[position]
        holder.itemView.bind(position,item)
    }

    fun update(items: List<ITEM> , isUpdate : Boolean) {
        if (!isUpdate) {
            itemList.clear()
        }
        itemList.addAll(items)
        updateAdapterWithDiffResult(calculateDiff(items))
    }

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newItems: List<ITEM>) =
            DiffUtil.calculateDiff(DiffUtilCallback(itemList, newItems))

    fun add(item: ITEM) {
        itemList.toMutableList().add(item)
        notifyItemInserted(itemList.size)

    }

    fun remove(position: Int) {
        itemList.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    final override fun onViewRecycled(holder: Holder) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.itemView)
    }

    protected open fun onViewRecycled(itemView: View) {
    }

    protected open fun onItemClick(itemView: View, position: Int) {
    }

    protected open fun View.bind( position: Int,item: ITEM) {
    }

    class Holder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}