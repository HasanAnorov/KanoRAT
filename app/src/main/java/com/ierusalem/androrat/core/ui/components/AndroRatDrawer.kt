package com.ierusalem.androrat.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.data.AppPreview
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.home.domain.model.Category
import com.ierusalem.androrat.features.home.domain.model.DrawerClicks
import com.ierusalem.androrat.features.home.domain.model.UserProfile
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

/**
 * AndroChatDrawerContent
 *
 * @author A.H.I "andro" on 07/03/2024
 */

@Composable
fun AndroChatDrawerContent(
    modifier: Modifier = Modifier,
    onDrawerItemClick: (DrawerClicks) -> Unit,
    userProfile: UserProfile,
    categories: List<Category>
) {
    // Use windowInsetsTopHeight() to add a spacer which pushes the drawer content
    // below the status bar (y-axis)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        DrawerHeader(userProfile)
        DividerItem()
        Spacer(modifier = Modifier.height(2.dp))
        categories.forEach {category ->
            ChatItem(
                text = stringResource(id = category.name),
                image = painterResource(id = category.icon),
                onChatClicked = { onDrawerItemClick(category.navigation) }
            )
        }
    }
}

@Composable
private fun DrawerHeader(userProfile: UserProfile) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(all = 12.dp)
    ) {
        GlideImage(
            imageModel = { userProfile.avatarUrl },
            failure = {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cib_arch_linux),
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            imageOptions = ImageOptions(
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Text(
                text = userProfile.username,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
private fun ChatItem(
    text: String,
    image: Painter,
    onChatClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .clickable(onClick = onChatClicked),
        verticalAlignment = CenterVertically
    ) {
        Icon(
            painter = image,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            contentDescription = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun DividerItem(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@Composable
@Preview
fun DrawerPreview() {
    AndroRATTheme {
        Surface {
            Column {
                AndroChatDrawerContent(
                    onDrawerItemClick = {},
                    userProfile = AppPreview.PreviewDrawer.userProfile,
                    categories = AppPreview.PreviewDrawer.categories
                )
            }
        }
    }
}

@Composable
@Preview
fun DrawerPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        Surface {
            Column {
                AndroChatDrawerContent(
                    onDrawerItemClick = {},
                    userProfile = AppPreview.PreviewDrawer.userProfile,
                    categories = AppPreview.PreviewDrawer.categories
                )
            }
        }
    }
}
