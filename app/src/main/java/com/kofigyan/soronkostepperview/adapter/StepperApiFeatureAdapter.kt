package com.kofigyan.soronkostepperview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kofigyan.soronkostepperview.R
import com.kofigyan.soronkostepperview.adapter.StepperApiFeatureAdapter.StepperApiFeatureHolder
import com.kofigyan.soronkostepperview.model.StepperApiFeature
import kotlinx.android.synthetic.main.list_item.view.*

class StepperApiFeatureAdapter : RecyclerView.Adapter<StepperApiFeatureHolder>() {

    var onItemClickListener: ((Int) -> Unit)? = null

    var apiFeaturesList = emptyList<StepperApiFeature>()

    fun setupApiFeatures(apiFeaturesList: List<StepperApiFeature>) {
        this.apiFeaturesList = apiFeaturesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepperApiFeatureHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return StepperApiFeatureHolder(v)
    }

    override fun getItemCount(): Int {
        return apiFeaturesList.size
    }

    override fun onBindViewHolder(holder: StepperApiFeatureHolder, position: Int) {
        holder.bindItems(apiFeaturesList[position])
    }


    inner class StepperApiFeatureHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(apiFeature: StepperApiFeature) {
            view.tv_name.text = apiFeature.name
            view.tv_description.text = apiFeature.description
        }

        init {
            view.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }
    }
}