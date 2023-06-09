package com.hana.codetest.utilities

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import com.hana.codetest.R
import javax.inject.Inject

class LoadingDialog @Inject
constructor(context: Activity) {
    private val dialog = Dialog(context)
    fun show() {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}