package com.bogdan801.bulletpower.presentation.screens.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bogdan801.bulletpower.R
import com.bogdan801.bulletpower.presentation.components.BasicDialogBox
import com.bogdan801.bulletpower.presentation.components.CustomTopAppBar
import com.bogdan801.bulletpower.presentation.components.MenuItem
import com.bogdan801.bulletpower.presentation.navigation.Screen
import com.bogdan801.bulletpower.presentation.util.LockScreenOrientation


@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = hiltViewModel()
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = "Енергія кулі",
                backButton = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.BottomStart
            ){
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Меню",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            MenuItem(
                title = "Одиночний рейтинг",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    navController.navigate(Screen.Rating(isSingleShot = true).routeWithArgs)
                }
            )
            MenuItem(
                title = "Рейтинг серії пострілів",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_graph),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    navController.navigate(Screen.Rating(isSingleShot = false).routeWithArgs)
                }
            )
            MenuItem(
                title = "Список пристроїв",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gun),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    navController.navigate(Screen.Devices(isSelectorScreen = false).routeWithArgs)
                }
            )
            MenuItem(
                title = "Список куль",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bullet),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    navController.navigate(Screen.Bullets(isSelectorScreen = false).routeWithArgs)
                }
            )
            var showInfoDialog by rememberSaveable { mutableStateOf(false) }
            MenuItem(
                title = "Про додаток",
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    showInfoDialog = true
                }
            )
            BasicDialogBox(
                showDialog = showInfoDialog,
                onDismiss = {
                    showInfoDialog = false
                },
                title = "Про додаток"
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    val style = MaterialTheme.typography.bodyMedium
                    Text(
                        text = buildAnnotatedString {
                            append(
                                """
                                    Цей додаток створений для зручного підрахунку енергії кулі, та збереження цієї інформації у вигляді рейтингу, або графіку серії пострілів.
                                    
                                    Додаток орієнтований на пневматику, та пристрої під патрон Флобера. Але, також підійде для метальної та вогнепальної зброї малих та середніх калібрів.
                                    
                                    Для розрахунків використовується формула 
                                """.trimIndent()
                            )
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = style.fontFamily
                                )
                            ) {
                                append("Е=m×v²/2")
                            }
                            append(", де:\n\n")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = style.fontFamily
                                )
                            ) {
                                append("\tE\t")
                            }
                            append("- енергія в Джоулях\n")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = style.fontFamily
                                )
                            ) {
                                append("\tm\t")
                            }
                            append("- маса в кілограмах\n")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = style.fontFamily
                                )
                            ) {
                                append("\tv\t\t")
                            }
                            append("- швидкість в метрах за секунду\n\n")
                            append("Питання, зауваження та пропозиції, пишіть на пошту:")
                        },
                        style = style,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SelectionContainer {
                        Text(
                            modifier = Modifier.clickable {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("mailto:" + "polikarpovichviktor@gmail.com")
                                )
                                intent.putExtra(Intent.EXTRA_SUBJECT, "email_subject")
                                intent.putExtra(Intent.EXTRA_TEXT, "email_body")
                                startActivity(context, intent,  null)
                            },
                            text = "polikarpovichviktor@gmail.com",
                            color = MaterialTheme.colorScheme.primary,
                            style = style
                        )
                    }
                }
            }
            MenuItem(
                title = "Подякувати за додаток",
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    val url = "https://send.monobank.ua/jar/9rQWSwwbwr"
                    var intent = Intent(Intent.ACTION_VIEW)

                    try {
                        intent.setPackage("com.ftband.mono")
                        intent.data = Uri.parse(url)
                        startActivity(context, intent, null)
                    } catch (e: ActivityNotFoundException) {
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(context, intent, null)
                    }
                }
            )
            MenuItem(
                title = "Канал автора \"Polikarpovich\"",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_polikarpovich),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                showDivider = false,
                onClick = {
                    val youtubeURL = "https://www.youtube.com/@Polikarpovich"
                    var youtubeIntent = Intent(Intent.ACTION_VIEW)

                    try {
                        youtubeIntent.setPackage("com.google.android.youtube")
                        youtubeIntent.data = Uri.parse(youtubeURL)
                        startActivity(context, youtubeIntent, null)
                    } catch (e: ActivityNotFoundException) {
                        youtubeIntent = Intent(Intent.ACTION_VIEW)
                        youtubeIntent.data = Uri.parse(youtubeURL)
                        startActivity(context, youtubeIntent, null)
                    }
                }
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        val url = "https://play.google.com/store/apps/developer?id=bogdan801"
                        var intent = Intent(Intent.ACTION_VIEW)

                        try {
                            intent.setPackage("com.android.vending")
                            intent.data = Uri.parse(url)
                            startActivity(context, intent, null)
                        } catch (e: ActivityNotFoundException) {
                            intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(context, intent, null)
                        }
                    },
                text = "Розробив @bogdan.801 bogdan2002801@gmail.com",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "Версія 1.0",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}