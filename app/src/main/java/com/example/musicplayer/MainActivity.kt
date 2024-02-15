package com.example.musicplayer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.presentation.ui.AudioViewModel
import com.example.musicplayer.presentation.ui.HomeScreen
import com.example.musicplayer.presentation.ui.navigationdrawer.NavigationItem
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {

                val permissionState = rememberPermissionState(
                    permission = android.Manifest.permission.READ_EXTERNAL_STORAGE)

                val lifecycleOwner = LocalLifecycleOwner.current

                DisposableEffect(key1 =lifecycleOwner ){
                    val  observer = LifecycleEventObserver{_ , event ->

                        if (event== Lifecycle.Event.ON_RESUME){
                            permissionState.launchPermissionRequest()
                        }
                    }

                    lifecycleOwner.lifecycle.addObserver(observer)


                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)

                    }
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (permissionState.hasPermission){

                        MainScreen()
                    }else
                    {
                        Box(contentAlignment = Alignment.Center){
                            Text(text = "Grant permission first to use application")
                        }
                    }
                }
            }
        }
    }
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(){

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
    ) {
        Navigation(navController = navController)
    }

}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState){

    TopAppBar(
        title = { Text(text = "",) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
//                Image(painter = painterResource(R.drawable.drawer_icon), contentDescription ="" )
                Icon(Icons.Default.Menu,contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = {

            }){
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "" ,
                    tint = Color.Black
                )
            }
        },
        backgroundColor = colorResource(id = R.color.background),
        contentColor = Color.Black,
        elevation = 0.dp
    )
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController){

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Profile,
        NavigationItem.Settings,
        NavigationItem.Share,
        NavigationItem.Contact
    )

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            )

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {

                navController.navigate(items.route){
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route){
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

                scope.launch {
                    scaffoldState.drawerState.close()
                }

            })
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Kiran Bahalaskar",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
fun DrawerItem(item: NavigationItem, selected: Boolean, onItemClick: (NavigationItem) -> Unit){
    val background = if(selected) R.color.grey else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 16.sp,
            color = Color.Black
        )

    }

}

@Composable
fun Navigation(navController: NavHostController ,
               audioViewModel :AudioViewModel = hiltViewModel()){

    NavHost(navController, startDestination = NavigationItem.Home.route){
        composable(NavigationItem.Home.route){
            HomeScreen(
                progress = audioViewModel.currentAudioProgress.value,
                onProgressChange ={
                    audioViewModel.seekTo(it)
                } ,
                isAudioPlaying = audioViewModel.isAudioPlaying,
                audioList = audioViewModel.audioList,
                currentPlayingAudio =audioViewModel.currentPlayingAudio.value ,
                onStart = {
                    audioViewModel.playAudio(it)
                } ,
                onItemClick = {
                    audioViewModel.playAudio(it)
                },
                onNext = {
                    audioViewModel.skipToNext()
                },
            )
        }

        composable(NavigationItem.Profile.route){
//            ProfileScreen()
        }

        composable(NavigationItem.Settings.route){
//            SettingsScreen()
        }

        composable(NavigationItem.Share.route){
//            ShareScreen()
        }

        composable(NavigationItem.Contact.route){
//            ContactScreen()
        }

    }

}



