package care.intouch.uikit.ui.screens.my_plan.my_plan

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import care.intouch.uikit.R
import care.intouch.uikit.common.StringVO
import care.intouch.uikit.theme.InTouchTheme
import care.intouch.uikit.ui.RegularChips

@Composable
fun ChipsRow(
    modifier: Modifier = Modifier,
    firstChipsTitle: StringVO = StringVO.Resource(R.string.show_all),
    secondChipsTitle: StringVO = StringVO.Resource(R.string.to_do),
    thirdChipsTitle: StringVO = StringVO.Resource(R.string.in_progress),
    fourthChipsTitle: StringVO = StringVO.Resource(R.string.done),
    onFirstChipsClick: () -> Unit,
    onSecondChipsClick: () -> Unit,
    onThirdChipsClick: () -> Unit,
    onFourthChipsClick: () -> Unit,
) {
    var selectedChips by remember {
        mutableStateOf(firstChipsTitle)
    }

    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RegularChips(
            text = firstChipsTitle,
            selected = firstChipsTitle == selectedChips,
            selectedColor = InTouchTheme.colors.mainGreen,
            onClick = {
                if (firstChipsTitle != selectedChips) {
                    selectedChips = firstChipsTitle
                    onFirstChipsClick.invoke()
                }
            }
        )
        RegularChips(
            text = secondChipsTitle,
            selected = secondChipsTitle == selectedChips,
            selectedColor = InTouchTheme.colors.mainGreen,
            onClick = {
                if (secondChipsTitle != selectedChips) {
                    selectedChips = secondChipsTitle
                    onSecondChipsClick.invoke()
                }
            }
        )
        RegularChips(
            text = thirdChipsTitle,
            selected = thirdChipsTitle == selectedChips,
            selectedColor = InTouchTheme.colors.mainGreen,
            onClick = {
                if (thirdChipsTitle != selectedChips) {
                    selectedChips = thirdChipsTitle
                    onThirdChipsClick.invoke()
                }
            }
        )
        RegularChips(
            text = fourthChipsTitle,
            selected = fourthChipsTitle == selectedChips,
            selectedColor = InTouchTheme.colors.mainGreen,
            onClick = {
                if (fourthChipsTitle != selectedChips) {
                    selectedChips = fourthChipsTitle
                    onFourthChipsClick.invoke()
                }
            }
        )
    }
}

@Composable
@Preview()
fun ChipsRowPreview() {
    ChipsRow(
        onFirstChipsClick = {},
        onSecondChipsClick = {},
        onThirdChipsClick = {},
        onFourthChipsClick = {}
    )
}