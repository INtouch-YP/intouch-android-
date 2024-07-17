package care.intouch.app.feature.questions.domain.useCase

import care.intouch.app.feature.questions.domain.models.Assignments
import javax.inject.Inject

interface GetAssignmentsUseCase {
    suspend operator fun invoke(id: Int): Result<Assignments>

    class Base @Inject constructor(
        private val assignmentsRepository: AssignmentsRepository
    ): GetAssignmentsUseCase {
        override suspend fun invoke(id: Int): Result<Assignments> {
            return assignmentsRepository.getAssignments(id)
        }
    }
}