package care.intouch.app.feature.questions.domain.models

data class BlockUpdate(
    val blocks: List<RequestBlock>?,
    val grade: Int?,
    val review: String?
)
