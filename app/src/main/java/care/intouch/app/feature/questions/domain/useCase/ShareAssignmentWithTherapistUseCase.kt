package care.intouch.app.feature.questions.domain.useCase

import javax.inject.Inject

interface ShareAssignmentWithTherapistUseCase {
    suspend operator fun invoke(id: Int) : Result<String>

    class Base @Inject constructor(
        private val assignmentsRepository: AssignmentsRepository
    ): ShareAssignmentWithTherapistUseCase
    {
        override suspend fun invoke(id: Int): Result<String> {
            return assignmentsRepository.shareWithTherapist(id)
        }
    }
}