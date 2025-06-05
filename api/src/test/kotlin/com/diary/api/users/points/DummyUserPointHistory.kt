package com.diary.api.users.points

import com.diary.domain.entity.users.points.UserPointHistory
import com.diary.domain.type.ID

object DummyUserPointHistory {
    private val id: ID = "0KH4WDSJA2CHB"
    private val amount: Int = 2000
    private val reason: String = "회원 가입 환영 포인트"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyUserPointHistory: UserPointHistory by lazy {
        UserPointHistory(
            id = id,
            amount = amount,
            reason = reason
        )
    }

    fun toEntity(): UserPointHistory {
        return dummyUserPointHistory
    }
}
