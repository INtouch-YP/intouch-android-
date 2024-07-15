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
import androidx.compose.ui.unit.dp
import care.intouch.uikit.common.StringVO
import care.intouch.uikit.theme.InTouchTheme
import care.intouch.uikit.ui.checkmark.CheckmarkWithText
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults.BLANC_STRING

@Composable
fun TextFieldWithCheckmars(
    modifier: Modifier = Modifier,
    titleText: StringVO = StringVO.Plain(BLANC_STRING),
    subtitleText: StringVO = StringVO.Plain(BLANC_STRING),
    captionText: StringVO = StringVO.Plain(BLANC_STRING),
    backgroundColor: Color = InTouchTheme.colors.input85,
    listOfChoiceReplise: MutableList<String> = mutableListOf()
) {
    Column(
        modifier = modifier.width(MultilineTextFieldDefaults.MinWidth)
    ) {
        val selectedOptions = remember {
            mutableStateOf(setOf<String>())
        }

        if (titleText.value().isNotBlank()
            || subtitleText.value().isNotBlank()
            || captionText.value().isNotBlank()
        ) {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
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
                modifier = Modifier.fillMaxWidth()
                    .selectableGroup()
            ) {
                listOfChoiceReplise.forEach { item ->
                    Spacer(modifier = Modifier.height(10.dp))
                    CheckmarkWithText(
                        isChecked = selectedOptions.value.contains(item),
                        text = item,
                        modifier = Modifier.padding(start = 24.dp, end = 22.dp),
                        onChangeState = { selected ->
                            val currentSelected = selectedOptions.value.toMutableSet()
                            if (selected) {
                                currentSelected.remove(item)
                            }
                            else {
                                currentSelected.add(item)
                            }
                            selectedOptions.value = currentSelected
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

        }
    }
}

@Preview
@Composable
fun TextFieldWithCheckmarksPreview() {
    val items = mutableListOf("Первый", "Второй")
    InTouchTheme {
        TextFieldWithCheckmars(
            titleText = StringVO.Plain("Title small "),
            subtitleText = StringVO.Plain("Body semi bold "),
            captionText = StringVO.Plain("Caption "),
            modifier = Modifier.padding(45.dp),
            listOfChoiceReplise = items
        )
    }
}