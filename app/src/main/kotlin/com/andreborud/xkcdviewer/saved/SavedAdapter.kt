package com.andreborud.xkcdviewer.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreborud.common.XkcdComic
import com.andreborud.xkcdviewer.databinding.ItemSavedComicBinding

class SavedAdapter (private val comics: List<XkcdComic>,
                    private val onItemClick: (XkcdComic) -> Unit,
                    private val onSavedClick: (XkcdComic) -> Unit) : RecyclerView.Adapter<SavedAdapter.ComicViewHolder>() {

    class ComicViewHolder(private val binding: ItemSavedComicBinding,
                          private val onItemClick: (XkcdComic) -> Unit,
                          private val onSavedClick: (XkcdComic) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comic: XkcdComic) {
            binding.title.text = comic.title
            binding.subtitle.text = comic.num.toString()
            binding.root.setOnClickListener {
                onItemClick.invoke(comic)
            }
            binding.saved.setOnClickListener {
                onSavedClick.invoke(comic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {

        return ComicViewHolder(
            ItemSavedComicBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick, onSavedClick)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(comics[position])
    }

    override fun getItemCount() = comics.size
}