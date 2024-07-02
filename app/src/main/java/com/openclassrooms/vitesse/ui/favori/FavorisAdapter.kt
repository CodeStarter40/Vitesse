package com.openclassrooms.vitesse.ui.candidat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.vitesse.R
import com.openclassrooms.vitesse.domain.model.Candidat

class FavorisAdapter(private val onItemClicked: (Candidat) -> Unit) : ListAdapter<Candidat, FavorisAdapter.FavorisViewHolder>(
    CandidatDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorisViewHolder {
        //inflate the layoutXML for each item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_adapter, parent, false)

        //return the viewholder with the newinstance of the view
        return FavorisViewHolder(view,onItemClicked)
    }

    //method to bind the data to the view with position of the item
    override fun onBindViewHolder(holder: FavorisViewHolder, position: Int) {
        //get the candidat at the postion
        val candidat = getItem(position)
        Log.d("CANDIDATADAPTER", "Bind candidat at position $position: ${candidat.prenom} ${candidat.nom}")
        holder.bind(candidat)
    }

    //link the view to the candidat data
    class FavorisViewHolder(itemView: View, val onItemClicked: (Candidat) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val prenomTextView: TextView = itemView.findViewById(R.id.item_card_prenom)
        private val nomTextView: TextView = itemView.findViewById(R.id.item_card_nom)
        private val noteTextView: TextView = itemView.findViewById(R.id.item_card_note)
        private val imageView: ImageView = itemView.findViewById(R.id.item_card_image)

        //bind the candidat data to the view
        fun bind(candidat: Candidat) {
            prenomTextView.text = candidat.prenom
            nomTextView.text = candidat.nom
            noteTextView.text = candidat.note
            itemView.setOnClickListener {
                onItemClicked(candidat)
            }

            val context = itemView.context
            val resourceId = context.resources.getIdentifier(candidat.picture, "drawable", context.packageName)
            imageView.setImageResource(resourceId)

        }
    }

    //declaration of the diffutil
    class CandidatDiffCallback : DiffUtil.ItemCallback<Candidat>() {
        override fun areItemsTheSame(oldItem: Candidat, newItem: Candidat): Boolean {
            return oldItem.id == newItem.id
        }


        //verify if the candidat are the same, compare the unique id
        override fun areContentsTheSame(oldItem: Candidat, newItem: Candidat): Boolean {
            return oldItem == newItem
        }
    }
}
