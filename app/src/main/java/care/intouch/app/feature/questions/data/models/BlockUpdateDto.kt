package care.intouch.app.feature.questions.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockUpdateDto(
    val blocks: List<RequestBlockDto>?,
    val grade: Int,
    val review: String
)
