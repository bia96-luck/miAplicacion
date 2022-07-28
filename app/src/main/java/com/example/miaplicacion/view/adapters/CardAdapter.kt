package com.example.miaplicacion.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miaplicacion.database.user.Cards
import com.example.miaplicacion.databinding.ItemCardBinding

class CardAdapter(private var data: List<Cards>?,private val context: Context): RecyclerView.Adapter<CardAdapter.CardViewHolder>(){

    private lateinit var mListener:OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onItemLongClick(v: View, position: Int)
        fun onFavClick(position: Int)
    }



    fun setOnItemClickListener(listener: OnItemClickListener){

        mListener = listener
    }

    inner class CardViewHolder(private val binding: ItemCardBinding,listener: OnItemClickListener): RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            binding.tvStoreName.text = data?.get(position)?.storeName.toString()
            binding.tvStoreType.text = data?.get(position)?.storeType.toString()
            if (data?.get(position)?.cardsFav == true) binding.cbFav.isChecked = true
            if (data?.get(position)?.cardsFav == false) binding.cbFav.isChecked = false
        }

        init {
            binding.root.setOnClickListener { listener.onItemClick(adapterPosition) }
            binding.root.setOnLongClickListener { listener.onItemLongClick(itemView,adapterPosition)
                return@setOnLongClickListener true
            }
            binding.cbFav.setOnClickListener {
                listener.onFavClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.minimumHeight = 200
        return (CardViewHolder(binding, mListener))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Cards>){
        this.data = data
        notifyDataSetChanged()
    }

}