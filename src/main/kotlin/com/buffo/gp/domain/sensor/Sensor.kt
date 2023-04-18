package com.buffo.gp.domain.sensor

import com.buffo.gp.domain.Persistable
import com.buffo.gp.domain.Room
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Sensor<T>(

    @ManyToOne
    var room: Room?,
) : Persistable() {

    abstract var readings: MutableList<T>

    abstract fun getType(): SensorType

    abstract fun generateReading(): T

    @PreRemove
    fun unlinkSensor() {
        room?.sensors?.remove(this)
    }
}

enum class SensorType {
    HUMIDITY, TEMPERATURE
}
