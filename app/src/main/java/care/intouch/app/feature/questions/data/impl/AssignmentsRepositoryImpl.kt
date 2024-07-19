package care.intouch.app.feature.questions.data.impl

import android.util.Log
import care.intouch.app.feature.authorization.data.models.exception.AuthenticationException
import care.intouch.app.feature.authorization.data.models.mappers.NetworkToUserExceptionMapper.Companion.COULD_NOT_CONVERT_TO_ERROR_RESPONSE
import care.intouch.app.feature.common.data.models.exception.NetworkException
import care.intouch.app.feature.questions.data.api.AssignmentsApi
import care.intouch.app.feature.questions.data.converters.AssignmentsConvertor
import care.intouch.app.feature.questions.data.converters.BlockUpdateConvertor
import care.intouch.app.feature.questions.domain.models.Assignments
import care.intouch.app.feature.questions.domain.models.BlockUpdate
import care.intouch.app.feature.questions.domain.models.UpdateVisibleAssignmentResponse
import care.intouch.app.feature.questions.domain.useCase.AssignmentsRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AssignmentsRepositoryImpl  @Inject constructor(
    private val assignmentsApi: AssignmentsApi,
    private val json: Json,
    private val convertor: AssignmentsConvertor,
    private val convertorBlock: BlockUpdateConvertor
): AssignmentsRepository {
    override suspend fun getAssignments(id: Int): Result<Assignments> {
        try {
            val response = assignmentsApi.getAssignments(id)
            return Result.success(
                convertor.map(response)
            )
        } catch (e: NetworkException) {
            return when(e) {
                is NetworkException.BadRequest -> {
                    val response = handleErrorResponse<Assignments>(e.errorBody)
                    Result.failure(
                        NetworkException.BadRequest(
                            "Надо сюда поймать ошибку какую-нибудь",
                            e.httpStatusCode
                        )
                    )
                }
                else -> {
                    Result.failure(e)
                }
            }
        }
    }

    override suspend fun shareWithTherapist(id: Int): Result<UpdateVisibleAssignmentResponse> {
        try {
            val response = assignmentsApi.shareWithTherapist(id)
            return Result.success (
                UpdateVisibleAssignmentResponse(response.message)
            )
        } catch (e: NetworkException) {
            return when(e) {
                is NetworkException.BadRequest -> {
                    val response = handleErrorResponse<Assignments>(e.errorBody)
                    Result.failure(
                        NetworkException.BadRequest(
                            "Надо сюда поймать ошибку какую-нибудь",
                            e.httpStatusCode
                        )
                    )
                }
                else -> {
                    Result.failure(e)
                }
            }
        }
    }

    override suspend fun patchClientAssingment(id: Int, data: BlockUpdate): Result<Assignments> {
        try {
            Log.d("MY_INTOUCH_TAG", "Мы в AssignmentsRepositoryImpl в блоке try")
            val response = assignmentsApi.patchClientAssignment(id, convertorBlock.map(data))
            Log.d("MY_INTOUCH_TAG", "Запрос в AssignmentsRepositoryImpl в блоке try выполнен")
            Log.d("MY_INTOUCH_TAG", "Посмотрим что там в ответе - id =  ${response.id}")
            Log.d("MY_INTOUCH_TAG", "Посмотрим что там в ответе - title =  ${response.title}")
            Log.d("MY_INTOUCH_TAG", "Посмотрим что там в ответе - blocks count =  ${response.blocks.count()}")
            return Result.success(
                convertor.map(response)
            )
        } catch (e: NetworkException) {
            Log.d("MY_INTOUCH_TAG", "Мы в AssignmentsRepositoryImpl в блоке catch 1")
            return when (e) {
                is NetworkException.BadRequest -> {
                    Log.d("MY_INTOUCH_TAG", "Мы в AssignmentsRepositoryImpl в блоке catch 2")
                    val response = handleErrorResponse<Assignments>(e.errorBody)
                    Result.failure(
                        NetworkException.BadRequest(
                            "Надо сюда поймать ошибку какую-нибудь",
                            e.httpStatusCode
                        )
                    )
                }
                else -> {
                    Log.d("MY_INTOUCH_TAG", "Мы в AssignmentsRepositoryImpl в блоке catch 3")
                    Result.failure(e)
                }
            }
        }
    }

    private inline fun <reified T> handleErrorResponse(errorMessage: String): T {
        try {
            return json.decodeFromString<T>(errorMessage)

        } catch (e: Exception) {
            throw AuthenticationException.Undefined(COULD_NOT_CONVERT_TO_ERROR_RESPONSE)
        }
    }
}