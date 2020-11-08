package org.eu5.githubclient

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.eu5.githubclient.model.Item

class CustomAdapter(val userList: ArrayList<Item>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(itm: Item) {
            val textViewName = itemView.findViewById(R.id.TVItemName) as TextView
            val textViewAddress  = itemView.findViewById(R.id.TVItemValue) as TextView
            textViewName.text = itm.nm
            textViewAddress.text = itm.vl
        }
    }
}
