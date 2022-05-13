package uz.gita.superdictionary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.superdictionary.R
import uz.gita.superdictionary.data.db.HistoryEntity
import uz.gita.superdictionary.databinding.ItemHistoryBinding
import uz.gita.superdictionary.util.toNormalCase

class HistoryAdapter : ListAdapter<HistoryEntity, HistoryAdapter.ViewHolder>(MyDiff) {

    private var removeButtonClick: ((HistoryEntity) -> Unit)? = null
    private var itemClick: ((HistoryEntity) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding by viewBinding(ItemHistoryBinding::bind)

        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                binding.textView.text = toNormalCase(this.word)
            }
        }

        init {
            binding.removeBtn.setOnClickListener {
                removeButtonClick?.invoke(
                    getItem(absoluteAdapterPosition)
                )
            }
            binding.root.setOnClickListener {
                itemClick?.invoke(
                    getItem(absoluteAdapterPosition)
                )
            }
        }

    }

    object MyDiff : DiffUtil.ItemCallback<HistoryEntity>() {
        override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
            return oldItem.id == newItem.id && oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
            return oldItem.id == newItem.id && oldItem.word == newItem.word
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setRemoveClick(block: ((HistoryEntity) -> Unit)) {
        removeButtonClick = block
    }

    fun setItemClick(block: ((HistoryEntity) -> Unit)) {
        itemClick = block
    }
}