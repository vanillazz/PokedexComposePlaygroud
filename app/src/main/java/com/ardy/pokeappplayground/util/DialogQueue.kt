package com.ardy.pokeappplayground.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ardy.pokeappplayground.presentation.component.common.GenericDialogInfo
import com.ardy.pokeappplayground.presentation.component.common.PositiveAction
import java.util.*

class DialogQueue {

    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    fun removeHeadMessage() {
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove()
            queue.value = ArrayDeque()
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .description(description)
                .onDismiss(this::removeHeadMessage)
                .positive(
                    PositiveAction(
                        positiveBtnText = "Ok",
                        onPositiveAction = this::removeHeadMessage
                    )
                )
                .build()
        )
    }
}