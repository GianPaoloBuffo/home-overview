package com.buffo.gp.controller

import com.buffo.gp.domain.sensor.SensorType
import com.buffo.gp.extension.toLocationUri
import com.buffo.gp.extension.toResponse
import com.buffo.gp.model.HomeRequest
import com.buffo.gp.model.HomeResponse
import com.buffo.gp.model.RoomRequest
import com.buffo.gp.model.RoomResponse
import com.buffo.gp.service.HomeService
import com.buffo.gp.service.RoomService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/homes")
class HomeController(private val homeService: HomeService, private val roomService: RoomService) {

    @PostMapping
    fun create(@RequestBody @Valid request: HomeRequest): ResponseEntity<HomeResponse> =
        homeService.create(request)
            .toResponse()
            .let { ResponseEntity.created(it.toLocationUri()).body(it) }

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long): ResponseEntity<HomeResponse> =
        homeService.show(id)
            ?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found")

    @RequestMapping("/{id}/rooms")
    fun createRoom(@PathVariable id: Long, @RequestBody @Valid request: RoomRequest): ResponseEntity<RoomResponse> =
        roomService.create(id, request)
            ?.toResponse()
            ?.let { ResponseEntity.created(it.toLocationUri()).body(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found")

    @ExceptionHandler(HttpMessageNotReadableException::class)
    private fun handleInvalidRequest(exception: HttpMessageNotReadableException): ResponseEntity<Any> =
        ResponseEntity.badRequest()
            .body("Invalid request. Make sure the sensor type is one of ${SensorType.values().map { it.toString() }}")
}
