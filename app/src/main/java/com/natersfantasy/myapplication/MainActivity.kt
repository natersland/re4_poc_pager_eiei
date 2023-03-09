package com.natersfantasy.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.natersfantasy.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Greeting() {
    val pagerState = rememberPagerState()

    Column() {
        TabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            // Add tabs for all of our pages
            mockVehicleList.forEachIndexed { index, characterType ->
                Tab(
                    text = { Text(text = characterType.name) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            count = mockVehicleList.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page ->
            VehicleTypeCard(
                characterTypeIndex = page
            )
        }
    }
}

@Composable
fun VehicleTypeCard(
    characterTypeIndex: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = 8.dp,
        contentColor = Color.White,
        border = BorderStroke(width = 1.dp, color = Color.Blue),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(
                    id = mockVehicleList[characterTypeIndex].characterImgResId
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 32.dp)
            )
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 1.dp,
                        spotColor = Color.LightGray
                    )
                    .fillMaxWidth()
                    .height(2.dp)
            )
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                when (characterTypeIndex) {
//                    CharacterTypeIndex.LEON, CharacterTypeIndex.ADA -> Text(text = "Hero Team")
//                    else -> Text(text = "Monster Team")
//                }
//            }
        }
    }
}


val mockVehicleList = listOf(
    CharacterTypeViewState.CharacterType(
        name = "Leon",
        characterImgResId = R.drawable.leon,
        maxItemSlot = 6,
        usedItemSlot = 3,
        isMonster = true
    ),
    CharacterTypeViewState.CharacterType(
        name = "Ada",
        characterImgResId = R.drawable.ada,
        maxItemSlot = 6,
        usedItemSlot = 3,
        isMonster = true
    ),
    CharacterTypeViewState.CharacterType(
        name = "Dr.Salvador",
        characterImgResId = R.drawable.dr_salvador,
        maxItemSlot = 4,
        usedItemSlot = 1,
        isMonster = true
    ),
    CharacterTypeViewState.CharacterType(
        name = "Wesker",
        characterImgResId = R.drawable.wesker,
        maxItemSlot = 4,
        usedItemSlot = 1,
        isMonster = true
    ),
)

data class CharacterTypeViewState(
    val fromDestination: String = "",
    val toDestination: String = "",
    val currentLocale: Locale,
    val vehicleTypes: List<CharacterType> = emptyList(),
    val selectedVehicleTypeIndex: Int = 0,
    val isWholeCar: Boolean = false,
    val isChoosePaymentEnabled: Boolean = false,
    val errorMessage: String? = null
) {
    data class CharacterType(
        val name: String,
        val characterImgResId: Int,
        val maxItemSlot: Int,
        val usedItemSlot: Int? = null,
        val isMonster: Boolean
    )
}

enum class CharacterTypeIndex(selectedVehicleTypeIndex: Int) {
    LEON(0),
    ADA(1),
    DR_SALVADOR(2),
    WESKER(3)
}
