package care.intouch.app.core.navigation.navhost

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import care.intouch.app.core.navigation.CreatingNoteIntroduction
import care.intouch.app.core.navigation.Diary
import care.intouch.app.core.navigation.DiaryEntries
import care.intouch.app.core.navigation.DiaryRouteBranch
import care.intouch.app.core.navigation.EmotionChoice
import care.intouch.app.feature.diary.presentation.ui.CreatingNoteIntroductionScreen
import care.intouch.app.feature.diary.presentation.ui.DiaryEntriesScreen
import care.intouch.app.feature.diary.presentation.ui.EmotionChoiceScreen

fun NavGraphBuilder.addNestedDiaryGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = CreatingNoteIntroduction.route,
        route = DiaryRouteBranch.route
    ) {
        composable(route = CreatingNoteIntroduction.route) {
            CreatingNoteIntroductionScreen(
                onNextClick = {
                    navController.navigate(route = DiaryEntries.route)
                },
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = DiaryEntries.route) {
            FillingOutScreen(
                onNextClick = {
                    navController.navigate(route = EmotionChoice.route)
                },
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }

        composable(route = EmotionChoice.route) {
            EmotionChoiceScreen(
                onSaveClick = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }
    }
}