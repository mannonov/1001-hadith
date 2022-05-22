package uz.h1001.hadith.ui.fragment

import android.app.SearchManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.h1001.hadith.R
import uz.h1001.hadith.databinding.FragmentHomeBinding
import uz.h1001.hadith.model.Hadith
import uz.h1001.hadith.ui.adapter.HomeAdapter
import uz.h1001.hadith.util.setUpAdapter


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var searchView: SearchView
    private var adapter: HomeAdapter = HomeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.appBarHome.toolbar)
        val toggle = ActionBarDrawerToggle(
            requireActivity(), binding.drawerLayout, binding.appBarHome.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        setHasOptionsMenu(true)
        binding.appBarHome.contentHome.rvMainChapters.setUpAdapter(adapter,LinearLayoutManager(requireContext()))
        subscribeHomeContent()
    }

    private fun subscribeHomeContent() {
        adapter.submitList(ArrayList<Hadith>().apply {
            for (i in 0..10){
                add(element = Hadith(number = i, title = "Title $i","Description"))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val searchManager =
            requireActivity().getSystemService(AppCompatActivity.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}