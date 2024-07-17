package care.intouch.app.feature.questions.domain.useCase

import care.intouch.app.feature.questions.domain.models.Assignments

interface AssignmentsRepository {
    suspend fun getAssignments(id: Int): Result<Assignments>

    suspend fun shareWithTherapist(id: Int) : Result<String>
}


