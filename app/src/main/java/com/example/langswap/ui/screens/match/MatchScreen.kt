import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.match.model.Match
import com.example.langswap.R
import com.example.langswap.ui.navigation.NavigationItem
import com.example.langswap.ui.screens.match.MatchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MatchScreen(
    viewModel: MatchViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                tonalElevation = 4.dp,
                containerColor = Color(0xFF252EFF),
                contentColor = Color.White,
                modifier = Modifier
                    .height(80.dp) // taller bar
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Navigate to home */ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_home),
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp) // larger icon
                        )
                    }

                    FloatingActionButton(
                        onClick = { navController.navigate(NavigationItem.ChatList.route) },
                        containerColor = Color(0xFF4B4EFF),
                        contentColor = Color.White,
                        modifier = Modifier.size(64.dp) // larger FAB
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chat),
                            contentDescription = "Chats",
                            modifier = Modifier.size(28.dp) // larger icon inside FAB
                        )
                    }

                    IconButton(onClick = { /* Navigate to profile */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "Profile",
                            tint = Color.Unspecified, // ← disables tint
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }


    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(
                text = "Matches",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF252EFF)
                ),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.error}")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.matches) { match ->
                        MatchItem(match, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MatchItem(match: Match, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F8FF)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(NavigationItem.PublicProfile.createRoute(match.id))
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(match.profilePhotoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.image_error),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
            )

            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    match.fullName,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF1A1A1A))
                )
                Text("Speaks: ${match.nativeLanguage}", color = Color(0xFF5A5A89))
                Text("Wants: ${match.targetLanguage}", color = Color(0xFF5A5A89))
                match.bio?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(it, style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF1A1A1A)))
                }
            }
        }
    }
}

