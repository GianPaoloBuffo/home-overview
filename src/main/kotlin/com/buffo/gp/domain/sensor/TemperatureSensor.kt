package com.buffo.gp.domain.sensor

import com.buffo.gp.domain.Room
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import kotlin.random.Random

@Entity
class TemperatureSensor(room: Room?) : Sensor<Double>(room) {

    @ElementCollection
    @Column(name = "reading")
    override var readings: MutableList<Double> = mutableListOf()

    override fun generateReading(): Double {
        val min = 18.5
        val max = 19.5

        with(String.format("%.1f", Random.nextDouble(min, max)).toDouble()) {
            readings.add(this)
            return this
        }
    }

    override fun getType(): SensorType = SensorType.TEMPERATURE
}
