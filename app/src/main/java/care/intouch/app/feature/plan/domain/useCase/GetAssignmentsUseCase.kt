package care.intouch.app.feature.plan.domain.usecase

import care.intouch.app.feature.common.Resource
import care.intouch.app.feature.common.domain.errors.ErrorEntity
import care.intouch.app.feature.plan.domain.api.PlanRepository
import care.intouch.app.feature.plan.domain.models.Assignment
import care.intouch.app.feature.plan.domain.models.AssignmentStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAssignmentsUseCase {
    suspend operator fun invoke(): Flow<Resource<List<Assignment>, ErrorEntity>>
    class Base @Inject constructor(
        private val planRepository: PlanRepository
    ) : GetAssignmentsUseCase {
        override suspend fun invoke(): Flow<Resource<List<Assignment>, ErrorEntity>> {
            return planRepository.getAssignments()
        }
    }
}