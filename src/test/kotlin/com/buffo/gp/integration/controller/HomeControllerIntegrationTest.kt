package com.buffo.gp.integration.controller

import com.buffo.gp.domain.Home
import com.buffo.gp.model.HomeResponse
import com.buffo.gp.repository.HomeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HomeControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var homeRepository: HomeRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `show existing home should return 200 OK`() {
        // given
        val home = homeRepository.save(Home())
        val roomId = home.rooms.firstOrNull()?.id

        // when-then
        val response = mockMvc.perform(get("/api/homes/${home.id}"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val homeResponse = objectMapper.readValue(response, HomeResponse::class.java)

        assertNotNull(homeResponse)
        assertEquals(home.id, homeResponse.id)
        assertEquals(home.rooms.size, homeResponse.rooms.size)
        assertEquals(roomId, homeResponse.rooms.firstOrNull()?.id)
    }

    @Test
    fun `show non-existing home should return 404 Not Found`() {
        // given
        val nonExistingId = Long.MAX_VALUE

        // when-then
        mockMvc.perform(get("/api/homes/$nonExistingId"))
            .andExpect(status().isNotFound)
    }
}
