package com.example.skillcinema.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.skillcinema.presentation.navigation.NavLogic

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        setContent {
            NavLogic()
        }
    }
}

//data class ScreenInfo(
//    val image : Int,
//    val description : String
//)
//
//val screen= listOf(
//    ScreenInfo(R.drawable.p1, "Узнавай о премьерах"),
//    ScreenInfo(R.drawable.p, "Создавай коллекции"),
//    ScreenInfo(R.drawable.p2, "Делись с друзьями")
//)
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun OnBoardingScreen (navController : NavHostController) {
//    val pagerState = rememberPagerState(initialPage = 0, pageCount = { screen.size })
//
//    Column (
//        modifier =Modifier
//            .fillMaxSize()
//            .background(Color.White)
//
//    ) {
//        Spacer(modifier = Modifier.height(55.dp))
//        Row (
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Skillcinema",
//                color = Color(0xFF272727),
//                fontSize = 20.sp
//            )
//            Button (
//                onClick = {
//                    navController.navigate(BottomNavItems.Home.route) {
//                        popUpTo(BottomNavItems.Home.route) { inclusive = true }
//                    }
//                    Log.d("OnBoarding", "Skip button clicked")
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.White
//                )
//            ) {
//                Text(text = "Пропустить",
//                    color = Color(0xFFB5B5C9)
//                )
//            }
//
//        }
//        Spacer(modifier = Modifier.height(190.dp))
//
//        HorizontalPager(
//            state = pagerState
//        ) {screenIndex->
//            val screenData = screen[screenIndex]
//
//            Column (
//                modifier = Modifier
//            ) {
//                Image(
//                    painter = painterResource(screenData.image),
//                    contentDescription = null,
//                    modifier = Modifier.width(360.dp).height(270.dp)
//                )
//                Spacer(modifier = Modifier.height(40.dp))
//
//                val descNew = screenData.description.replaceFirst(" ", "\n")
//
//                Text( text = descNew,
//                    fontSize = 40.sp,
//                    color = Color(0xFF272727),
//                    fontWeight = FontWeight.Light,
//                    modifier = Modifier.padding(start = 20.dp),
//
//                    )
//
//
//            }
//        }
//        Spacer(modifier = Modifier.height(40.dp))
//
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(18.dp),
//        ){
//            Row (
//                horizontalArrangement = Arrangement.Center
//            ) {
//                for (i in screen.indices){
//                    val color = if (i == pagerState.currentPage) Color(0xFF121616) else Color(0xFFD9D9D9)
//                    Box(modifier = Modifier.size(20.dp).padding(4.dp)
//                        .background(color, CircleShape))
//                }
//            }
//        }
//    }
//
//}
//
//@Preview
//@Composable
//fun OnBoardingScreenPreview () {
//    val navController = rememberNavController()
//    OnBoardingScreen(navController)
//}