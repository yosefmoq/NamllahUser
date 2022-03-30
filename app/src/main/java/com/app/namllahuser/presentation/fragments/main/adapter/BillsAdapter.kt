package com.app.namllahuser.presentation.fragments.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.data.model.BillsData
import com.app.namllahuser.databinding.ItemBillsImageBinding

class BillsAdapter(var context: Context, var list: MutableList<BillsData>) :
    RecyclerView.Adapter<BillsAdapter.BillsViewHolder>() {
    class BillsViewHolder(var itemBillsImageBinding: ItemBillsImageBinding) :
        RecyclerView.ViewHolder(itemBillsImageBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillsViewHolder = BillsViewHolder(
        ItemBillsImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BillsViewHolder, position: Int){
        holder.itemBillsImageBinding.url = list[position].image.original
        holder.itemBillsImageBinding.executePendingBindings()
    }

    fun update(list:MutableList<BillsData>){
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeInserted(0,list.size-1)
    }
}