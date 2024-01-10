package notifications

import com.mindovercnc.linuxcnc.domain.model.Message

data class NotificationsState(
    val messageList: List<Message>
)