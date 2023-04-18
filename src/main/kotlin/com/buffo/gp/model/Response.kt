package com.buffo.gp.model

import com.buffo.gp.domain.sensor.SensorType

data class HomeResponse(val id: Long?, val rooms: List<RoomResponse> = emptyList())

data class RoomResponse(val id: Long?, val name: String, val sensors: List<SensorResponse> = emptyList())

data class SensorResponse(val id: Long?, val type: SensorType)

data class ResidentResponse(val id: Long?, val name: String)

data class SensorReadingResponse(val sensorId: Long?, val reading: Any?)
