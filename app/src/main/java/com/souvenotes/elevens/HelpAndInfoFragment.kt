package com.souvenotes.elevens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.souvenotes.elevens.databinding.FragmentHelpAndInfoBinding
import com.souvenotes.elevens.utils.fragmentViewBinding
import com.souvenotes.elevens.utils.setClickableSubstring

class HelpAndInfoFragment : Fragment(R.layout.fragment_help_and_info) {

    companion object {
        private const val TERMS_FILENAME = "elevens_terms_of_service.txt"
        private const val PRIVACY_FILENAME = "elevens_privacy_policy.txt"

        private const val MAIL_TO = "mailto:"
    }

    private val binding by fragmentViewBinding { FragmentHelpAndInfoBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        binding.termsLink.setClickableSubstring(R.string.terms_and_conditions) {
            findNavController().navigate(
                HelpAndInfoFragmentDirections.toLegalText(
                    getString(R.string.terms_and_conditions),
                    TERMS_FILENAME
                )
            )
        }

        binding.privacyLink.setClickableSubstring(R.string.privacy_policy) {
            findNavController().navigate(
                HelpAndInfoFragmentDirections.toLegalText(
                    getString(R.string.privacy_policy),
                    PRIVACY_FILENAME
                )
            )
        }

        binding.contactLink.setClickableSubstring(R.string.contact_us) {
            sendSupportEmailIntent()
        }
    }

    private fun sendSupportEmailIntent() {
        val sendEmailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(MAIL_TO)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email_address)))
            putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.support_email_subject, BuildConfig.VERSION_NAME)
            )
        }
        if (sendEmailIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(sendEmailIntent)
        } else {
            showEmailDialog()
        }
    }

    private fun showEmailDialog() {
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_contact_us, requireView() as ViewGroup, false)
        val dialog = AlertDialog.Builder(requireContext(), R.style.ElevensAlertDialog)
            .setCancelable(false)
            .setView(view)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        view.findViewById<View>(R.id.dialog_text).setOnClickListener {
            val clipboardManager =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            val clip = ClipData.newPlainText(
                getString(R.string.support_email_label),
                getString(R.string.support_email_address)
            )
            clipboardManager?.let {
                it.setPrimaryClip(clip)
                Toast.makeText(requireContext(), R.string.copied_to_clipboard, Toast.LENGTH_LONG)
                    .show()
            }
        }
        dialog.show()
    }
}