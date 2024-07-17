package care.intouch.app.feature.questions.domain.useCase

import care.intouch.app.feature.questions.domain.models.Assignments
import care.intouch.app.feature.questions.domain.models.BlockUpdate
import care.intouch.app.feature.questions.domain.models.UpdateVisibleAssignmentResponse

interface AssignmentsRepository {
    suspend fun getAssignments(id: Int): Result<Assignments>

    suspend fun shareWithTherapist(id: Int) : Result<UpdateVisibleAssignmentResponse>

    suspend fun patchClientAssingment(id: Int, data: BlockUpdate): Result<Assignments>
}


