package com.andreborud.xkcdviewer.comics

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andreborud.common.XkcdComic
import com.andreborud.xkcdviewer.R
import com.andreborud.xkcdviewer.databinding.FragmentComicsBinding
import com.andreborud.xkcdviewer.extensions.getSerializableSafe
import com.andreborud.xkcdviewer.extensions.show
import com.andreborud.xkcdviewer.extensions.viewBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComicsFragment : Fragment(R.layout.fragment_comics) {

    private val viewModel: ComicsViewModel by viewModels()
    private val binding by viewBinding(FragmentComicsBinding::bind)

    private val isViewSingle by lazy { arguments?.getString(COMIC_NUMBER) != null }
    private val isViewSaved by lazy { arguments?.getSerializableSafe(COMIC, XkcdComic::class.java) != null }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.comicView.minimumScale = 0.7f
        disableUi()

        binding.next.setOnClickListener {
            disableUi()
            viewModel.onUser(intent = ComicsIntent.DownloadNext)
        }

        binding.previous.setOnClickListener {
            disableUi()
            viewModel.onUser(intent = ComicsIntent.DownloadPrevious)
        }

        binding.share.setOnClickListener {
            viewModel.onUser(intent = ComicsIntent.Share)
        }

        binding.save.setOnClickListener {
            viewModel.onUser(intent = ComicsIntent.Save)
        }

        binding.explanation.setOnClickListener {
            viewModel.onUser(intent = ComicsIntent.ShowExplanation)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is ComicsState.SaveLatest -> {
                        val sharedPreferences = requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putInt(LATEST, state.number)
                        editor.apply()
                    }

                    is ComicsState.OnComicDownloaded -> {
                        loadComicImage(state.comic.img)
                        setUi(state.comic, state.isFirst, state.isLatest, state.isSaved)
                    }

                    is ComicsState.OnLoadSaved -> {
                        val image = state.bitmap
                        if (image != null) {
                            binding.comicView.setImageBitmap(image)
                            enableUi()
                        }
                        else
                            loadComicImage(state.comic.img)
                        setUi(state.comic, isFirst = true, isLatest = true, isSaved = true)
                    }

                    ComicsState.OnSaved -> {
                        binding.save.setImageResource(R.drawable.ic_bookmark_saved)
                    }

                    ComicsState.OnUnsaved -> {
                        binding.save.setImageResource(R.drawable.ic_bookmark)
                        if (isViewSaved) findNavController().navigateUp()
                    }

                    is ComicsState.OnError -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        enableUi()
                    }

                    is ComicsState.OnShare -> {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            val textToShare = getString(R.string.share_message, state.link)
                            putExtra(Intent.EXTRA_TEXT, textToShare)
                        }
                        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)))
                    }

                    is ComicsState.OnShowExplanation -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(state.link)))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onUser(ComicsIntent.Resume)
    }

    private fun loadComicImage(url: String) {
        Picasso.get()
            .load(url)
            .into(binding.comicView, object : Callback {
                override fun onSuccess() {
                    binding.comicView.setScale(0.8f, true)
                    enableUi()
                }

                override fun onError(e: Exception?) {}
            })
    }

    private fun setUi(comic: XkcdComic, isFirst: Boolean, isLatest: Boolean, isSaved: Boolean) {
        binding.title.text = getString(R.string.comic_title, comic.title, comic.num.toString())
        binding.description.text = comic.alt
        binding.description.show(comic.alt.isNotEmpty())
        binding.comicView.setScale(0.8f, true)
        binding.next.show(!isLatest && !isViewSaved && !isViewSingle)
        binding.previous.show(!isFirst && !isViewSaved && !isViewSingle)
        binding.save.setImageResource(if (isSaved) R.drawable.ic_bookmark_saved else R.drawable.ic_bookmark)
    }

    private fun disableUi() {
        binding.loading.show(true)
        binding.next.isEnabled = false
        binding.previous.isEnabled = false
        binding.share.isEnabled = false
        binding.save.isEnabled = false
    }

    private fun enableUi() {
        binding.loading.show(false)
        binding.next.isEnabled = true
        binding.previous.isEnabled = true
        binding.share.isEnabled = true
        binding.save.isEnabled = true
    }

    companion object {
        const val PREFS = "comic_prefs"
        const val LATEST = "latest_comic"
        const val COMIC_NUMBER = "comicNumber"
        const val COMIC = "comic"
    }
}