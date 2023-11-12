package com.andreborud.xkcdviewer.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreborud.xkcdviewer.R
import com.andreborud.xkcdviewer.databinding.FragmentSearchBinding
import com.andreborud.xkcdviewer.extensions.viewBinding

class SearchFragment: Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}