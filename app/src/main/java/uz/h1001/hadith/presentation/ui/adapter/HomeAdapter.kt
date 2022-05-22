package uz.h1001.hadith.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.h1001.hadith.databinding.ItemChapterBinding
import uz.h1001.hadith.domain.model.Hadith

class HomeAdapter() : ListAdapter<Hadith, HomeAdapter.ViewHolder>(ItemComparator()) {

    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hadith) {
            binding.apply {
                tvChapterNumber.text = "${item.number} - hadis"
                tvChapterTitle.text = item.title
            }
            binding.root.setOnClickListener {
                itemClickListener.onClick(item)
            }
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    class ItemClickListener(val onClick: (hadith: Hadith) -> Unit)

    class ItemComparator : DiffUtil.ItemCallback<Hadith>() {

        override fun areItemsTheSame(oldItem: Hadith, newItem: Hadith): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hadith, newItem: Hadith): Boolean {
            return oldItem.description == newItem.description &&
                    oldItem.number == newItem.number &&
                    oldItem.title == newItem.title
        }

    }

}