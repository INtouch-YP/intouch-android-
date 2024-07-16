package care.intouch.app.feature.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import care.intouch.app.R
import care.intouch.app.feature.common.Resource
import care.intouch.app.feature.diary.domain.modal.Diary
import care.intouch.app.feature.diary.domain.useCase.DeleteDiaryUC
import care.intouch.app.feature.diary.domain.useCase.GetDiariesUC
import care.intouch.app.feature.diary.domain.useCase.SwitchVisibleUC
import care.intouch.app.feature.diary.presentation.ui.models.DiaryChangeEvent
import care.intouch.app.feature.diary.presentation.ui.models.DiaryDataState
import care.intouch.app.feature.diary.presentation.ui.models.DiaryEntry
import care.intouch.app.feature.diary.presentation.ui.models.DiaryPopUp
import care.intouch.app.feature.diary.presentation.ui.models.DiaryUiState
import care.intouch.app.feature.diary.presentation.ui.models.Mood
import care.intouch.uikit.common.StringVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getDiariesUC: GetDiariesUC,
    private val deleteDiaryUC: DeleteDiaryUC,
    private val switchVisibleUC: SwitchVisibleUC,
) : ViewModel() {
    private val _diaryUIState = MutableStateFlow(DiaryUiState())
    val diaryUIState: StateFlow<DiaryUiState> = _diaryUIState.asStateFlow()
    private val _diaryDataState = MutableStateFlow(DiaryDataState())
    val diaryDataState: StateFlow<DiaryDataState> = _diaryDataState.asStateFlow()
    private val _sideEffect = MutableSharedFlow<DiaryPopUp>()
    val sideEffect: SharedFlow<DiaryPopUp> = _sideEffect.asSharedFlow()

    init {
        loadDiaryData()
        setUserName()

        viewModelScope.launch {
            diaryDataState.collect { state ->
                _diaryUIState.update {
                    it.copy(
                        diaryList = state.noteList
                    )
                }
            }
        }
    }

    private fun getState(): DiaryDataState = _diaryDataState.value

    fun onEvent(event: DiaryChangeEvent) {
        when (event) {
            is DiaryChangeEvent.OnShareWithDoc -> {
                onShareToggle(
                    index = event.index,
                    event.sharedWithDoctor,
                    id = event.idToShare
                )

            }

            is DiaryChangeEvent.IntentionToDelete -> {
                showDeletePopup(
                    index = event.index,
                    id = event.idToDelete
                )
            }
        }
    }

    private fun showDeletePopup(index: Int, id: Int) {
        showPopup(
            title = StringVO.Resource(R.string.info_delete_node_question),
            massage = StringVO.Resource(R.string.warning_delete),
            onConfirmButtonText = StringVO.Resource(R.string.confirm_button),
            onDismissButtonText = StringVO.Resource(R.string.cancel_button),
            onConfirm = {
                handleDeleteDiaryEntry(index, id)
            },
            onDismiss = {}
        )
    }

    private fun handleDeleteDiaryEntry(index: Int, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (deleteDiaryUC.invoke(id)) {
                is Resource.Error -> {}

                is Resource.Success -> {
                    val newDiaryList = getState().noteList.toMutableList()
                    newDiaryList.removeAt(index)
                    _diaryDataState.update {
                        it.copy(noteList = newDiaryList)
                    }
                }
            }
        }
    }

    private fun onShareToggle(index: Int, isShared: Boolean, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = switchVisibleUC.invoke(id)) {
                is Resource.Error -> {
                }

                is Resource.Success -> {
                    val shareNote = getState()
                        .noteList[index]
                        .copy(sharedWithDoc = isShared)
                    val diaryList = getState().noteList.toMutableList()
                    diaryList[index] = shareNote
                    _diaryDataState.update { state ->
                        state.copy(noteList = diaryList)
                    }

                }
            }
        }
    }

    private fun showPopup(
        title: StringVO,
        massage: StringVO,
        onConfirmButtonText: StringVO,
        onDismissButtonText: StringVO,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        viewModelScope.launch {
            _sideEffect.emit(
                DiaryPopUp.ShowPopUp(
                    title = title,
                    massage = massage,
                    onConfirmButtonText = onConfirmButtonText,
                    onDismissButtonText = onDismissButtonText,
                    onConfirm = onConfirm,
                    onDismiss = onDismiss
                )
            )
        }
    }

    private fun setUserName() {
        val name = buildString {
            append("Bober Jan")
        }
        _diaryUIState.update {
            it.copy(userName = name)
        }
    }

    private fun mapperToDiaryEntry(diary: Diary): DiaryEntry {
        val inputDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
        val date = inputDateFormat.parse(diary.addDate)
        val calendar = Calendar.getInstance().apply { time = date }
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val month = monthFormat.format(date).lowercase(Locale.ROOT)
        val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

        return DiaryEntry(
            id = diary.id,
            data = "$month $day",
            note = diary.eventDetails,
            moodList = diary.clarifyingEmotion.map {
                Mood(it)
            },
            sharedWithDoc = diary.visible
        )
    }

    private fun loadDiaryData() {
        viewModelScope.launch(Dispatchers.IO) {
            getDiariesUC.invoke().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _diaryDataState.update {
                            it.copy(noteList = emptyList())
                        }
                    }

                    is Resource.Success -> {
                        _diaryDataState.update {
                            it.copy(noteList = result.data.map { diary: Diary ->
                                mapperToDiaryEntry(diary)
                            })
                        }
                    }
                }
            }
        }
    }
}