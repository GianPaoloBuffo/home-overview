package com.buffo.gp.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class Persistable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}
