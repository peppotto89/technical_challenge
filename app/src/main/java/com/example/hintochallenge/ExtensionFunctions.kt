package com.example.hintochallenge

import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.model.PostDto
import com.example.hintochallenge.data.model.PostEntity

fun PostEntity.toDomain(): Post {
    return Post(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )
}

fun Post.toEntity(): PostEntity {
    return PostEntity(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )
}

fun PostDto.toDomain(): Post {
    return Post(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )
}

