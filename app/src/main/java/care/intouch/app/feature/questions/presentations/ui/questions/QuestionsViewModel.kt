package care.intouch.app.feature.questions.presentations.ui.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import care.intouch.app.feature.questions.domain.models.Assignments
import care.intouch.app.feature.questions.domain.models.AssignmentsBlock
import care.intouch.app.feature.questions.domain.models.TypeOfBlocks
import care.intouch.app.feature.questions.domain.useCase.GetAssignmentsUseCase
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
    private val getAssignments: GetAssignmentsUseCase
) : ViewModel() {

    // Задача полученная с сервера, получение реализовано в блоке init
    private var assignments: Assignments? = null
    private var _state = MutableStateFlow(QuestionsState(mutableListOf()))
    val state = _state.asStateFlow()

    init {      // Получам данные по задаче
        viewModelScope.launch(Dispatchers.IO) {
            getAssignments.invoke(1)
                .onSuccess {        // тут id - хардкод, в будущем будем выдергивать id из данных с предыдущего экрана
                    assignments = it
                    addBlocksInState(it.blocks)
                }
        }
    }

    fun onEvent(event: QuestionEvent) {
        when(event) {
            is QuestionEvent.OnBlockChange -> {
                updateState(event)
            }

            is QuestionEvent.OnCheckedToggle -> {
                _state.update {
                    _state.value.copy(
                        isCheckedToggle = event.isChecked
                    )
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
            }
        }
    }



    private fun addBlocksInState(blocks: List<AssignmentsBlock>) {  //конвертирую блоки с сервера в блоки для стейта и выставляю на доп поля значения по хардкоду
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
                    answerNotSpecified = false     // ставлю true  чтоб поле не отрисовывалось красным при изначальной отрисовке экрана
                )                           // blockIsValid будет проверяться только при нажатии кнопки на отправку.
                // Вот функция для этого checkTheValidityOfBlocks()
            )
        }
        _state.update {     // обновляем стейт
            _state.value.copy(
                blocks = result
            )
        }
    }

    // как-то надо проверить можно ли нажимать на кнопки "далее", "отправить врачу" или что там еще
    // и их состояния так же поместить в стейт, добавить там полей то есть

    private fun checkTheValidityOfBlocks() {    // Проверка ответили на вопрос или нет
        val result: MutableList<QuestionsBlock> = mutableListOf()
        _state.value.blocks.forEach {
            when(it.type) {

                TypeOfBlocks.OPEN -> {
                    if(it.reply.isEmpty()) {
                        result.add(it.copy(
                            answerNotSpecified = true
                        ))
                    } else {
                        result.add(it)
                    }
                }

                TypeOfBlocks.SINGLE, TypeOfBlocks.MULTIPLE -> {
                    var answerWasChoice = false
                    it.choiceReplies!!.forEach {
                        if(it.checked) {
                            answerWasChoice = true
                            //break    // не даёт прервать цикл, потом разберусь
                        }
                    }
                    if(answerWasChoice) {
                        result.add(it)
                    } else {
                        result.add(it.copy(
                            answerNotSpecified = true
                        ))
                    }
                }

                else -> {       // Сюда идут TypeOfBlocks.UNDEFINED, TypeOfBlocks.IMAGE, TypeOfBlocks.RANGE, TypeOfBlocks.TEXT
                    result.add(it)
                }
            }
        }
        _state.update {     // обновляем стейт
            _state.value.copy(
                blocks = result
            )
        }
    }

    private fun updateState(event: QuestionEvent.OnBlockChange) {       // Поганое решение, но пока самое простое что в голову пришло
        val result: MutableList<QuestionsBlock> = mutableListOf()
        when(event.type) {
            TypeOfBlocks.OPEN -> {
                _state.value.blocks.forEach {
                    if(it.id == event.id){
                        result.add(it.copy(
                            reply = event.reply!!
                        ))
                    } else {
                        result.add(it)
                    }
                }
            }

            TypeOfBlocks.SINGLE, TypeOfBlocks.MULTIPLE -> {
                _state.value.blocks.forEach {
                    if(it.id == event.id){
                        result.add(it.copy(
                            choiceReplies = event.choiceReplies!!
                        ))
                    } else {
                        result.add(it)
                    }
                }
            }
            TypeOfBlocks.RANGE -> {
                _state.value.blocks.forEach {
                    if(it.id == event.id){
                        result.add(it.copy(
                            selectedValue = event.selectedValue!!
                        ))
                    } else {
                        result.add(it)
                    }
                }
            }

            else ->{

            }
        }
        _state.update {     // обновляем стейт
            _state.value.copy(
                blocks = result
            )
        }
    }
}