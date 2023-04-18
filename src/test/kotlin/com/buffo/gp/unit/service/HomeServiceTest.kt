package com.buffo.gp.unit.service

import com.buffo.gp.domain.Home
import com.buffo.gp.domain.Room
import com.buffo.gp.model.HomeRequest
import com.buffo.gp.model.RoomRequest
import com.buffo.gp.repository.HomeRepository
import com.buffo.gp.service.HomeService
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class HomeServiceTest {

    private val homeRepository = mockk<HomeRepository>()
    private val homeService = HomeService(homeRepository)

    @BeforeEach
    fun setUp() {
        clearMocks(homeRepository)
    }

    @Test
    fun `create should call homeRepository save`() {
        // given
        val request = spyk(HomeRequest(listOf(RoomRequest("Living room"))))

        val home = Home()
        val room = Room("Living room", home)
        home.rooms.add(room)

        every { homeRepository.save(any()) } returns home

        // when
        val result = homeService.create(request)

        // then
        verify { homeRepository.save(any()) }
        assertEquals(home, result)
    }

    @Test
    fun `show should call homeRepository findByIdOrNull`() {
        // given
        val home = Home()
        home.id = 1L
        val room = Room("Living room", home)
        home.rooms.add(room)

        every { homeRepository.findByIdOrNull(1L) } returns home

        // when
        val result = homeService.show(1L)

        // then
        verify { homeRepository.findByIdOrNull(1L) }
        assertEquals(home, result)
    }
}
