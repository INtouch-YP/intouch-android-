package care.intouch.app.feature.questions.domain.models

data class RequestBlock(
    val choiceReplies: List<AssignmentsChoiceReplies>,
    val leftPole: String,
    val rightPole: String,
    val reply: String
)
