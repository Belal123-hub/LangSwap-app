package com.example.langswap.ui.screens.chat.chatList

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.chat.Chat
import com.example.langswap.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListItem(
    chat: Chat,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Get the other participant (not current user)
    val otherParticipant = chat.participants.firstOrNull { it.userId != getCurrentUserId() }
        ?: chat.participants.first()


    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F8FF) // Light indigo tint
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profile image with fallback
            if (!otherParticipant.profilePhotoUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = otherParticipant.profilePhotoUrl,
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3F51B5)), // Indigo fallback
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = otherParticipant.fullName.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = otherParticipant.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1A1A1A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (chat.lastMessage != null) {
                    Text(
                        text = chat.lastMessage!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4A4A4A),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Text(
                text = formatShortTime(chat.lastUpdated),
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF888AA0)
            )
        }
    }
}

// Helper function (put this in your utils)
@SuppressLint("NewApi")
private fun formatShortTime(timestamp: String): String {
    return try {
        val dateTime = OffsetDateTime.parse(timestamp)
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy") // e.g., "Jun 16, 2025"
        dateTime.format(formatter)
    } catch (e: Exception) {
        "" // fallback in case of bad format
    }
}

// Mock function - replace with your actual current user ID retrieval
private fun getCurrentUserId(): String = "current_user_id"