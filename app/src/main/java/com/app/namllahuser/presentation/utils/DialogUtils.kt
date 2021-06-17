package com.app.namllahuser.presentation.utils

import android.app.Activity
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.app.namllahuser.R
import com.app.namllahuser.presentation.base.DialogData
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.tapadoo.alerter.Alerter

class DialogUtils(val activity: Activity) {
    private var sweetAlertDialogError: SweetAlertDialog =
        SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
    private var sweetAlertDialogSuccess: SweetAlertDialog =
        SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
    private var sweetAlertDialog: SweetAlertDialog =
        SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)

    init {
        sweetAlertDialog.setCancelable(false)


    }

    fun loading(boolean: Boolean) {

        if (boolean) {
            if (!sweetAlertDialog.isShowing){
                sweetAlertDialogSuccess.create()
                sweetAlertDialog.show()
            }
        } else
            if (sweetAlertDialog.isShowing)
                sweetAlertDialog.hide()

    }

    fun showErrorMessage(message: String) {
        if (sweetAlertDialogError.isShowing) {
            sweetAlertDialogError.dismissWithAnimation()
            sweetAlertDialogError.setContentText(message)
            sweetAlertDialogError.show()
        }
    }

    fun showSuccessMessage(dialogData: DialogData) {
        if (sweetAlertDialogSuccess.isShowing) {
            sweetAlertDialogSuccess.dismissWithAnimation()
            sweetAlertDialogSuccess.titleText = dialogData.title
            sweetAlertDialogSuccess.contentText = dialogData.message
            sweetAlertDialogSuccess.create()
            sweetAlertDialogSuccess.show()
        }
    }

    fun hideSuccessMessage() {
        if (sweetAlertDialogSuccess.isShowing) {
            sweetAlertDialogSuccess.dismissWithAnimation()

        }
    }

    fun showFailAlert(message: String) {
        Alerter.create(activity)
            .setText(message)
            .setTitleAppearance(R.style.AlertTextAppearance_Title)
            .setTextAppearance(R.style.AlertTextAppearance_Text)
            .setBackgroundColorInt(ContextCompat.getColor(activity, R.color.red))
            .enableInfiniteDuration(false)
            .setDuration(3000)
            .setContentGravity(Gravity.END)
            .show()
    }

    fun showSuccessAlert(msg: String) {
        Alerter.create(activity)
            .setTitle(msg)
            .setBackgroundColorInt(ContextCompat.getColor(activity, R.color.green_light))
            .enableInfiniteDuration(false)
            .setDuration(3000)
            .setTitleAppearance(R.style.AlertTextAppearance_Title)
            .setTextAppearance(R.style.AlertTextAppearance_Text)
            .setContentGravity(Gravity.END)
            .show()

    }

}