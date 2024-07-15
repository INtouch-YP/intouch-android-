package care.intouch.app.feature.home.domain.models

import care.intouch.app.R

enum class Mood(val nameId: Int) {
    Terrible(nameId = R.string.terrible_clarifying_emotional),
    Bad(nameId = R.string.bad_clarifying_emotional),
    Okay(nameId = R.string.okay_clarifying_emotional),
    Good(nameId = R.string.good_clarifying_emotional),
    Great(nameId = R.string.great_clarifying_emotional),
    Loss(nameId = R.string.loss_clarifying_emotional),
    Fear(nameId = R.string.fear_clarifying_emotional),
    Guilt(nameId = R.string.guilt_clarifying_emotional),
    Laziness(nameId = R.string.laziness_clarifying_emotional),
    Interest(nameId = R.string.interest_clarifying_emotional),
    Pride(nameId = R.string.pride_clarifying_emotional),
    Hope(nameId = R.string.hope_clarifying_emotional),
    Hapiness(nameId = R.string.happiness_clarifying_emotional),
    Anger(nameId = R.string.anger_clarifying_emotional),
    Anxiety(nameId = R.string.anxiety_clarifying_emotional),
    Embarrassment(nameId = R.string.embarrassment_clarifying_emotional),
    Calmness(nameId = R.string.calmness_clarifying_emotional),
    Inspiration(nameId = R.string.inspiration_clarifying_emotional),
    Humility(nameId = R.string.humility_clarifying_emotional),
    Nervousness(nameId = R.string.nervousness_clarifying_emotional),
    Depression(nameId = R.string.depression_clarifying_emotional),
    Shame(nameId = R.string.shame_clarifying_emotional),
    Amazement(nameId = R.string.amazement_clarifying_emotional),
    Euphoria(nameId = R.string.euphoria_clarifying_emotional),
    Disgust(nameId =R.string.disgust_clarifying_emotional),
    Sadness(nameId = R.string.sadness_clarifying_emotional),
    Joy(nameId = R.string.joy_clarifying_emotional),
    Respect(nameId = R.string.respect_clarifying_emotional),
    Exhaustion(nameId = R.string.exhaustion_clarifying_emotional),
    Impatience(nameId = R.string.impatience_clarifying_emotional),
    Excitement(nameId = R.string.excitement_clarifying_emotional),
    Loneliness(nameId = R.string.loneliness_clarifying_emotional),
    Frustration(nameId = R.string.frustration_clarifying_emotional),
    Acceptance(nameId = R.string.acceptance_clarifying_emotional),
    Enthusiasm(nameId = R.string.enthusiasm_clarifying_emotional),
    Love(nameId = R.string.love_clarifying_emotional),
    Dissapointment(nameId = R.string.disappointment_clarifying_emotional),
    Confusion(nameId = R.string.confusion_clarifying_emotional),
    Satisfaction(nameId = R.string.satisfaction_clarifying_emotional),
    SelfLove(nameId = R.string.self_love_clarifying_emotional),
    Gratitude(nameId = R.string.gratitude_clarifying_emotional)
}