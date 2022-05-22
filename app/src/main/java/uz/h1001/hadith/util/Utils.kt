package uz.h1001.hadith.util

import androidx.recyclerview.widget.RecyclerView
import uz.h1001.hadith.ui.adapter.HomeAdapter

fun RecyclerView.setUpAdapter(rvAdapter: Any, rvLayoutManager: RecyclerView.LayoutManager) {
    this.apply {
        layoutManager = rvLayoutManager
        adapter = rvAdapter as RecyclerView.Adapter<*>
    }
}