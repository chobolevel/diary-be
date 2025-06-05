package com.diary.domain.entity.users.points

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface UserPointRepository : JpaRepository<UserPoint, ID>
