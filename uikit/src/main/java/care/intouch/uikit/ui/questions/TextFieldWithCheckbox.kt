package care.intouch.uikit.ui.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import care.intouch.uikit.common.StringVO
import care.intouch.uikit.theme.InTouchTheme
import care.intouch.uikit.ui.Checkbox
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults.BLANC_STRING
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults.TextPadding

@Composable
fun TextFieldWithCheckbox(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    titleText: StringVO = StringVO.Plain(BLANC_STRING),
    subtitleText: StringVO = StringVO.Plain(BLANC_STRING),
    captionText: StringVO = StringVO.Plain(BLANC_STRING),
    textPadding: Dp = TextPadding,
    backgroundColor: Color = InTouchTheme.colors.input85,
    listOfChoiceReplise: MutableList<Pair<String, Int>> = mutableListOf()
) {
    val (selected, setSelected) = remember { mutableStateOf(listOfChoiceReplise[0]) }

    Column(
        modifier = modifier.width(MultilineTextFieldDefaults.MinWidth)
    ) {
        if (titleText.value().isNotBlank()
            || subtitleText.value().isNotBlank()
            || captionText.value().isNotBlank()
        ) {
            Column(modifier = Modifier.padding(bottom = textPadding)) {
                if (titleText.value().isNotBlank()) {
                    Text(
                        text = titleText.value(),
                        style = InTouchTheme.typography.titleSmall,
                        color = InTouchTheme.colors.textBlue,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (subtitleText.value().isNotBlank()) {
                    Text(
                        text = subtitleText.value(),
                        modifier = if (titleText.value().isNotBlank()) Modifier
                            .padding(top = 8.dp) else Modifier,
                        style = InTouchTheme.typography.bodySemibold,
                        color = InTouchTheme.colors.textGreen,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (captionText.value().isNotBlank()) {
                    Text(
                        text = captionText.value(),
                        modifier = if (subtitleText.value().isNotBlank() || titleText.value()
                                .isNotBlank()
                        )
                            Modifier.padding(top = 2.dp) else Modifier,
                        style = InTouchTheme.typography.caption1Regular,
                        color = InTouchTheme.colors.textGreen,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp),
                )
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                listOfChoiceReplise.forEach { item ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Checkbox(
                        isChecked = selected == item,
                        text = item.first,
                        modifier = Modifier.padding(start = 24.dp, end = 22.dp),
                        onClick = {
                            setSelected(item)
                            onClick(item.second)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        }
    }

@Preview(showBackground = true)
@Composable
fun TextFieldWithCheckboxPreview() {
    val items = mutableListOf(
        Pair("Первый", 1),
        Pair("Второй", 2),
        Pair("Третий", 3))
    InTouchTheme {
        TextFieldWithCheckbox(
            titleText = StringVO.Plain("Title small "),
            subtitleText = StringVO.Plain("Body semi bold "),
            captionText = StringVO.Plain("Caption "),
            modifier = Modifier.padding(45.dp),
            listOfChoiceReplise = items,
            onClick = {}
        )
    }
}