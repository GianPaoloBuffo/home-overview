package com.buffo.gp.controller

import com.buffo.gp.extension.toLocationUri
import com.buffo.gp.extension.toResponse
import com.buffo.gp.model.ResidentRequest
import com.buffo.gp.model.ResidentResponse
import com.buffo.gp.service.ResidentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/residents")
class ResidentController(private val residentService: ResidentService) {

    @PostMapping
    fun create(@RequestBody @Valid request: ResidentRequest): ResponseEntity<ResidentResponse> =
        residentService.create(request)
            .toResponse()
            .let { ResponseEntity.created(it.toLocationUri()).body(it) }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid request: ResidentRequest): ResponseEntity<ResidentResponse> =
        residentService.update(id, request)
            ?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Resident not found")

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> =
        residentService.delete(id)
            .let { ResponseEntity.noContent().build() }

}