package com.relevanttechnosketch.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class DesignAdapter(
    private val items: List<DesignModel>,
    private val onItemClick: (DesignModel) -> Unit
) : RecyclerView.Adapter<DesignAdapter.DesignViewHolder>() {

    class DesignViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCardBadge: TextView = view.findViewById(R.id.tvCardBadge)
        val tvFormatBadge: TextView = view.findViewById(R.id.tvFormatBadge)
        val tvDesignTitle: TextView = view.findViewById(R.id.tvDesignTitle)
        val tvDesignDesc: TextView = view.findViewById(R.id.tvDesignDesc)
        val btnOrderThisDesign: MaterialButton = view.findViewById(R.id.btnOrderThisDesign)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_design_card, parent, false)
        return DesignViewHolder(view)
    }

    override fun onBindViewHolder(holder: DesignViewHolder, position: Int) {
        val item = items[position]
        holder.tvCardBadge.text = item.software
        holder.tvFormatBadge.text = item.fileFormats
        holder.tvDesignTitle.text = item.title
        holder.tvDesignDesc.text = "${item.description}\n⚙️ ${item.specifications}"

        holder.btnOrderThisDesign.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}
