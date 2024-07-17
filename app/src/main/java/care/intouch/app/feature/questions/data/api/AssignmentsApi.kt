package care.intouch.app.feature.questions.data.api

import care.intouch.app.feature.questions.data.models.AssignmentsDto
import care.intouch.app.feature.questions.data.models.BlockUpdateDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AssignmentsApi {
    @GET("/api/v1/assignments-client/{id}/")
    suspend fun getAssignments(
        @Path("id") id: Int,
    ): AssignmentsDto

    @PATCH("/api/v1/assignments-client/{id}/visible/")
    suspend fun shareWithTherapist (
        @Path("id") id: Int ): String

    @PATCH("/api/v1/assignments-client/{id}/complete/")
    suspend fun compliteClientsAssignment (
        @Path("id") id: Int ): String

    @PATCH("\"/api/v1/assignments-client/{id}/")
    suspend fun patchClientAssignment(
        @Path("id") id: Int,
        @Body data: BlockUpdateDto
    ): AssignmentsDto
}


