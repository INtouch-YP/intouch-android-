package care.intouch.app.feature.questions.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestBlockDto(
    @SerialName("choice_replies")
    val choiceReplies: List<AssignmentsChoiceRepliesDto>,
    @SerialName("left_pole")
    val leftPole: String,
    @SerialName("right_pole")
    val rightPole: String,
    val reply: String
)
