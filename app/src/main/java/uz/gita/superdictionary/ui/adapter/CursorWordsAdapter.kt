package uz.gita.superdictionary.ui.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.zhanghai.android.fastscroll.PopupTextProvider
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.databinding.ItemWordBinding
import uz.gita.superdictionary.util.toNormalCase


class CursorWordsAdapter : RecyclerView.Adapter<CursorWordsAdapter.Holder>(), PopupTextProvider {

    private var itemClick: ((WordEntity) -> Unit)? = null
    private var toggleClick: ((WordEntity) -> Unit)? = null

    private var cursor: Cursor? = null
    private var query: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitCursor(cursor: Cursor, query: String? = null) {
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()
    }

    inner class Holder(private val itemWordBinding: ItemWordBinding) :
        RecyclerView.ViewHolder(itemWordBinding.root) {
        fun bind() {
            cursor?.let { cursor ->
                cursor.moveToPosition(absoluteAdapterPosition)


                val data = WordEntity(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("en_word")),
                    cursor.getString(cursor.getColumnIndexOrThrow("en_definition")),
                    cursor.getString(cursor.getColumnIndexOrThrow("example")),
                    cursor.getString(cursor.getColumnIndexOrThrow("synonyms")),
                    cursor.getString(cursor.getColumnIndexOrThrow("antonyms")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("isSaved")),
                )

                itemWordBinding.apply {
                    this.textView.text = toNormalCase(data.word)
                    this.isSavedTgbtn.isChecked = data.isSaved == 1
                }

                itemWordBinding.root.setOnClickListener {
                    itemClick!!.invoke(data)
                }

                itemWordBinding.isSavedTgbtn.setOnClickListener {
                    if (data.isSaved == 1) {
                        data.isSaved = 0
                    } else data.isSaved = 1
                    toggleClick!!.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind()
    }

    override fun getItemCount(): Int {
        cursor?.let {
            return it.count
        }
        return 0
    }

    fun setItemClick(block: (WordEntity) -> Unit) {
        itemClick = block
    }

    fun setToggleClick(block: (WordEntity) -> Unit) {
        toggleClick = block
    }

    override fun getPopupText(position: Int): String {
        return cursor!!.getString(cursor!!.getColumnIndexOrThrow("en_word")).substring(0, 1)
            .uppercase()
    }

}