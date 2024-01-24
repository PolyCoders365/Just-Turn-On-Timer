package jtot.dev.feature.main

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jtot.dev.R
import jtot.dev.databinding.ItemContentBinding
import jtot.dev.databinding.ItemContentTodoBinding
import jtot.dev.databinding.ItemFolderBinding
import jtot.dev.model.Folder
import jtot.dev.model.Schedule
import jtot.dev.model.Todo
import jtot.dev.utils.addFirst

class FolderAdapter(
    private val createFolder: (Folder) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {
    private var folderList = mutableListOf<Any>()
    private lateinit var selectedFoldersDocs: MutableList<Any>

    fun setFolderList(list: List<Any>) {
        if (list.isNotEmpty()) {
            folderList = list.toMutableList()
            notifyDataSetChanged()
        }
    }

    fun getFolderList() = folderList

    fun createNewFolder() {
        folderList = folderList.addFirst(Folder("새로운 폴더", listOf())).toMutableList()
        notifyDataSetChanged()
    }

    fun deleteLastFolder() {
        folderList.removeLast()
    }

    fun addNewFolder(title: String) {
        folderList.add(Folder(title = title, listOf()))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            1 -> {
                FolderViewHolder(
                    ItemFolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                    createFolder = { value ->

                        Log.e("CF 1", "!!")
                        createFolder(value)
                    },
                )
            }

            2 -> {
                TodoViewHolder(
                    ItemContentTodoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }
            // 3 -> schedule viewholder

            else -> {
                TextViewHolder(
                    ItemContentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = folderList[position]
        if (item is Folder) {
            return 1
        } else if (folderList[position] is Todo) {
            return 2
        } else if (folderList[position] is Schedule) {
            return 3
        }
        return 99
    }

    override fun getItemCount(): Int = folderList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is TodoViewHolder -> {
                holder.bind(folderList[position] as Todo)
                holder.btnMore.visibility = INVISIBLE
                holder.layoutBlock.setOnClickListener {
                    // TODO: Todo 보기 페이지 이동
                }
            }

            is FolderViewHolder -> {
                holder.bind(folderList[position] as Folder)

                // edittext focus에 따른 수정 기능
                holder.etTitle.setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        holder.etTitle.clearFocus()
                    }
                    false
                }
                holder.etTitle.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        holder.layoutFolder.background =
                            ContextCompat.getDrawable(
                                holder.itemView.context,
                                R.drawable.background_folder_create,
                            )
                    } else {
                        folderList[position] = (folderList[position] as Folder).copy(title = holder.etTitle.text.toString())
                        holder.layoutFolder.background =
                            ContextCompat.getDrawable(
                                holder.itemView.context,
                                R.drawable.background_todo_top,
                            )
                    }
                }
                /*
                // expand 처리
                View.OnClickListener { view ->

                    // var clickedFolder = folderList[position] as Folder
                    ToggleAnimation.toggleArrow(view = holder.btnMore, isExpanded = holder.isExpand)
                    if (holder.isExpand) {
                        ToggleAnimation.expand(holder.layoutExpand)
                        holder.layoutFolder.background =
                            ContextCompat.getDrawable(
                                holder.itemView.context,
                                R.drawable.background_todo_top_expand,
                            )
                    } else {
                        ToggleAnimation.collapse(holder.layoutExpand)
                        holder.layoutFolder.background =
                            ContextCompat.getDrawable(
                                holder.itemView.context,
                                R.drawable.background_todo_top,
                            )
                    }
                    holder.isExpand = !(holder.isExpand)
                    holder.rvNestContent.run {
                        adapter =
                            nestAdapter.apply {
                                holder.bindFolder?.let {
                                    setFolderList(it.docs)
                                }
                            }

                        if (this.itemDecorationCount < 1) {
                            addItemDecoration(
                                ContentDecoration(
                                    holder.itemView.context.dpToPixel(16f).toInt(),
                                ),
                            )
                        }
                    }
                }.apply {
                    holder.btnMore.setOnClickListener(this)
                    holder.layoutFolder.setOnClickListener(this)
                }
                 */

                // 폴더 버튼 처리
                holder.btnFolder.setOnClickListener {
                    val popup = PopupMenu(holder.itemView.context, it)
                    popup.menuInflater.inflate(R.menu.menu_folder, popup.menu)
                    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                        when (menuItem.itemId) {
                            R.id.item_create_folder -> {
//                                val currentFolder = getFolderList().find { value -> value == holder.bindFolder }
//                                val newDocs = (folderList[position] as Folder).docs.toMutableList().addFirst(Folder("새로운 폴더"))
//                                folderList[position] = (folderList[position] as Folder).copy(docs = newDocs)
//                                createFolder(folderList[position] as Folder)

                                createFolder(holder.bindFolder)
                            }
                        }
                        true
                    }
                    popup.show()
                }
            }
        }
    }
}
