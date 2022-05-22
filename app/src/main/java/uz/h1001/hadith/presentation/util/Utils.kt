package uz.h1001.hadith.presentation.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setUpAdapter(rvAdapter: Any, rvLayoutManager: RecyclerView.LayoutManager) {
    this.apply {
        layoutManager = rvLayoutManager
        adapter = rvAdapter as RecyclerView.Adapter<*>
    }
}