package com.buffo.gp.domain

import com.buffo.gp.domain.sensor.Sensor
import jakarta.persistence.*

@Entity
class Room(

    @Column(unique = true, nullable = false)
    var name: String,

    @ManyToOne
    var home: Home,

    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST],
        mappedBy = "room",
        targetEntity = Sensor::class
    )
    var sensors: MutableSet<Sensor<*>> = mutableSetOf(),

    @ManyToMany(targetEntity = Resident::class, cascade = [CascadeType.PERSIST])
    @JoinTable(joinColumns = [JoinColumn(name = "room_id")], inverseJoinColumns = [JoinColumn(name = "resident_id")])
    var residents: MutableSet<Resident> = mutableSetOf(),
) : Persistable() {

    @PreRemove
    fun unlinkRoom() {
        home.rooms.remove(this)
        residents.forEach { it.rooms.remove(this) }
        sensors.forEach { it.room = null }
    }
}
