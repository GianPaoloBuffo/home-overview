package com.buffo.gp.repository

import com.buffo.gp.domain.sensor.Sensor
import org.springframework.data.repository.CrudRepository

interface SensorRepository : CrudRepository<Sensor<*>, Long>

