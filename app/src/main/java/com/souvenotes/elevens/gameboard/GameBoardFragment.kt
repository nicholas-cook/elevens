package com.souvenotes.elevens.gameboard

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.souvenotes.elevens.GameCard
import com.souvenotes.elevens.R
import com.souvenotes.elevens.databinding.FragmentGameBoardBinding
import com.souvenotes.elevens.utils.fragmentViewBinding
import com.souvenotes.elevens.utils.getAdSize
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameBoardFragment : Fragment(R.layout.fragment_game_board) {

    companion object {
        private const val CARD_IMAGE_WIDTH = 222
        private const val CARD_IMAGE_HEIGHT = 323
    }

    private val binding by fragmentViewBinding { FragmentGameBoardBinding.bind(it) }

    private val viewModel: GameBoardViewModel by viewModel()

    private var paddingPixels = 0

    private var calculatedHeight: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_help -> {
                    findNavController().navigate(R.id.toHelpScreen)
                    true
                }
                R.id.menu_record -> {
                    findNavController().navigate(R.id.toRecord)
                    true
                }
                R.id.restart -> {
                    viewModel.resetBoard(true)
                    true
                }
                else -> false
            }
        }

//        val requestedAdSize = getAdSize()
//        binding.gameBoardAdContainer.layoutParams =
//            binding.gameBoardAdContainer.layoutParams.apply {
//                height = requestedAdSize.getHeightInPixels(requireContext())
//            }
//        val adView = AdView(requireContext()).apply {
//            adUnitId = "ca-app-pub-5268862472871083/7283862600"
//            adSize = requestedAdSize
//        }
//        binding.gameBoardAdContainer.addView(adView)
//        adView.loadAd(AdRequest.Builder().build())

        paddingPixels = resources.getDimensionPixelOffset(R.dimen.padding_half)
        view.post {
            binding.gameBoardContainer.iterator().forEach {
                if (it is MaterialCardView) {
                    setCardHeight(it)
                }
            }
        }
        setUpObservers()
    }

    private fun setUpObservers() = with(viewModel) {
        card1Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(1, selected, gameCard, binding.card1)
            }
        )
        card2Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(2, selected, gameCard, binding.card2)
            }
        )
        card3Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(3, selected, gameCard, binding.card3)
            }
        )
        card4Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(4, selected, gameCard, binding.card4)
            }
        )
        card5Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(5, selected, gameCard, binding.card5)
            }
        )
        card6Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(6, selected, gameCard, binding.card6)
            }
        )
        card7Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(7, selected, gameCard, binding.card7)
            }
        )
        card8Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(8, selected, gameCard, binding.card8)
            }
        )
        card9Display.observe(
            viewLifecycleOwner,
            { (selected, gameCard) ->
                adjustCard(9, selected, gameCard, binding.card9)
            }
        )
        showLosingDialog.observe(
            viewLifecycleOwner,
            {
                if (it == true) {
                    showLosingDialog()
                }
            }
        )
        loading.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    true -> {
                        with(binding.loadingOverlay) {
                            isVisible = true
                            bringToFront()
                            requestFocus()
                        }
                    }
                    else -> {
                        binding.loadingOverlay.isVisible = false
                    }
                }
            }
        )
        showWinningDialog.observe(
            viewLifecycleOwner,
            {
                if (it == true) {
                    showWinningDialog()
                }
            }
        )
    }

    private fun setCardHeight(cardView: MaterialCardView) {
        if (calculatedHeight == 0) {
            calculatedHeight = (CARD_IMAGE_HEIGHT * cardView.measuredWidth) / CARD_IMAGE_WIDTH
        }
        if (calculatedHeight > cardView.measuredHeight) {
            return
        }
        cardView.layoutParams = cardView.layoutParams.apply {
            height = calculatedHeight
        }
    }

    private fun adjustCard(
        position: Int,
        selected: Boolean,
        gameCard: GameCard,
        cardView: AppCompatImageView
    ) {
        with(cardView) {
            if (selected) {
                setOnClickListener(null)
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(R.drawable.back_2x)
                contentDescription = getString(R.string.card_selected)
                setPadding(0)
            } else {
                setOnClickListener {
                    viewModel.onCardSelected(position)
                }
                scaleType = ImageView.ScaleType.FIT_CENTER
                setImageResource(gameCard.imageRes)
                contentDescription = getString(gameCard.contentDescription)
                setPadding(paddingPixels)
            }
        }
    }

    private fun showLosingDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.ElevensAlertDialog)
            .setCancelable(false)
            .setTitle(R.string.losing_title)
            .setMessage(R.string.losing_message)
            .setPositiveButton(R.string.play_again) { dialog, _ ->
                viewModel.resetBoard()
                dialog.dismiss()
            }
            .show()
    }

    private fun showWinningDialog() {
        AlertDialog.Builder(requireContext(), R.style.ElevensAlertDialog)
            .setCancelable(false)
            .setTitle(R.string.winning_title)
            .setMessage(R.string.winning_message)
            .setPositiveButton(R.string.play_again) { dialog, _ ->
                viewModel.resetBoard()
                dialog.dismiss()
            }
            .show()
    }
}