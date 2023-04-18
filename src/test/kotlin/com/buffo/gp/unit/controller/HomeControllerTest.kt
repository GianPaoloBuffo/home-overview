package com.buffo.gp.unit.controller

import com.buffo.gp.controller.HomeController
import com.buffo.gp.domain.Home
import com.buffo.gp.domain.Room
import com.buffo.gp.model.HomeRequest
import com.buffo.gp.model.HomeResponse
import com.buffo.gp.model.RoomRequest
import com.buffo.gp.model.RoomResponse
import com.buffo.gp.service.HomeService
import com.buffo.gp.service.RoomService
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class HomeControllerTest {

    private val homeService = mockk<HomeService>()
    private val roomService = mockk<RoomService>()
    private val controller = HomeController(homeService, roomService)

    @BeforeEach
    fun setUp() {
        clearMocks(homeService, roomService)
    }

    @Test
    fun `create should return HTTP 201 and created home when valid request`() {
        // given
        val homeRequest = HomeRequest(
            listOf(
                RoomRequest("Room 1"),
                RoomRequest("Room 2")
            )
        )

        val home = Home()
        home.id = 1L
        val room1 = Room("Room 1", home)
        room1.id = 1
        val room2 = Room("Room 2", home)
        room2.id = 2
        home.rooms.addAll(mutableSetOf(room1, room2))

        val homeResponse = HomeResponse(
            1L, listOf(
                RoomResponse(1L, "Room 1"),
                RoomResponse(2L, "Room 2")
            )
        )

        every { homeService.create(homeRequest) } returns home

        // when
        val response = controller.create(homeRequest)

        // then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(homeResponse, response.body)
        assertEquals("/api/homes/1", response.headers.location.toString())
    }

    @Test
    fun `show should return HTTP 200 and home when valid ID`() {
        // given
        val homeId = 1L
        val homeResponse = HomeResponse(
            homeId, listOf(
                RoomResponse(1L, "Room 1"),
                RoomResponse(2L, "Room 2")
            )
        )

        val home = Home()
        home.id = 1L
        val room1 = Room("Room 1", home)
        room1.id = 1
        val room2 = Room("Room 2", home)
        room2.id = 2
        home.rooms.addAll(mutableSetOf(room1, room2))

        every { homeService.show(homeId) } returns home

        // when
        val response = controller.show(homeId)

        // then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(homeResponse, response.body)
    }

    @Test
    fun `show should throw HTTP 404 when invalid ID`() {
        // given
        val homeId = 1L

        every { homeService.show(homeId) } returns null

        // when-then
        assertThrows<ResponseStatusException> {
            controller.show(homeId)
        }.also { exception ->
            assertEquals(HttpStatus.NOT_FOUND, exception.statusCode)
            assertEquals("Home not found", exception.reason)
        }
    }

    @Test
    fun `createRoom should return HTTP 201 and created room when valid request`() {
        // given
        val home = Home()
        val homeId = 1L
        home.id = homeId

        val roomRequest = RoomRequest("Room 1")
        val room = Room("Room 1", home)
        room.id = 1

        val roomResponse = RoomResponse(1L, "Room 1")

        every { roomService.create(homeId, roomRequest) } returns room

        // when
        val response = controller.createRoom(homeId, roomRequest)

        // then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(roomResponse, response.body)
        assertEquals("/api/homes/$homeId/rooms", response.headers.location.toString())
    }
}
