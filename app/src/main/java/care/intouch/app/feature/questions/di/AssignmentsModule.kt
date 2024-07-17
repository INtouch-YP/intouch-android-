package care.intouch.app.feature.questions.di

import care.intouch.app.feature.questions.data.impl.AssignmentsRepositoryImpl
import care.intouch.app.feature.questions.domain.useCase.AssignmentsRepository
import care.intouch.app.feature.questions.domain.useCase.GetAssignmentsUseCase
import care.intouch.app.feature.questions.domain.useCase.PatchBlockAssignmentUseCase
import care.intouch.app.feature.questions.domain.useCase.ShareAssignmentWithTherapistUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface AssignmentsModule {

    @Binds
    fun bindGetAssignmentsUseCase(impl: GetAssignmentsUseCase.Base): GetAssignmentsUseCase

    @Binds
    fun  bindSharaAssignmentWithTherapistUseCase(impl: ShareAssignmentWithTherapistUseCase.Base): ShareAssignmentWithTherapistUseCase

    @Binds
    fun bindPatchBlockAssignmentUseCase(impl: PatchBlockAssignmentUseCase.Base): PatchBlockAssignmentUseCase

    @Binds
    fun bindGetAssignmentsRepository(impl: AssignmentsRepositoryImpl): AssignmentsRepository
}
