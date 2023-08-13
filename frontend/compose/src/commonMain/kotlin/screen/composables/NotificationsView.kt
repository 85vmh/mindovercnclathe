package screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import usecase.MessagesUseCase
import usecase.model.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsView() {
    val messagesUseCase by rememberInstance<MessagesUseCase>()

    val messageList by messagesUseCase.getAllMessages().collectAsState(emptyList())

    val lastMessage = messageList.lastOrNull()
    var showNotification by remember { mutableStateOf(false) }

    showNotification = lastMessage != null

    Box(
        modifier = Modifier
    ) {
        lastMessage?.let {
            when (it.level) {
                Message.Level.WARNING -> {
                    Snackbar(
                        modifier = Modifier.padding(8.dp)
                            .wrapContentSize()
                            .align(Alignment.BottomEnd)
                    ) { Text(text = "Message: ${it.text}") }
                }

                Message.Level.ERROR -> {
//                    TODO
                    AlertDialog(
                        onDismissRequest = { },
                        text = {
                            Text("Message: ${it.text}")
                        },
                        confirmButton = {},
                        modifier = Modifier.height(200.dp).width(300.dp)
                    )
//                    Dialog(
//                        undecorated = true,
//                        title = "Messages",
//                        onCloseRequest = { },
//                    ) {
//                        Text("Message: ${it.text}")
//
//                    }
                }

                else -> Unit
            }
        }
    }
}