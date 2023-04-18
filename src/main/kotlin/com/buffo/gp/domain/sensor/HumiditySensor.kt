package com.buffo.gp.domain.sensor

import com.buffo.gp.domain.Room
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import kotlin.random.Random

@Entity
class HumiditySensor(room: Room?) : Sensor<Int>(room) {

    @ElementCollection
    @Column(name = "reading")
    override var readings: MutableList<Int> = mutableListOf()

    override fun generateReading(): Int {
        val min = 50
        val max = 55

        with(Random.nextInt(min, max)) {
            readings.add(this)
            return this
        }
    }

    override fun getType(): SensorType = SensorType.HUMIDITY
}
