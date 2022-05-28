package uz.h1001.hadith.presentation.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.h1001.hadith.R
import uz.h1001.hadith.databinding.FragmentDetailBinding
import uz.h1001.hadith.domain.model.HadithNavigation

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.back)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        setupContent(hadith = args.hadith)
    }

    private fun setupContent(hadith: HadithNavigation) {
        binding.apply {
            tvChapterNumber.text = hadith.number
            tvChapterTitle.text = hadith.title
            tvContent.text = hadith.description
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavHostFragment.findNavController(this).navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}