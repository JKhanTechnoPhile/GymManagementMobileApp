package com.fitness.enterprise.management.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AlertDialog {

    fun showAlert(context: Context, title: String? = null, message: String? = null, neutralButtonText: String? = null, negativeButtonText: String? = null, positiveButtonText: String? = null) {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
        title?.let {
            materialAlertDialogBuilder.setTitle(it)
        }
        message?.let {
            materialAlertDialogBuilder.setMessage(it)
        }
        neutralButtonText?.let {
            materialAlertDialogBuilder.setNeutralButton(it) { dialog, which ->

            }
        }
        negativeButtonText?.let {
            materialAlertDialogBuilder.setNegativeButton(it) { dialog, which ->

            }
        }
        positiveButtonText?.let {
            materialAlertDialogBuilder.setPositiveButton(it) { dialog, which ->

            }
        }
        materialAlertDialogBuilder.show()
    }
}