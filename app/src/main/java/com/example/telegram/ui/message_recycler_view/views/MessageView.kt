package com.example.telegram.ui.message_recycler_view.views

interface MessageView {

    val from: String
    val id: String
    val timeStamp: String
    val fileUrl: String
    val text: String

    companion object {
        val MESSAGE_IMAGE: Int
            get() = 0
        val MESSAGE_TEXT: Int
            get() = 1
        val MESSAGE_VOICE: Int
            get() = 2

    }

    fun getTypeView(): Int


}