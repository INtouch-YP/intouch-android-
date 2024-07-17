package care.intouch.app.feature.questions.domain.useCase

import care.intouch.app.feature.questions.domain.models.Assignments
import care.intouch.app.feature.questions.domain.models.BlockUpdate
import javax.inject.Inject

interface PatchBlockAssignmentUseCase {
    suspend operator fun invoke(id: Int, data:BlockUpdate) : Result<Assignments>

    class Base @Inject constructor(
        private val assignmentsRepository: AssignmentsRepository
    ): PatchBlockAssignmentUseCase
    {
        override suspend fun invoke(id: Int, data: BlockUpdate): Result<Assignments> {
            return assignmentsRepository.patchClientAssingment(id, data)
        }

    }
}