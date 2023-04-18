package com.buffo.gp.service

import com.buffo.gp.domain.Home
import com.buffo.gp.domain.Room
import com.buffo.gp.domain.sensor.Sensor
import com.buffo.gp.extension.toEntity
import com.buffo.gp.model.SensorReadingResponse
import com.buffo.gp.model.SensorRequest
import com.buffo.gp.repository.HomeRepository
import com.buffo.gp.repository.SensorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SensorService(
    private val homeRepository: HomeRepository,
    private val sensorRepository: SensorRepository
) {

    fun generateReadings(homeId: Long): List<SensorReadingResponse> =
        homeRepository.findByIdOrNull(homeId)
            ?.let { generateReadings(it) }
            ?: emptyList()

    private fun generateReadings(home: Home) =
        home.rooms.flatMap { generateReadings(it) }

    private fun generateReadings(room: Room) =
        room.sensors.map { generateReading(it) }

    private fun <T> generateReading(sensor: Sensor<T>) =
        SensorReadingResponse(sensor.id, sensor.generateReading())

    fun create(request: SensorRequest): Sensor<*> =
        sensorRepository.save(request.toEntity())

    fun delete(id: Long) =
        sensorRepository.deleteById(id)
}
