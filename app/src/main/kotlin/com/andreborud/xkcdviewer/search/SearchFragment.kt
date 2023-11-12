package com.andreborud.xkcdviewer.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreborud.xkcdviewer.R
import com.andreborud.xkcdviewer.comics.ComicsFragment
import com.andreborud.xkcdviewer.databinding.FragmentSearchBinding
import com.andreborud.xkcdviewer.extensions.ComicNumberFilter
import com.andreborud.xkcdviewer.extensions.viewBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences(ComicsFragment.PREFS, Context.MODE_PRIVATE)
        val latestComicNumber = sharedPreferences.getInt(ComicsFragment.LATEST, 1)
        binding.search.hint = getString(R.string.search_hint, latestComicNumber.toString())
        binding.search.filters = arrayOf(ComicNumberFilter(1, latestComicNumber))

        binding.search.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                loadComicsFragment(v.text.toString())
                hideSoftKeyboard()
                true // Return true, action handled
            } else {
                false // Return false, action not handled
            }
        }
    }

    private fun loadComicsFragment(comicNumber: String) {
        val fragment = ComicsFragment().apply {
            arguments = Bundle().apply {
                putString(ComicsFragment.COMIC_NUMBER, comicNumber)
            }
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.search.windowToken, 0)
    }
}