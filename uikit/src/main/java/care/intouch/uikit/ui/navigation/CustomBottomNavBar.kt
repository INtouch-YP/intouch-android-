package care.intouch.uikit.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import care.intouch.uikit.R
import care.intouch.uikit.theme.InTouchTheme

@Composable
fun NavBottomBarPlusButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(70.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(InTouchTheme.colors.mainGreen)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick.invoke() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_plus_large),
            contentDescription = null,
            tint = InTouchTheme.colors.input
        )
    }
}

@Composable
@Preview(showBackground = true)
fun NavBottomBarPlusButtonPreview() {
    InTouchTheme {
        NavBottomBarPlusButton(
            onClick = {}
        )
    }
}

@Composable
fun NavBottomComplexElement(
    screenRoute: String,
    currentRoute: String? = null,
    onClick: () -> Unit,
    text: String,
    painter: Painter,
    onFocusTint: Color,
    outFocusTint: Color,
    modifier: Modifier = Modifier
) {

    val selected = screenRoute == currentRoute

    Column(
        modifier = modifier
            .width(75.dp)
            .height(56.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    onClick.invoke()
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = if (selected) onFocusTint else outFocusTint,
            modifier = modifier.padding(bottom = 5.dp)
        )
        Text(
            text = text,
            color = if (selected) onFocusTint else outFocusTint,
            style = InTouchTheme.typography.tabBar,
            modifier = Modifier.padding(bottom = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview(showBackground = true)
fun NavBottomComplexElementPreview() {
    InTouchTheme {
        NavBottomComplexElement(
            screenRoute = "",
            onClick = { /*TODO*/ },
            text = "Home",
            painter = painterResource(id = R.drawable.icon_home),
            onFocusTint = InTouchTheme.colors.mainGreen,
            outFocusTint = InTouchTheme.colors.mainGreen40,
        )
    }
}

@Composable
fun CustomBottomNavBar(
    navController: NavHostController? = null,
    screenRoute1: String = "",
    screenRoute2: String = "",
    screenRoute3: String = "",
    screenRoute4: String = "",
    screenRoute5: String = "",
    onFocusTint: Color = InTouchTheme.colors.mainGreen,
    outFocusTint: Color = InTouchTheme.colors.mainGreen40,
    firstItemText: String = "Home",
    secondItemText: String = "My plan",
    thirdItemText: String = "My diary",
    fourthItemText: String = "Profile",
    firstItemImage: Painter = painterResource(id = R.drawable.icon_home),
    secondItemImage: Painter = painterResource(id = R.drawable.icon_plan_notebook),
    thirdItemImage: Painter = painterResource(id = R.drawable.icon_diary),
    fourthItemImage: Painter = painterResource(id = R.drawable.icon_profile),
    firstItemClick: () -> Unit = {},
    secondItemClick: () -> Unit = {},
    thirdItemClick: () -> Unit = {},
    fourthItemClick: () -> Unit = {},
    onPlusItemClick: () -> Unit = {}
) {

    val currentRoute = navController?.let { currentRoute(navController = it) }

    ConstraintLayout(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        val (homeTag, progressTag, plusTag, myPlanTag, additionalTag, box) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .constrainAs(box) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        NavBottomComplexElement(
            screenRoute = screenRoute1,
            currentRoute = currentRoute,
            onClick = {
                firstItemClick.invoke()
                navController?.navigate(screenRoute1)  {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            text = firstItemText,
            painter = firstItemImage,
            onFocusTint = onFocusTint,
            outFocusTint = outFocusTint,
            modifier = Modifier
                .constrainAs(homeTag) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        NavBottomComplexElement(
            screenRoute = screenRoute5,
            currentRoute = currentRoute,
            onClick = {
                fourthItemClick.invoke()
                navController?.navigate(screenRoute5)  {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            text = fourthItemText,
            painter = fourthItemImage,
            onFocusTint = onFocusTint,
            outFocusTint = outFocusTint,
            modifier = Modifier
                .constrainAs(additionalTag) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )

        NavBottomBarPlusButton(
            modifier = Modifier.constrainAs(plusTag) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            }
        ) {
            onPlusItemClick.invoke()
        }

        NavBottomComplexElement(
            screenRoute = screenRoute2,
            currentRoute = currentRoute,
            onClick = {
                secondItemClick.invoke()
                navController?.navigate(screenRoute2)  {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            text = secondItemText,
            painter = secondItemImage,
            onFocusTint = onFocusTint,
            outFocusTint = outFocusTint,
            modifier = Modifier
                .constrainAs(progressTag) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(homeTag.end)
                    end.linkTo(plusTag.start)
                }
        )

        NavBottomComplexElement(
            screenRoute = screenRoute4,
            currentRoute = currentRoute,
            onClick = {
                thirdItemClick.invoke()
                navController?.navigate(screenRoute4)  {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            text = thirdItemText,
            painter = thirdItemImage,
            onFocusTint = onFocusTint,
            outFocusTint = outFocusTint,
            modifier = Modifier
                .constrainAs(myPlanTag) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(plusTag.end)
                    end.linkTo(additionalTag.start)
                }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CustomBottomNavBarPreview() {
    InTouchTheme {
        CustomBottomNavBar()
    }
}