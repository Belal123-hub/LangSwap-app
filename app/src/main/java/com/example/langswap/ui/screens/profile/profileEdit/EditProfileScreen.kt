@file:Suppress("UNREACHABLE_CODE")

package com.example.langswap.ui.screens.profile.profileEdit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.profile.model.UserProfile
import com.example.langswap.ui.common.AppButton
import com.example.langswap.ui.common.AppTextField
import com.example.langswap.ui.navigation.NavigationItem
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.domain.profile.model.Goal

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Collect errors independently
    LaunchedEffect(Unit) {
        viewModel.error.collect { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    // Collect success independently and navigate on update
    LaunchedEffect(viewModel.success) {
        viewModel.success.collect {
            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
            navController.navigate(NavigationItem.Match.route) {
                // Pop ProfileEdit from backstack to prevent going back to it
                popUpTo(NavigationItem.ProfileEdit.route) { inclusive = true }
            }
        }
    }

    profile?.let { profile ->
        EditProfileContent(
            profile = profile,
            isLoading = isLoading,
            onSubmit = viewModel::updateProfile
        )
    } ?: run {
        if (isLoading) {
            CircularProgressIndicator(Modifier.padding(32.dp))
        }
    }
}


@Composable
fun EditProfileContent(
    profile: UserProfile,
    isLoading: Boolean,
    onSubmit: (UserProfile) -> Unit
) {
    var fullName by remember { mutableStateOf(profile.fullName) }
    var birthDate by remember { mutableStateOf(profile.birthDate) }
    var gender by remember { mutableStateOf(profile.gender) }
    var country by remember { mutableStateOf(profile.country) }
    var nativeLanguage by remember { mutableStateOf(profile.nativeLanguage) }
    var targetLanguage by remember { mutableStateOf(profile.targetLanguage) }
    var bio by remember { mutableStateOf(profile.bio) }

    // Copy the goals list into a mutableStateList so we can update isSelected
    val goals = remember {
        mutableStateListOf<Goal>().apply {
            profile.goals?.forEach { add(it) }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Profile photo placeholder
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👤",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(32.dp)
                )
            }
        }

        item {
            Text("Edit Profile", style = MaterialTheme.typography.headlineMedium)
        }

        item { AppTextField(label = "Full Name", value = fullName, onValueChange = { fullName = it }) }
        item { AppTextField(label = "Birth Date", value = birthDate ?: "", onValueChange = { birthDate = it }) }
        item { AppTextField(label = "Gender", value = gender, onValueChange = { gender = it }) }
        item { AppTextField(label = "Country", value = country?: "", onValueChange = { country = it }) }
        item { AppTextField(label = "Native Language", value = nativeLanguage, onValueChange = { nativeLanguage = it }) }
        item { AppTextField(label = "Target Language", value = targetLanguage, onValueChange = { targetLanguage = it }) }
        item { AppTextField(label = "Bio", value = bio?: "", onValueChange = { bio = it }) }

        if (goals.isNotEmpty()) {
            item {
                Text("Your Goals", style = MaterialTheme.typography.titleMedium)
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(goals.size) { index ->
                        val goal = goals[index]
                        val backgroundColor =
                            if (goal.isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            else Color.LightGray.copy(alpha = 0.5f)

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(backgroundColor)
                                .clickable {
                                    goals[index] = goal.copy(isSelected = !goal.isSelected)
                                }
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = goal.name,
                                color = if (goal.isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }

        item {
            AppButton(
                text = if (isLoading) "Saving..." else "Save Changes",
                onClick = {
                    onSubmit(
                        profile.copy(
                            fullName = fullName,
                            birthDate = birthDate?.takeIf { it.isNotBlank() },
                            gender = gender,
                            country = if (country.isNullOrBlank()) "" else country,
                            nativeLanguage = nativeLanguage,
                            targetLanguage = targetLanguage,
                            bio = bio?.takeIf { it.isNotBlank() },
                            goals = goals.toList()
                        )
                    )
                },
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxSize()
            )
        }
    }
}




