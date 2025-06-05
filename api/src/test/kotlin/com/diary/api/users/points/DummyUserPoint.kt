package com.diary.api.users.points

import com.diary.domain.entity.users.points.UserPoint
import com.diary.domain.type.ID

object DummyUserPoint {
    private val id: ID = "0KH4WDSJA2CHB"
    private val amount: Int = 2000
    private val reason: String = "회원 가입 환영 포인트"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyUserPoint: UserPoint by lazy {
        UserPoint(
            id = id,
            amount = amount,
            reason = reason
        )
    }

    fun toEntity(): UserPoint {
        return dummyUserPoint
    }
}
