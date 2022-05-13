package uz.gita.superdictionary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.superdictionary.R
import uz.gita.superdictionary.data.db.AddedWordEntity
import uz.gita.superdictionary.databinding.ItemAddWordBinding
import uz.gita.superdictionary.util.toNormalCase

class AddedWordsAdapter : ListAdapter<AddedWordEntity, AddedWordsAdapter.ViewHolder>(MyDiff) {


    private var removeButtonClick: ((AddedWordEntity) -> Unit)? = null
    private var itemClick: ((AddedWordEntity) -> Unit)? = null
    private var editButtonClick: ((AddedWordEntity) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding by viewBinding(ItemAddWordBinding::bind)

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
            binding.editBtn.setOnClickListener {
                editButtonClick?.invoke(
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

    object MyDiff : DiffUtil.ItemCallback<AddedWordEntity>() {
        override fun areItemsTheSame(oldItem: AddedWordEntity, newItem: AddedWordEntity): Boolean {
            return oldItem.id == newItem.id && oldItem.word == newItem.word
        }

        override fun areContentsTheSame(
            oldItem: AddedWordEntity,
            newItem: AddedWordEntity
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.word == newItem.word
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_word, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setRemoveClick(block: ((AddedWordEntity) -> Unit)) {
        removeButtonClick = block
    }

    fun setEditClick(block: ((AddedWordEntity) -> Unit)) {
        editButtonClick = block
    }

    fun setItemClick(block: ((AddedWordEntity) -> Unit)) {
        itemClick = block
    }
}