package care.intouch.app.feature.diary.presentation.ui.EmotionScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import care.intouch.app.R
import care.intouch.app.feature.diary.presentation.ui.EmotionScreens.models.EmotionDescriptionTask
import care.intouch.uikit.common.StringVO
import care.intouch.uikit.theme.InTouchTheme
import care.intouch.uikit.ui.cards.EmotionDescriptionCard


@Composable
fun EmotionDescriptionPager(
    taskList: List<EmotionDescriptionTask>,
    onClick: (index: Int) -> Unit,
) {
    val selectedItems = remember { mutableStateListOf<EmotionDescriptionTask>() }
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Adaptive(40.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(239.dp),
        contentPadding = PaddingValues(top = 24.dp, start = 28.dp, end = 28.dp)
    ) {
        this.itemsIndexed(taskList) { index, items ->
            val isSelected = selectedItems.contains(items)
            EmotionDescriptionCard(
                modifier = Modifier.padding(end = 10.dp, bottom = 6.dp),
                text = items.text,
                selected = isSelected,
                onClick = {
                    onClick(index)
                    if (selectedItems.contains(items)) {
                        selectedItems.remove(items)
                    } else {
                        selectedItems.add(items)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmotionDescriptionPager() {
    InTouchTheme {
        EmotionDescriptionPager(
            taskList = listOf(
                EmotionDescriptionTask(StringVO.Resource(R.string.loss_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.humility_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.anxiety_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.embarrassment_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.depression_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.fear_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.anger_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.excitement_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.shame_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.disgust_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.guilt_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.nervousness_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.sadness_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.exhaustion_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.impatience_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.laziness_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.confusion_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.loneliness_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.acceptance_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.frustration_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.interest_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.pride_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.hope_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.calmness_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.gratitude_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.happiness_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.respect_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.enthusiasm_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.satisfaction_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.self_love_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.joy_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.inspiration_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.amazement_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.euphoria_clarifying_emotional)),
                EmotionDescriptionTask(StringVO.Resource(R.string.love_clarifying_emotional))
            ),
            onClick = {}
        )
    }
}
