package com.example.langswap.ui.screens.chat.chat

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.chat.Message
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageBubble(
    message: Message,
    isCurrentUser: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    maxWidthPercent: Float = 0.8f,
    timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
) {
    val alignment = if (isCurrentUser) Alignment.End else Alignment.Start

    val bubbleColor = if (isCurrentUser) Color(0xFF252EFF) else Color(0xFFF0F1FF)
    val textColor = if (isCurrentUser) Color.White else Color(0xFF1A1A1A)
    val timeColor = if (isCurrentUser) Color.White.copy(alpha = 0.7f) else Color(0xFF555770)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = alignment,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Surface(
                color = bubbleColor,
                contentColor = textColor,
                shape = shape,
                shadowElevation = 2.dp,
                modifier = Modifier.clip(shape)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 4.dp),
                        overflow = TextOverflow.Clip
                    )

                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = formatMessageTime(message.sentAt),
                            style = MaterialTheme.typography.labelSmall,
                            color = timeColor
                        )
                        if (isCurrentUser) {
                            Spacer(modifier = Modifier.width(6.dp))
                            MessageStatusIndicator(isRead = message.isRead)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageStatusIndicator(isRead: Boolean) {
    Text(
        text = if (isRead) "✓✓" else "✓",
        style = MaterialTheme.typography.labelSmall,
        color = Color.White.copy(alpha = 0.85f)
    )
}

@SuppressLint("NewApi")
private fun formatMessageTime(timestamp: String): String {
    return try {
        val dateTime = OffsetDateTime.parse(timestamp)
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy") // e.g., "Jun 16, 2025"
        dateTime.format(formatter)
    } catch (e: Exception) {
        "" // fallback in case of bad format
    }
}
