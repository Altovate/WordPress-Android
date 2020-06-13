package org.wordpress.android.ui.posts.prepublishing.home.usecases

import org.wordpress.android.fluxc.model.PostModel
import org.wordpress.android.fluxc.model.post.PostStatus
import org.wordpress.android.ui.posts.EditPostRepository
import org.wordpress.android.util.DateTimeUtilsWrapper
import javax.inject.Inject

class PublishPostImmediatelyUseCase @Inject constructor(private val dateTimeUtilsWrapper: DateTimeUtilsWrapper) {
    fun updatePostToPublishImmediately(
        editPostRepository: EditPostRepository,
        isNewPost: Boolean
    ) {
        editPostRepository.updateAsync({ postModel: PostModel ->
            postModel.setDateCreated(dateTimeUtilsWrapper.currentTimeInIso8601())

            // when the post is a Draft, Publish Now is shown as the Primary Action but if it's already Published then
            // Update Now is shown.
            if (isNewPost) {
                postModel.setStatus(PostStatus.DRAFT.toString())
            } else {
                postModel.setStatus(PostStatus.PUBLISHED.toString())
            }
            true
        })
    }
}
