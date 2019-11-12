package com.kirdmt.sbertranslator.app.ui.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kirdmt.sbertranslator.R
import com.kirdmt.sbertranslator.app.models.SavedWord
import kotlinx.android.synthetic.main.word_list_item.view.*


class SavedWordAdapter(
    val words: ArrayList<SavedWord>,
    private val clickListener: (SavedWord) -> Unit
) :
    RecyclerView.Adapter<SavedWordAdapter.ViewHolder>() {

    var items = words

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.word_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position], clickListener)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(word: SavedWord, clickListener: (SavedWord) -> Unit) {

            itemView.native_text.text = word.nativeWord
            itemView.translated_text.text = word.translatedWord
            itemView.setOnClickListener { clickListener(word) }

        }
    }

    fun update(word: SavedWord) {
        items.add(word)
        notifyDataSetChanged()
    }


}