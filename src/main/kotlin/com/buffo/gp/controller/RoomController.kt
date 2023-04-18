package com.buffo.gp.controller

import com.buffo.gp.extension.toResponse
import com.buffo.gp.model.RoomRequest
import com.buffo.gp.model.RoomResponse
import com.buffo.gp.service.RoomService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/rooms")
class RoomController(private val roomService: RoomService) {

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long): ResponseEntity<RoomResponse> =
        roomService.show(id)
            ?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found")

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid request: RoomRequest): ResponseEntity<RoomResponse> =
        roomService.update(id, request)
            ?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found")

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> =
        roomService.delete(id)
            .let { ResponseEntity.noContent().build() }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun handleInvalidRequest(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = exception.bindingResult.fieldErrors.map { it.defaultMessage }
        return ResponseEntity.badRequest().body(errors)
    }
}
