package care.intouch.app.feature.questions.presentations.ui.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import care.intouch.app.feature.questions.domain.models.Assignments
import care.intouch.app.feature.questions.domain.models.AssignmentsBlock
import care.intouch.app.feature.questions.domain.models.BlockUpdate
import care.intouch.app.feature.questions.domain.models.RequestBlock
import care.intouch.app.feature.questions.domain.models.TypeOfBlocks
import care.intouch.app.feature.questions.domain.useCase.GetAssignmentsUseCase
import care.intouch.app.feature.questions.domain.useCase.PatchBlockAssignmentUseCase
import care.intouch.app.feature.questions.domain.useCase.ShareAssignmentWithTherapistUseCase
import care.intouch.app.feature.questions.presentations.ui.models.QuestionEvent
import care.intouch.app.feature.questions.presentations.ui.models.QuestionsBlock
import care.intouch.app.feature.questions.presentations.ui.models.QuestionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val getAssignments: GetAssignmentsUseCase,
    private val shareAssignmentWithTherapist: ShareAssignmentWithTherapistUseCase,
    private val patchBlockAssignment: PatchBlockAssignmentUseCase
) : ViewModel() {

    private var assignments: Assignments? = null
    private var _state = MutableStateFlow(QuestionsState(mutableListOf()))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAssignments.invoke(1)
                .onSuccess {
                    assignments = it
                    addBlocksInState(it.blocks)
                }
        }
    }

    fun onEvent(event: QuestionEvent) {
        when (event) {
            is QuestionEvent.OnBlockChange -> {
                updateState(event)
            }

            is QuestionEvent.OnCheckedToggle -> {
                _state.update {
                    _state.value.copy(
                        isCheckedToggle = event.isChecked
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    assignments?.let { shareAssignmentWithTherapist.invoke(it.id) }
                }
            }

            is QuestionEvent.OnShowClosingDialog -> {
                _state.update {
                    _state.value.copy(
                        isShowClosingDialog = event.isShow
                    )
                }
            }

            is QuestionEvent.OnShowCompleteTaskDialog -> {
                _state.update {
                    _state.value.copy(
                        isShowCompleteTaskDialog = event.isShow
                    )
                }
                checkTheValidityOfBlocks()
            }

            is QuestionEvent.OnPatchBlocksAssignment -> {
                val blocksOfRequest: MutableList<RequestBlock> = mutableListOf()

                _state.value.blocks.forEach { questionBlock ->
                    blocksOfRequest.add(
                        RequestBlock(
                            choiceReplies = questionBlock.choiceReplies ?: emptyList(),
                            leftPole = questionBlock.leftPole,
                            rightPole = questionBlock.rightPole,
                            reply = questionBlock.reply
                        )
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    assignments?.let {
                        patchBlockAssignment(
                            it.id, BlockUpdate(
                                grade = assignments?.grade,
                                review = assignments?.review,
                                blocks = blocksOfRequest
                            )
                        )
                    }
                }
            }
        }
    }


    private fun addBlocksInState(blocks: List<AssignmentsBlock>) {
        val result: MutableList<QuestionsBlock> = mutableListOf()
        blocks.forEach {
            result.add(
                QuestionsBlock(
                    id = it.id,
                    choiceReplies = it.choiceReplies,
                    leftPole = it.leftPole,
                    rightPole = it.rightPole,
                    image = it.image,
                    question = it.question,
                    reply = it.reply,
                    description = it.description,
                    type = it.type,
                    startRange = it.startRange,
                    endRange = it.endRange,
                    selectedValue = 1,
                    answerNotSpecified = false
                )
            )
        }
        _state.update {
            _state.value.copy(
                blocks = result
            )
        }
    }

    private fun checkTheValidityOfBlocks() {
        val result: MutableList<QuestionsBlock> = mutableListOf()
        _state.value.blocks.forEach {
            when (it.type) {
                TypeOfBlocks.OPEN -> {
                    if (it.reply.isEmpty()) {
                        result.add(
                            it.copy(
                                answerNotSpecified = true
                            )
                        )
                    } else {
                        result.add(
                            it.copy(
                                answerNotSpecified = false
                            )
                        )
                    }
                }

                TypeOfBlocks.SINGLE, TypeOfBlocks.MULTIPLE -> {
                    var answerWasChoice = false
                    it.choiceReplies!!.forEach {
                        if (it.checked) {
                            answerWasChoice = true
                        }
                    }
                    if (answerWasChoice) {
                        result.add(it)
                    } else {
                        result.add(
                            it.copy(
                                answerNotSpecified = true
                            )
                        )
                    }
                }

                else -> {
                    result.add(it)
                }
            }
        }
        _state.update {
            _state.value.copy(
                blocks = result
            )
        }
    }

    private fun updateState(event: QuestionEvent.OnBlockChange) {
        val result: MutableList<QuestionsBlock> = mutableListOf()
        when (event.type) {
            TypeOfBlocks.OPEN -> {
                _state.value.blocks.forEach {
                    if (it.id == event.id) {
                        result.add(
                            it.copy(
                                reply = event.reply!!
                            )
                        )
                    } else {
                        result.add(it)
                    }
                }
            }

            TypeOfBlocks.SINGLE, TypeOfBlocks.MULTIPLE -> {
                _state.value.blocks.forEach {
                    if (it.id == event.id) {
                        result.add(
                            it.copy(
                                choiceReplies = event.choiceReplies!!
                            )
                        )
                    } else {
                        result.add(it)
                    }
                }
            }

            TypeOfBlocks.RANGE -> {
                _state.value.blocks.forEach {
                    if (it.id == event.id) {
                        result.add(
                            it.copy(
                                selectedValue = event.selectedValue!!
                            )
                        )
                    } else {
                        result.add(it)
                    }
                }
            }

            else -> {

            }
        }
        _state.update {
            _state.value.copy(
                blocks = result
            )
        }
    }

    private fun patchBlock(id: Int, data: BlockUpdate) {
        viewModelScope.launch(Dispatchers.IO) {
            patchBlockAssignment.invoke(id, data)
        }
    }
}