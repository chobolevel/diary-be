package com.diary.domain.entity.users.points

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface UserPointHistoryRepository : JpaRepository<UserPointHistory, ID>
