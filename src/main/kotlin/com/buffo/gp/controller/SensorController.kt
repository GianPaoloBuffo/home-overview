package com.buffo.gp.controller

import com.buffo.gp.extension.toLocationUri
import com.buffo.gp.extension.toResponse
import com.buffo.gp.model.SensorRequest
import com.buffo.gp.model.SensorResponse
import com.buffo.gp.service.SensorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sensors")
class SensorController(private val sensorService: SensorService) {

    @PostMapping
    fun create(@RequestBody request: SensorRequest): ResponseEntity<SensorResponse> =
        sensorService.create(request)
            .toResponse()
            .let { ResponseEntity.created(it.toLocationUri()).body(it) }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> =
        sensorService.delete(id)
            .let { ResponseEntity.noContent().build() }
}
