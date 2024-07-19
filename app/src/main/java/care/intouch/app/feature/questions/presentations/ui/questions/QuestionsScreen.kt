package care.intouch.app.feature.questions.presentations.ui.questions

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import care.intouch.app.R
import care.intouch.app.feature.questions.domain.models.AssignmentsChoiceReplies
import care.intouch.app.feature.questions.domain.models.BlockDescription
import care.intouch.app.feature.questions.domain.models.TypeOfBlocks
import care.intouch.app.feature.questions.domain.models.TypeOfTitle
import care.intouch.app.feature.questions.presentations.ui.models.QuestionEvent
import care.intouch.app.feature.questions.presentations.ui.models.QuestionsBlock
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
            viewModel.onEvent(it)
        },
        state = state
    )
}

@Composable
private fun QuestionsScreen(
    onCloseClick: () -> Unit,
    onCompleteTaskClick: () -> Unit,
    onEvent: (QuestionEvent) -> Unit,
    state: QuestionsState
) {

    val systemKeyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .background(InTouchTheme.colors.mainBlue)
            .verticalScroll(rememberScrollState())
            .clickable {
                systemKeyboardController?.hide()
                onEvent(QuestionEvent.OnShowClosingDialog(false))
                onEvent(QuestionEvent.OnShowCompleteTaskDialog(false))
            }
            .alpha(if (state.isShowClosingDialog || state.isShowCompleteTaskDialog) 0.2f else 1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopBarArcButton(
                onClick = {
                    onEvent(QuestionEvent.OnShowClosingDialog(true))
                },
                enabled = true,
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(48.dp))

            state.blocks.forEachIndexed { index, block ->
                when (block.type) {
                    TypeOfBlocks.OPEN -> {
                        MultilineTextField(
                            subtitleText = StringVO.Plain(block.question),
                            captionText = StringVO.Resource(R.string.inscribe_motivates_question),
                            value = block.reply,
                            modifier = Modifier.fillMaxWidth(),
                            hint = StringVO.Resource(R.string.write_your_answer_here),
                            onValueChange = {
                                onEvent(
                                    QuestionEvent.OnBlockChange(
                                        id = block.id,
                                        reply = it,
                                        type = TypeOfBlocks.OPEN,
                                    )
                                )
                            },
                            isError = block.answerNotSpecified,
                            enabled = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                systemKeyboardController?.hide()
                            })
                        )

                        Spacer(modifier = Modifier.height(if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    TypeOfBlocks.SINGLE -> {
                        val replyTextList: MutableList<Pair<String, Int>> = mutableListOf()
                        block.choiceReplies?.forEach {
                            replyTextList.add(Pair(it.reply, it.id))
                        }
                        TextFieldWithCheckbox(
                            modifier = Modifier.fillMaxWidth(),
                            subtitleText = StringVO.Plain(block.question),
                            listOfChoiceReplise = replyTextList,
                            onClick = {
                                val choiceRepliesList =
                                    updateSingleChoiceReplies(block.choiceReplies!!, it)
                                onEvent(
                                    QuestionEvent.OnBlockChange(
                                        id = block.id,
                                        choiceReplies = choiceRepliesList,
                                        type = TypeOfBlocks.SINGLE,
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    TypeOfBlocks.MULTIPLE -> {
                        val replyTextList: MutableList<Pair<String, Int>> = mutableListOf()
                        block.choiceReplies?.forEach {
                            replyTextList.add(Pair(it.reply, it.id))
                        }
                        TextFieldWithCheckmars(
                            modifier = Modifier.fillMaxWidth(),
                            subtitleText = StringVO.Plain(block.question),
                            captionText = StringVO.Resource(R.string.inscribe_professional_development_question),
                            listOfChoiceReplise = replyTextList,
                            onClick = {
                                val choiceRepliesList =
                                    updateMultipleChoiceReplies(block.choiceReplies!!, it)
                                onEvent(
                                    QuestionEvent.OnBlockChange(
                                        id = block.id,
                                        choiceReplies = choiceRepliesList,
                                        type = TypeOfBlocks.SINGLE,
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    TypeOfBlocks.RANGE -> {
                        TextFieldWithSliderAndDigits(
                            subtitleText = StringVO.Plain(block.question),
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                onEvent(
                                    QuestionEvent.OnBlockChange(
                                        id = block.id,
                                        selectedValue = it,
                                        type = TypeOfBlocks.RANGE,
                                    )
                                )
                            },
                            isError = block.answerNotSpecified,
                            leftEvaluateText = StringVO.Plain(block.leftPole),
                            rightEvaluateText = StringVO.Plain(block.rightPole)
                        )
                        Spacer(modifier = Modifier.height(if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    TypeOfBlocks.TEXT -> {
                        val itemsOfDescriptionBlock: MutableList<Pair<String, String>> =
                            mutableListOf()
                        block.description.forEach { itemOfBlockDescription ->
                            itemsOfDescriptionBlock.add(
                                Pair(
                                    when (itemOfBlockDescription.type) {
                                        TypeOfTitle.UNSTYLED -> "unstyled"
                                        TypeOfTitle.HEADER_ONE -> "header-one"
                                        TypeOfTitle.HEADER_TWO -> "header-two"
                                    },
                                    itemOfBlockDescription.text
                                )
                            )
                        }
                        TextFieldQuestion(
                            modifier = Modifier.fillMaxWidth(),
                            isError = false,
                            enabled = true,
                            listOfBlockDescription = itemsOfDescriptionBlock
                        )
                        Spacer(modifier = Modifier.height(if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    TypeOfBlocks.IMAGE -> {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = InTouchTheme.colors.input85,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 14.dp, bottom = 20.dp)
                            )
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
                                    modifier = Modifier.fillMaxSize(),
                                    placeholder = painterResource(id = care.intouch.uikit.R.drawable.dunning_kruger_effect_image)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(if (state.blocks.size - 1 == index) 34.dp else 40.dp))
                    }

                    else -> {

                    }
                }
            }
            Row(
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
                    isChecked = state.isCheckedToggle,
                    onChange = {
                        onEvent(QuestionEvent.OnCheckedToggle(!state.isCheckedToggle))
                    }
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            IntouchButton(
                onClick = {
                    onEvent(QuestionEvent.OnShowCompleteTaskDialog(true))
                },
                isEnabled = if (state.isShowCompleteTaskDialog) false else true,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = StringVO.Resource(R.string.complete_task),
                contentPadding = PaddingValues(horizontal = 51.dp, vertical = 13.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
    if (state.isShowClosingDialog) {
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
                        onEvent(QuestionEvent.OnShowClosingDialog(false))
                    },
                    firstLineText = StringVO.Resource(R.string.questions_popap_closing_task_first_line),
                    secondLineText = StringVO.Resource(R.string.questions_popup_closing_task_second_line),
                    intouchButtonText = StringVO.Resource(R.string.save_in_progress_button),
                    secondaryButtonText = StringVO.Resource(R.string.discard_close_button)
                )
            }
        )
    }
    if (state.isShowCompleteTaskDialog) {
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
                            onEvent(QuestionEvent.OnShowCompleteTaskDialog(false))
                        },
                        secondaryButtonClick = {
                            onEvent(QuestionEvent.OnPatchBlocksAssignment())
                        },
                        firstLineText = StringVO.Resource(R.string.questions_popap_not_all_filled),
                        intouchButtonText = StringVO.Resource(R.string.back_button),
                        secondaryButtonText = StringVO.Resource(R.string.complete_as_is_button)
                    )
                }
            }
        )
    }
}

private fun updateSingleChoiceReplies(
    list: List<AssignmentsChoiceReplies>,
    id: Int
): List<AssignmentsChoiceReplies> {
    val result: MutableList<AssignmentsChoiceReplies> = mutableListOf()
    list.forEach {
        if (it.id == id) {
            result.add(
                it.copy(
                    checked = true
                )
            )
        } else {
            result.add(
                it.copy(
                    checked = false
                )
            )
        }
    }
    return result
}

private fun updateMultipleChoiceReplies(
    list: List<AssignmentsChoiceReplies>,
    id: Int
): List<AssignmentsChoiceReplies> {
    val result: MutableList<AssignmentsChoiceReplies> = mutableListOf()
    list.forEach {
        if (it.id == id) {
            result.add(
                it.copy(
                    checked = !it.checked
                )
            )
        } else {
            result.add(it)
        }
    }
    return result
}

@Preview(showBackground = true, heightDp = 1960)
@Composable
fun QuestionsScreenPreview() {
    val blocks: MutableList<QuestionsBlock> = mutableListOf(
        QuestionsBlock(
            id = 7,
            choiceReplies = null,
            leftPole = "",
            rightPole = "",
            image = null,
            question = "Это текстовый блок (text)\n\nЭто заголовок 1\n\nЭто заголовок 2",
            reply = "",
            description = mutableListOf(
                BlockDescription(
                    type = TypeOfTitle.UNSTYLED,
                    text = "Это текстовый блок (text)",
                ),
                BlockDescription(
                    type = TypeOfTitle.UNSTYLED,
                    text = "",
                ),
                BlockDescription(
                    type = TypeOfTitle.HEADER_ONE,
                    text = "Это заголовок 1",
                ),
                BlockDescription(
                    type = TypeOfTitle.HEADER_ONE,
                    text = "Это заголовок 1",
                ),
                BlockDescription(
                    type = TypeOfTitle.HEADER_ONE,
                    text = "",
                ),
                BlockDescription(
                    type = TypeOfTitle.HEADER_TWO,
                    text = "Это заголовок 2",
                ),
            ),
            type = TypeOfBlocks.TEXT,
            startRange = 1,
            endRange = 10,
            selectedValue = 1,
            answerNotSpecified = true,
        )
    )
    val state = QuestionsState(
        blocks = blocks
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