package care.intouch.app.feature.questions.presentations.ui.questions


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import care.intouch.app.R
import care.intouch.app.feature.questions.presentations.ui.models.QuestionsState
import care.intouch.uikit.common.StringVO
import care.intouch.uikit.theme.InTouchTheme
import care.intouch.uikit.ui.buttons.IntouchButton
import care.intouch.uikit.ui.navigation.TopBarArcButton
import care.intouch.uikit.ui.questions.PopupQuestions
import care.intouch.uikit.ui.questions.TextFieldQuestion
import care.intouch.uikit.ui.questions.TextFieldWithCheckbox
import care.intouch.uikit.ui.questions.TextFieldWithCheckmars
import care.intouch.uikit.ui.questions.TextFieldWithSliderAndDigits
import care.intouch.uikit.ui.textFields.MultilineTextField
import care.intouch.uikit.ui.toggle.Toggle
import coil.compose.AsyncImage

@Composable
fun QuestionsScreen(
    onCloseClick: () -> Unit,
    onCompleteTaskClick: () -> Unit,
    viewModel: QuestionsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    QuestionsScreen(
        onCloseClick = onCloseClick,
        onCompleteTaskClick = onCompleteTaskClick,
        onEvent = {
            viewModel.onEvent()
        },
        state = state
    )

}

