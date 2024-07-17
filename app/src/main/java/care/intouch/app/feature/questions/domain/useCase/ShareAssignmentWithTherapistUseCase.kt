package care.intouch.app.feature.questions.domain.useCase

import care.intouch.app.feature.questions.domain.models.UpdateVisibleAssignmentResponse
import javax.inject.Inject

interface ShareAssignmentWithTherapistUseCase {
    suspend operator fun invoke(id: Int) : Result<UpdateVisibleAssignmentResponse>

    class Base @Inject constructor(
        private val assignmentsRepository: AssignmentsRepository
    ): ShareAssignmentWithTherapistUseCase
    {
        override suspend fun invoke(id: Int): Result<UpdateVisibleAssignmentResponse> {
            return assignmentsRepository.shareWithTherapist(id)
        }
    }
}