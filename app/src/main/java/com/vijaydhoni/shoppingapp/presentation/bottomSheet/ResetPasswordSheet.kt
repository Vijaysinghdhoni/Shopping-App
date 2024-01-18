package com.vijaydhoni.shoppingapp.presentation.bottomSheet

import android.content.res.Resources
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vijaydhoni.shoppingapp.R

fun Fragment.setupBottomSheetDialog(
    onsendClick: (String) -> Unit
) {

    val dialog = BottomSheetDialog(
        requireContext(),
        R.style.DialogStyle
    )

    val view = layoutInflater.inflate(R.layout.reset_password_bottom_sheet_layout, null)

    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val email = view.findViewById<EditText>(R.id.user_reset_email)
    val buttonSend = view.findViewById<Button>(R.id.send_mail_button)
    val cancleBttn = view.findViewById<Button>(R.id.cancel_dialog_bttn)

    buttonSend.setOnClickListener {
        val remail = email.text.toString().trim()
        if (remail.isEmpty()) {
            email.requestFocus()
            email.error = "Email cannot be empty!"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(remail).matches()) {
            email.requestFocus()
            email.error = "Email format invalid!"
        } else {
            onsendClick(remail)
            dialog.dismiss()
        }
    }

    cancleBttn.setOnClickListener {
        dialog.dismiss()
    }
}