@Composable
private fun QuestionsScreen(
    onCloseClick: () -> Unit,
    onCompleteTaskClick: () -> Unit,
    onEvent: () -> Unit,
    state: QuestionsState
) {

    var answerText by remember { mutableStateOf("") }
    val systemKeyboardController = LocalSoftwareKeyboardController.current
    var isCheckedToggle by remember {
        mutableStateOf(false)
    }
    var isShowClosingDialog by remember {
        mutableStateOf(false)
    }
    var isShowCompleteTaskDialog by remember {
        mutableStateOf(false)
    }
    var currentSliderPosition by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier
            .background(InTouchTheme.colors.mainBlue)
            .verticalScroll(rememberScrollState())
            .clickable {
                systemKeyboardController?.hide()
                isShowClosingDialog = false
                isShowCompleteTaskDialog = false
            }
            .alpha(if (isShowClosingDialog) 0.2f else 1f)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopBarArcButton(
                onClick = {
                    isShowClosingDialog = true
                },
                enabled = true,
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(48.dp))

            state.blocks.forEachIndexed { index, block ->
                when(block.type) {
                   "open" -> {
                       MultilineTextField(
                           subtitleText = StringVO.Plain(block.question),
                           captionText = StringVO.Resource(R.string.inscribe_motivates_question),
                           value = answerText,
                           modifier = Modifier.fillMaxWidth(),
                           hint = StringVO.Resource(R.string.write_your_answer_here),
                           onValueChange = {
                               answerText = it
                           },
                           isError = if (isShowCompleteTaskDialog && answerText.isBlank()) true else false,
                           enabled = true,
                           keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                           keyboardActions = KeyboardActions(onDone = {
                               systemKeyboardController?.hide()
                           }))

                       Spacer(modifier = Modifier.height( if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                   }

                    "single" -> {
                        val replyTextList: MutableList<String> = mutableListOf()
                        block.choiceReplies?.forEach {
                            replyTextList.add(it.reply)
                        }
                        TextFieldWithCheckbox(
                            modifier = Modifier.fillMaxWidth(),
                            subtitleText = StringVO.Plain(block.question),
                            listOfChoiceReplise = replyTextList
                        )
                        Spacer(modifier = Modifier.height( if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    "multiple" -> {
                        val replyTextList: MutableList<String> = mutableListOf()
                        block.choiceReplies?.forEach {
                            replyTextList.add(it.reply)
                        }
                        TextFieldWithCheckmars(
                            modifier = Modifier.fillMaxWidth(),
                            subtitleText = StringVO.Plain(block.question),
                            captionText = StringVO.Resource(R.string.inscribe_professional_development_question),
                            listOfChoiceReplise = replyTextList
                        )
                        Spacer(modifier = Modifier.height( if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    "range" -> {
                        TextFieldWithSliderAndDigits(
                            subtitleText = StringVO.Plain(block.question),
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { value ->
                                currentSliderPosition = value
                            },
                            isError = if (currentSliderPosition == 0 && isShowCompleteTaskDialog) true else false,
                            leftEvaluateText = StringVO.Plain(block.leftPole),
                            rightEvaluateText = StringVO.Plain(block.rightPole)
                        )
                        Spacer(modifier = Modifier.height( if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    "text" -> {
                        TextFieldQuestion(
                            modifier = Modifier.fillMaxWidth(),
                            isError = false,
                            enabled = true,
                            topTitleText = StringVO.Plain(block.question),
                            topSubtitleText = StringVO.Resource(R.string.skills_survey),
                            topCaptionText = StringVO.Resource(R.string.skills_survey_discription),
                            bottomSubtitleText = StringVO.Resource(R.string.professional_development_snapshot),
                            bottomCaptionText = StringVO.Resource(R.string.professional_development_snapshot_description)
                        )
                        Spacer(modifier = Modifier.height( if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    "image" -> {
                        Box(modifier = Modifier
                            .background(
                                color = InTouchTheme.colors.input85,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth()
                        ) {
                            Column (modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 14.dp, bottom = 20.dp))
                            {
                                Text(
                                    text = StringVO.Plain(block.question).value(),
                                    style = InTouchTheme.typography.caption1Bold,
                                    color = InTouchTheme.colors.textGreen
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                AsyncImage(
                                    model = block.image,
                                    contentDescription = "task image on questions screen",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height( if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    else -> {

                    }
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = StringVO.Resource(R.string.share_with_therapist).value(),
                    style = InTouchTheme.typography.bodySemibold,
                    color = InTouchTheme.colors.textGreen
                )
                Spacer(modifier = Modifier.weight(1f))
                Toggle(
                    isChecked = isCheckedToggle,
                    onChange = {
                        isCheckedToggle = !isCheckedToggle
                    }
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            IntouchButton(
                onClick = {
                    isShowCompleteTaskDialog = true
                },
                isEnabled = if (isShowCompleteTaskDialog) false else true,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = StringVO.Resource(R.string.complete_task),
                contentPadding = PaddingValues(horizontal = 51.dp, vertical = 13.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
        }
    if (isShowClosingDialog) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .wrapContentHeight(Alignment.Top)
                .alpha(1F)
                .padding(top = 100.dp)
                .clip(RoundedCornerShape(20.dp)),
            content = {
                PopupQuestions(
                    inTouchButtonClick = {

                    },
                    secondaryButtonClick = {
                        isShowClosingDialog = !isShowClosingDialog
                    },
                    firstLineText = StringVO.Resource(R.string.questions_popap_closing_task_first_line),
                    secondLineText = StringVO.Resource(R.string.questions_popup_closing_task_second_line),
                    intouchButtonText = StringVO.Resource(R.string.save_in_progress_button),
                    secondaryButtonText = StringVO.Resource(R.string.discard_close_button))
            }
        )
    }
    if (isShowCompleteTaskDialog) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .wrapContentHeight(Alignment.CenterVertically)
                .alpha(1F)
                .padding(top = 100.dp)
                .clip(RoundedCornerShape(20.dp)),
            content = {
                Column {
                    PopupQuestions(
                        inTouchButtonClick = {
                            isShowCompleteTaskDialog = !isShowCompleteTaskDialog
                        },
                        secondaryButtonClick = {

                        },
                        firstLineText = StringVO.Resource(R.string.questions_popap_not_all_filled),
                        intouchButtonText = StringVO.Resource(R.string.back_button),
                        secondaryButtonText = StringVO.Resource(R.string.complete_as_is_button))
                }
                }
        )
    }
}

@Preview(showBackground = true, heightDp = 1960)
@Composable
fun QuestionsScreenPreview() {
    val state = QuestionsState(0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        emptyList(),
        0,
        "",
        0,
        false,
        null,
        "",
        0
    )
    InTouchTheme {
        QuestionsScreen(
            onCloseClick = {},
            onCompleteTaskClick = {},
            onEvent = {},
            state = state
        )
    }
}