package com.example.telegram.ui.message_recycler_view.views

import com.example.telegram.models.CommonModel
import com.example.telegram.utils.TYPE_MESSAGE_IMAGE
import com.example.telegram.utils.TYPE_MESSAGE_VOICE

class AppViewFactory {
    companion object {
        fun getView(message: CommonModel): MessageView {
            return when (message.type) {
                TYPE_MESSAGE_IMAGE -> ViewImageMessage(
                    message.from,
                    message.id,
                    message.timeStamp.toString(),
                    message.fileUrl
                )
                TYPE_MESSAGE_VOICE -> ViewVoiceMessage(
                    message.from,
                    message.id,
                    message.timeStamp.toString(),
                    message.fileUrl
                )
                else -> ViewTextMessage(
                    message.from,
                    message.id,
                    message.timeStamp.toString(),
                    message.fileUrl,
                    message.text
                )

            }
        }
    }
}