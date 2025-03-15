package com.daangn.api.service.posts

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.PostResponseDto
import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.service.posts.converter.PostConverter
import com.daangn.api.service.posts.converter.PostImageConverter
import com.daangn.api.service.posts.updater.PostUpdater
import com.daangn.api.service.posts.validator.PostValidator
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.CategoryRepositoryWrapper
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.PostOrderType
import com.daangn.domain.entity.posts.PostQueryFilter
import com.daangn.domain.entity.posts.PostRepositoryWrapper
import com.daangn.domain.entity.users.UserRepositoryWrapper
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val repositoryWrapper: PostRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val categoryRepositoryWrapper: CategoryRepositoryWrapper,
    private val converter: PostConverter,
    private val postImageConverter: PostImageConverter,
    private val updater: PostUpdater,
    private val validator: PostValidator,
) {

    @Transactional
    fun create(userId: String, request: CreatePostRequestDto): String {
        validator.validate(request)
        val post = converter.convert(request).also { post ->
            val user = userRepositoryWrapper.findById(userId)
            val category = categoryRepositoryWrapper.findById(request.categoryId)
            post.set(user)
            post.set(category)
            request.mainImages.forEach { postMainImageRequest ->
                postImageConverter.convert(postMainImageRequest).also { postMainImage ->
                    postMainImage.set(post)
                }
            }
        }
        return repositoryWrapper.save(post).id
    }

    @Transactional(readOnly = true)
    fun getPosts(
        queryFilter: PostQueryFilter,
        pagination: Pagination,
        orderTypes: List<PostOrderType>
    ): PaginationResponseDto {
        val posts = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = repositoryWrapper.searchCount(
            queryFilter = queryFilter
        )
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(posts),
            totalCount = totalCount
        )
    }

    @Transactional(readOnly = true)
    fun getPost(postId: String): PostResponseDto {
        val post = repositoryWrapper.findById(postId)
        return converter.convert(post)
    }

    @Transactional
    fun update(userId: String, postId: String, request: UpdatePostRequestDto): String {
        validator.validate(request)
        val post = repositoryWrapper.findById(postId)
        validatePostWriter(
            userId = userId,
            post = post
        )
        updater.markAsUpdate(
            request = request,
            entity = post
        )
        return postId
    }

    @Transactional
    fun delete(userId: String, postId: String): Boolean {
        val post = repositoryWrapper.findById(postId)
        validatePostWriter(
            userId = userId,
            post = post
        )
        post.delete()
        return true
    }

    private fun validatePostWriter(userId: String, post: Post) {
        if (post.writer!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.ONLY_POST_WRITER_CAN_ACCESS,
                message = ErrorCode.ONLY_POST_WRITER_CAN_ACCESS.message
            )
        }
    }
}
