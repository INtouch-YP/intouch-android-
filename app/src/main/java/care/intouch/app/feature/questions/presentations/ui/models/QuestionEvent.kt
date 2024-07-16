package care.intouch.app.feature.questions.presentations.ui.models

import care.intouch.app.feature.questions.domain.models.AssignmentsChoiceReplies
import care.intouch.app.feature.questions.domain.models.TypeOfBlocks

sealed class QuestionEvent {
    data class OnBlockChange(
        val id: Int,
        val choiceReplies: List<AssignmentsChoiceReplies>? = null,
        val reply: String? = null,
        val type: TypeOfBlocks,
        val selectedValue: Int? = null,
    ): QuestionEvent()

    data class OnCheckedToggle(
        val isChecked: Boolean
    ): QuestionEvent()

    data class OnShowClosingDialog(
        val isShow: Boolean
    ): QuestionEvent()

    data class OnShowCompleteTaskDialog(
        val isShow: Boolean
    ): QuestionEvent()

}