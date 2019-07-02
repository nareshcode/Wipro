package com.example.wiproexercise.activites.adapters.recyclerviews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wiproexercise.R
import com.example.wiproexercise.activites.FactsActivity
import com.example.wiproexercise.activites.contracts.FactsContract
import com.example.wiproexercise.activites.models.Row
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listitem_facts_view.view.*

class FactsAdapter(private val activity: FactsActivity, private val presenter: FactsContract.Presenter) :
    RecyclerView.Adapter<FactsAdapter.FactsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listitem_facts_view, parent, false)

        return FactsViewHolder(view)
    }

    override fun getItemCount() = presenter.getFacts().size

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        val factsData = presenter.getFacts()[position]

        holder.bindData(factsData)
    }
    
    class FactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(factsData: Row) {
            itemView.title_text.text = factsData.title
            itemView.description_text.text = factsData.description

            Picasso.with(itemView.facts_image.context)
                .load(factsData.imageHref)
                .into(itemView.facts_image)
        }
    }
}