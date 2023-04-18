package com.buffo.gp.model

import com.buffo.gp.domain.sensor.SensorType
import jakarta.validation.constraints.Size

data class HomeRequest(val rooms: List<RoomRequest> = emptyList())

data class RoomRequest(
    @field:Size(min = 3, max = 50) val name: String?,
    val sensors: List<SensorRequest> = emptyList(),
    val residents: List<ResidentRequest> = emptyList()
)

data class SensorRequest(val id: Long?, val type: SensorType)

data class ResidentRequest(
    val id: Long?,
    @field:Size(min = 3, max = 50) val name: String?
)
