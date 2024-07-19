package care.intouch.app.feature.questions.domain.models

data class Assignments(
    val id: Int,
    val title: String,
    val text: String,
    val updateDate: String,
    val addDate: String,
    val assignmentType: String,
    val status: String,
    val tags: String,
    val language: String,
    val share: Int,
    val imageUrl: String,
    val blocks: List<AssignmentsBlock>,
    val author: Int,
    val authorName: String,
    val user: Int,
    val visible: Boolean,
    val grade: Int?,
    val review: String?,
    val assignmentRoot: Int
)
