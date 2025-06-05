package com.diary.api.users.points

import com.diary.api.controller.v1.admin.users.AdminUserPointController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.service.users.UserPointService
import com.diary.api.users.DummyUser
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.points.UserPoint
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
@DisplayName("관리자 회원 포인트 컨트롤러 단위 테스트")
class AdminUserPointControllerTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyUserPoint: UserPoint = DummyUserPoint.toEntity()

    @Mock
    private lateinit var service: UserPointService

    @InjectMocks
    private lateinit var controller: AdminUserPointController

    @Test
    fun `회원 포인트 지급`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyUserPointId: ID = dummyUserPoint.id
        val request: AddUserPointRequestDto = DummyUserPoint.toAddUserPointRequestDto()
        `when`(
            service.addPoint(
                userId = dummyUserId,
                request = request
            )
        ).thenReturn(dummyUserPointId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.addPoint(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserPointId)
    }

    @Test
    fun `회원 포인트 차감`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyUserPointId: ID = dummyUserPoint.id
        val request: SubUserPointRequestDto = DummyUserPoint.toSubUserPointRequestDto()
        `when`(
            service.subPoint(
                userId = dummyUserId,
                request = request
            )
        ).thenReturn(dummyUserPointId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.subPoint(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserPointId)
    }
}
