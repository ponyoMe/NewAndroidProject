package com.example.skillcinema.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skillcinema.presentation.navigation.BottomNavItems
import com.example.testing.R


data class OnBoardingPageData(
    val image: Int,
    val description: String
)

val onBoardingPages = listOf(
    OnBoardingPageData(R.drawable.p1, "Узнавай о премьерах"),
    OnBoardingPageData(R.drawable.p, "Создавай коллекции"),
    OnBoardingPageData(R.drawable.p2, "Делись с друзьями")
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { onBoardingPages.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Skillcinema",
                color = Color(0xFF272727),
                fontSize = 20.sp
            )
            Button(
                onClick = {
                    navController.navigate(BottomNavItems.Home.route) {
                        popUpTo(BottomNavItems.Home.route) { inclusive = true }
                    }
                    Log.d("OnBoarding", "Skip button clicked")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Пропустить", color = Color(0xFFB5B5C9))
            }
        }

        Spacer(modifier = Modifier.height(190.dp))

        HorizontalPager(state = pagerState) { screenIndex ->
            val screenData = onBoardingPages[screenIndex]

            Column(modifier = Modifier) {
                Image(
                    painter = painterResource(screenData.image),
                    contentDescription = null,
                    modifier = Modifier.width(360.dp).height(270.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))

                val descNew = screenData.description.replaceFirst(" ", "\n")

                Text(
                    text = descNew,
                    fontSize = 40.sp,
                    color = Color(0xFF272727),
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                for (i in onBoardingPages.indices) {
                    val color = if (i == pagerState.currentPage) Color(0xFF121616) else Color(0xFFD9D9D9)
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(4.dp)
                            .background(color, CircleShape)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    val navController = rememberNavController()
    OnBoardingScreen(navController)
}
