package com.buffo.gp.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.PreRemove

@Entity
class Resident(

    @Column(unique = true, nullable = false)
    var name: String,

    @ManyToMany(mappedBy = "residents", targetEntity = Room::class)
    var rooms: MutableSet<Room> = mutableSetOf()
): Persistable() {

    @PreRemove
    fun unlinkResident() {
        rooms.forEach { it.residents.remove(this) }
    }
}
