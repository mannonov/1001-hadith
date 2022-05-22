package uz.h1001.hadith.presentation.ui.fragment

import android.app.SearchManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.h1001.hadith.R
import uz.h1001.hadith.core.Constants
import uz.h1001.hadith.databinding.FragmentHomeBinding
import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.model.Response
import uz.h1001.hadith.presentation.ui.adapter.HomeAdapter
import uz.h1001.hadith.presentation.util.setUpAdapter
import uz.h1001.hadith.presentation.viewmodel.HadithsViewModel

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var searchView: SearchView
    private var adapter: HomeAdapter = HomeAdapter()
    private val hadithsViewModel:HadithsViewModel by viewModels()

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
        subscribeHomeContent(hadithsViewModel.hadithsLiveData)
    }

    private fun subscribeHomeContent(hadithsLiveData: LiveData<Response<List<Hadith>>>) {
        hadithsLiveData.observe(viewLifecycleOwner){
            when(it){
                is Response.Loading -> {
                    Snackbar.make(binding.root,"Loading",Snackbar.LENGTH_LONG).show()
                }
                is Response.Error -> {
                    Snackbar.make(binding.root,"Error ${it.message}",Snackbar.LENGTH_LONG).show()
                    Log.d(Constants.TAG, "subscribeHomeContent: ${it.message}")
                }
                is Response.Success -> {
                    adapter.submitList(it.data)
                }
            }
        }
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