package com.buffo.gp.extension

import com.buffo.gp.domain.Room
import com.buffo.gp.domain.sensor.HumiditySensor
import com.buffo.gp.domain.sensor.Sensor
import com.buffo.gp.domain.sensor.SensorType
import com.buffo.gp.domain.sensor.TemperatureSensor
import com.buffo.gp.model.SensorRequest
import com.buffo.gp.model.SensorResponse
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

fun Collection<Sensor<*>>.toResponse(): List<SensorResponse> =
    map { it.toResponse() }

fun <T> Sensor<T>.toResponse(): SensorResponse =
    SensorResponse(id, getType())

fun Collection<SensorRequest>.toEntity(room: Room): MutableSet<Sensor<*>> =
    map { it.toEntity(room) }.toMutableSet()

fun SensorRequest.toEntity(room: Room? = null) =
    when (type) {
        SensorType.HUMIDITY -> HumiditySensor(room)
        SensorType.TEMPERATURE -> TemperatureSensor(room)
    }

fun SensorResponse.toLocationUri(): URI =
    UriComponentsBuilder.fromPath("/api/sensors")
        .buildAndExpand(id)
        .toUri()
