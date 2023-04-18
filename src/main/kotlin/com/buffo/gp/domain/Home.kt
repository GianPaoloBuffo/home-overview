package com.buffo.gp.domain

import jakarta.persistence.*

@Entity
class Home(

    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        mappedBy = "home",
        targetEntity = Room::class
    )
    var rooms: MutableSet<Room> = mutableSetOf()
) : Persistable()
