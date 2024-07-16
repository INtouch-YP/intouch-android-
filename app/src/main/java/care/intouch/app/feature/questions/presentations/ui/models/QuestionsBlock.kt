package care.intouch.app.feature.questions.presentations.ui.models

import care.intouch.app.feature.questions.domain.models.AssignmentsChoiceReplies
import care.intouch.app.feature.questions.domain.models.BlockDescription
import care.intouch.app.feature.questions.domain.models.TypeOfBlocks

data class QuestionsBlock(
    val id: Int,
    val choiceReplies: List<AssignmentsChoiceReplies>?,
    val leftPole: String,
    val rightPole: String,
    val image: String?,
    val question: String,
    val reply: String,
    val description: List<BlockDescription>,
    val type: TypeOfBlocks,
    val startRange: Int,
    val endRange: Int,
    val selectedValue: Int,
    val blockIsValid: Boolean
)