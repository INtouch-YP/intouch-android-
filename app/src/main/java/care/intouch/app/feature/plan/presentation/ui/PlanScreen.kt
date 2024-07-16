package care.intouch.app.feature.plan.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import care.intouch.app.R
import care.intouch.app.feature.home.presentation.ui.FoldingScreen
import care.intouch.app.feature.plan.domain.models.AssignmentStatus
import care.intouch.app.feature.plan.domain.useCase.mockAssignments
import care.intouch.app.feature.plan.presentation.models.PlanScreenEvent
import care.intouch.app.feature.plan.presentation.models.PlanScreenState
import care.intouch.app.feature.plan.presentation.viewmodel.PlanScreenViewModel
import care.intouch.uikit.common.StringVO
import care.intouch.uikit.theme.InTouchTheme
import care.intouch.uikit.ui.cards.ConformationDialog
import care.intouch.uikit.ui.screens.my_plan.my_plan.CardHolder
import care.intouch.uikit.ui.screens.my_plan.my_plan.ChipsRowItem
import care.intouch.uikit.ui.screens.my_plan.my_plan.ChipsRow
import care.intouch.uikit.ui.screens.my_plan.my_plan.PlanHeader

@Composable
fun PlanScreen(
    onTaskListItemClick: (Int) -> Unit,
    onBackArrowClick: () -> Unit
) {
    val viewModel: PlanScreenViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    PlanScreen(
        state = state,
        onEvent = { planScreenEvent ->
            viewModel.onEvent(planScreenEvent)
        },
        onTaskListItemClick = onTaskListItemClick,
        onBackArrowClick = onBackArrowClick
    )
}

@Composable
fun PlanScreen(
    state: PlanScreenState,
    onEvent: (PlanScreenEvent) -> Unit,
    onTaskListItemClick: (Int) -> Unit,
    onBackArrowClick: () -> Unit
) {
    val context = LocalContext.current

    val chipsRowList = listOf(
        ChipsRowItem(
            title = StringVO.Resource(care.intouch.uikit.R.string.show_all),
            onItemClick = {
                onEvent(PlanScreenEvent.FilterAssignmentsEvent(
                    assignmentStatus = AssignmentStatus.SHOW_ALL
                ))
            }
        ),
        ChipsRowItem(
            title = StringVO.Resource(care.intouch.uikit.R.string.to_do),
            onItemClick = {
                onEvent(PlanScreenEvent.FilterAssignmentsEvent(
                    assignmentStatus = AssignmentStatus.TO_DO
                ))
            }
        ),
        ChipsRowItem(
            title = StringVO.Resource(care.intouch.uikit.R.string.in_progress),
            onItemClick = {
                onEvent(PlanScreenEvent.FilterAssignmentsEvent(
                    assignmentStatus = AssignmentStatus.IN_PROGRESS
                ))
            }
        ),
        ChipsRowItem(
            title = StringVO.Resource(care.intouch.uikit.R.string.done),
            onItemClick = {
                onEvent(PlanScreenEvent.FilterAssignmentsEvent(
                    assignmentStatus = AssignmentStatus.DONE
                ))
            }
        ),
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PlanHeader(
                modifier = Modifier.padding(bottom = 24.dp),
                onBackArrowClick = {
                    onBackArrowClick.invoke()
                }
            )

            ChipsRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                chipsRowList = chipsRowList
            )

            LazyColumn(
                modifier = Modifier.padding(top = 28.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                itemsIndexed(state.filteredAssignments) { index, assignment ->
                    CardHolder(
                        chipText = assignment.status.value,
                        text = assignment.title,
                        dateText = assignment.date,
                        onCardHolderClick = {
                            onTaskListItemClick.invoke(assignment.id)
                        },
                        onDuplicateMenuItemClick = {
                            Toast.makeText(
                                context,
                                "Duplicate operation. Assignment index: $index",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onClearMenuItemClick = {
                            Toast.makeText(
                                context,
                                "Clean operation. Assignment index: $index",
                                Toast.LENGTH_SHORT
                            ).show()

                            onEvent(PlanScreenEvent.SetDialogueVisibilityEvent(isVisible = true))
                        },
                        onClickToggle = { toggleValue ->
                            Toast.makeText(
                                context,
                                "Toggle action with '$toggleValue' and assignment index: $index",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }

        if (state.isDialogueVisible) {
            FoldingScreen()
            ConformationDialog(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onDismissRequest = {
                    Toast.makeText(context, "On dismiss dialogue", Toast.LENGTH_SHORT).show()
                    onEvent(PlanScreenEvent.SetDialogueVisibilityEvent(isVisible = false))
                },
                onConfirmation = {
                    Toast.makeText(context, "On confirm dialogue", Toast.LENGTH_SHORT).show()
                    onEvent(PlanScreenEvent.SetDialogueVisibilityEvent(isVisible = false))
                },
                headerText = StringVO.Resource(resId = R.string.info_delete_task_question).value(),
                dialogText = StringVO.Resource(resId = R.string.warning_delete).value(),
                dismissButtonText = StringVO.Resource(resId = R.string.cancel_button).value(),
                confirmButtonText = StringVO.Resource(resId = R.string.confirm_button).value()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlanScreenPreview() {
    InTouchTheme {
        PlanScreen(
            state = PlanScreenState(
                filteredAssignments = mockAssignments
            ),
            onEvent = {},
            onTaskListItemClick = {},
            onBackArrowClick = {}
        )
    }
}