package com.example.langswap.ui.screens.profile.profileInfo

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.profile.model.UserPublicProfile
import com.example.langswap.ui.screens.chat.chat.ChatCreationState
import com.example.langswap.ui.screens.chat.chat.ChatViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PublicProfileScreen(
    navController: NavController,
    userId: String,
    viewModel: PublicProfileViewModel = koinViewModel(),
    chatViewModel: ChatViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val chatUiState by chatViewModel.uiState.collectAsState()
    val chatCreationState by chatViewModel.chatCreationState.collectAsState()
    val isStartingChat = chatCreationState is ChatCreationState.Loading

    LaunchedEffect(chatCreationState) {
        when (chatCreationState) {
            is ChatCreationState.Success -> {
                val chatId = (chatCreationState as ChatCreationState.Success).chatId
                navController.navigate("chat/$chatId")
                chatViewModel.resetChatCreationState()
            }
            is ChatCreationState.Error -> {
                Toast.makeText(context, "Failed to create chat", Toast.LENGTH_SHORT).show()
                chatViewModel.resetChatCreationState()
            }
            else -> {}
        }
    }

    LaunchedEffect(userId) {
        if (userId.isBlank()) {
            Toast.makeText(context, "Invalid user ID", Toast.LENGTH_SHORT).show()
            return@LaunchedEffect
        }
        viewModel.loadPublicProfile(userId)
    }

    val onStartChat = {
        if (!isStartingChat) {
            viewModel.startChatIfPossible(context, chatViewModel, userId)
        }
    }

    when {
        state.isLoading -> FullScreenLoader()
        state.error != null -> ErrorMessage(state.error!!)
        state.profile != null -> ProfileContent(
            profile = state.profile!!,
            onStartChat = onStartChat,
            isLoadingChat = isStartingChat
        )
    }
}

@Composable
private fun FullScreenLoader() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(error: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error: $error", modifier = Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileContent(
    profile: UserPublicProfile,
    onStartChat: () -> Unit,
    isLoadingChat: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profile.profilePhotoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            tonalElevation = 4.dp,
            shape = MaterialTheme.shapes.large,
            color = Color(0xFFF7F8FF) // Light indigo background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = profile.fullName,
                        style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFF252EFF)),
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = onStartChat,
                        enabled = !isLoadingChat,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF252EFF),
                            contentColor = Color.White
                        )
                    ) {
                        if (isLoadingChat) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Chat")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                ProfileSection(
                    title = "Bio",
                    content = profile.bio ?: "No bio provided",
                    titleColor = Color(0xFF252EFF),
                    textColor = Color(0xFF1A1A1A)
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE0E0F0))

                Row(modifier = Modifier.fillMaxWidth()) {
                    ProfileSection(
                        title = "Speaks",
                        content = profile.nativeLanguage ?: "N/A",
                        titleColor = Color(0xFF252EFF),
                        textColor = Color(0xFF5A5A89),
                        modifier = Modifier.weight(1f)
                    )
                    ProfileSection(
                        title = "Learns",
                        content = profile.targetLanguage ?: "N/A",
                        titleColor = Color(0xFF252EFF),
                        textColor = Color(0xFF5A5A89),
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE0E0F0))

                Text("Goals", style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF252EFF)))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    profile.goals.forEach { goal ->
                        AssistChip(
                            onClick = { /* Handle click */ },
                            label = { Text(goal.name) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(0xFFE5E6FF),
                                labelColor = Color(0xFF252EFF)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Location",
                        tint = Color(0xFF5A5A89)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(profile.country ?: "Unknown location", color = Color(0xFF1A1A1A))
                }
            }
        }
    }
}

@Composable
private fun ProfileSection(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    titleColor: Color = Color.Black,
    textColor: Color = Color.DarkGray
) {
    Column(modifier = modifier) {
        Text(title, style = MaterialTheme.typography.titleMedium.copy(color = titleColor))
        Text(content, modifier = Modifier.padding(vertical = 4.dp), color = textColor)
    }
}

