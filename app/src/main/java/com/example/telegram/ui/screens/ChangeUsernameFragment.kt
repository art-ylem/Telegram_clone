package com.example.telegram.ui.screens

import com.example.telegram.R
import com.example.telegram.database.*
import com.example.telegram.utils.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*


class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String
    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }


    override fun change() {
        mNewUsername = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()) {
            showToast("Пустое поле")
        } else {
            REF_DATABASE_ROOT.child(
                NODE_USERS
            )
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) {
                        showToast(getString(R.string.settings_toast_username_has_exist))
                    } else {
                        changeUsername()

                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(
            CURRENT_UID
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername(mNewUsername)
                }
            }
    }
}