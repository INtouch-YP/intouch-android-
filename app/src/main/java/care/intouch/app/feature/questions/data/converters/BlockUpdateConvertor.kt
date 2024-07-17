package care.intouch.app.feature.questions.data.converters

import care.intouch.app.feature.questions.data.models.AssignmentsChoiceRepliesDto
import care.intouch.app.feature.questions.data.models.BlockUpdateDto
import care.intouch.app.feature.questions.data.models.RequestBlockDto
import care.intouch.app.feature.questions.domain.models.AssignmentsChoiceReplies
import care.intouch.app.feature.questions.domain.models.BlockUpdate
import care.intouch.app.feature.questions.domain.models.RequestBlock
import javax.inject.Inject

class BlockUpdateConvertor @Inject constructor() {

    fun map(block: BlockUpdate) : BlockUpdateDto {
        return BlockUpdateDto(
            blocks = block.blocks?.map {
                updateBlockMapToDto(it)
            },
            grade = block.grade,
            review = block.review
        )
    }

    private fun updateBlockMapToDto(block: RequestBlock) : RequestBlockDto {
        return RequestBlockDto(
            choiceReplies = block.choiceReplies.map {
                choiceRepliesMapToDto(it)
            },
            leftPole = block.leftPole,
            rightPole = block.rightPole,
            reply = block.reply
        )
    }

    private fun choiceRepliesMapToDto(choiceReplies: AssignmentsChoiceReplies): AssignmentsChoiceRepliesDto {
        return AssignmentsChoiceRepliesDto(
            id = choiceReplies.id,
            reply = choiceReplies.reply,
            checked = choiceReplies.checked
        )
    }

    fun map(block: BlockUpdateDto): BlockUpdate {
        return BlockUpdate(
            blocks = block.blocks?.map {
                updateBlockMap(it)
            },
            grade = block.grade,
            review = block.review
        )
    }

    private fun updateBlockMap(block: RequestBlockDto) : RequestBlock {
        return RequestBlock(
            choiceReplies = block.choiceReplies.map {
                choiceRepliesMap(it)
            },
            leftPole = block.leftPole,
            rightPole = block.rightPole,
            reply = block.reply
        )
    }

    private fun choiceRepliesMap(choiceReplies: AssignmentsChoiceRepliesDto): AssignmentsChoiceReplies {
        return AssignmentsChoiceReplies(
            id = choiceReplies.id,
            reply = choiceReplies.reply,
            checked = choiceReplies.checked
        )
    }

}