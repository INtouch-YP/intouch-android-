package care.intouch.app.feature.questions.domain.models

data class AssignmentsBlock(
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
)
