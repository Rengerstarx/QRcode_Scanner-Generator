package com.example.scanner2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner2.databinding.BlockBinding

class HistoryAdapter(val listener: Listener): RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {
    val HistoryList=ArrayList<History>()

    class HistoryHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = BlockBinding.bind(item)
        fun bind(history: History, listener: Listener) = with(binding){
            textTitle.text=history.text
            carder.setOnClickListener{
                listener.onClick(history)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.block,parent,false)
        return  HistoryHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(HistoryList[position], listener )
    }

    override fun getItemCount(): Int {
        return HistoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun HistiryCreate(text: String){
        HistoryList.add(History(HistoryList.size,text))
        notifyDataSetChanged()
    }

    fun deleter(){
        var t=HistoryList.size
        var h=0
        while(h<t){
            HistoryList.removeAt(0)
            h+=1
        }
    }

    fun GetItemSize():String{
        return HistoryList.size.toString()
    }

    interface Listener{
        fun onClick(history: History)
    }
}