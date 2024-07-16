package care.intouch.uikit.ui.questions

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import care.intouch.uikit.common.StringVO
import care.intouch.uikit.theme.InTouchTheme
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults.BLANC_STRING
import care.intouch.uikit.ui.textFields.MultilineTextFieldDefaults.TextPadding

@Composable
fun TextFieldQuestion(
    modifier: Modifier = Modifier,
    textPadding: Dp = TextPadding,
    isError: Boolean,
    enabled: Boolean,
    backgroundColor: Color = InTouchTheme.colors.input85,
    listOfBlockDescription: MutableList<Pair<String, String>> = mutableListOf()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        modifier = modifier.width(MultilineTextFieldDefaults.MinWidth)
    ) {
        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = when {
                        isError && enabled -> InTouchTheme.colors.errorRed
                        isFocused && enabled -> InTouchTheme.colors.accentGreen
                        else -> backgroundColor
                    },
                    shape = RoundedCornerShape(12.dp),
                ),
        ) {
            Column (
                modifier = Modifier.padding(vertical = 14.dp)
            ) {
                        Column(modifier = Modifier
                            .padding(bottom = textPadding)
                            .padding(horizontal = 16.dp)) {

                            listOfBlockDescription.forEach { itemOfBlockDescription ->
                                when (itemOfBlockDescription.first) {
                                    "header-one" -> {
                                        Text(
                                            text = itemOfBlockDescription.second,
                                            style = InTouchTheme.typography.bodyBold,
                                            color = InTouchTheme.colors.textBlue,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    "header-two" -> {
                                        Text(
                                            text = itemOfBlockDescription.second,
                                            modifier =  Modifier
                                                .padding(top = 8.dp),
                                            style = InTouchTheme.typography.bodySemibold,
                                            color = InTouchTheme.colors.textGreen,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    "unstyled" -> {
                                        Text(
                                            text = itemOfBlockDescription.second,
                                            modifier = Modifier.padding(top = 2.dp),
                                            style = InTouchTheme.typography.caption1Regular,
                                            color = InTouchTheme.colors.textGreen,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                            }
                        }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

@Preview
@Composable
fun TextFieldQuestionPreview() {
    val items = mutableListOf(
        Pair("header-one", "Заголовок первого уровня"),
        Pair("header-two", "Заголовок второго уровня"),
        Pair("unstyled", "Просто текст, который лежит под всеми заголовками"),
        Pair("header-one", "Заголовок первого уровня второй раз"),
        Pair("header-two", "Заголовок второго уровня второй раз"),
        Pair("unstyled", "Просто текст, который лежит под всеми заголовками второй раз"))
    InTouchTheme {
        TextFieldQuestion(
            isError = false,
            enabled = true,
            modifier = Modifier.padding(45.dp),
            listOfBlockDescription = items
        )
    }
}