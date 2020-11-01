package com.givenocode.getupsidetest.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.givenocode.getupsidetest.R
import com.givenocode.getupsidetest.data.model.Place
import kotlinx.android.synthetic.main.list_item_place.view.*

class PlacesListAdapter : RecyclerView.Adapter<PlacesListAdapter.Holder>() {

    private val items = mutableListOf<Place>()

    fun setItems(items: List<Place>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_place, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(place: Place) {
            itemView.labelTextView.text = place.label
        }
    }
}