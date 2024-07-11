package care.intouch.app.feature.diary.presentation.ui.EmotionScreens.models

import care.intouch.uikit.common.StringVO

data class EmotionDescriptionTask(
    val text: StringVO,
    val emotionDescriptionEnum: EmotionDescriptionEnum,
)
