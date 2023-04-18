package com.buffo.gp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HomeOverviewApplication

// todo: nice to have - authentication / authorisation
// todo: nice to have - add non-blocking database (r2dbc, CoroutineCrudRepository, suspending functions)
// todo: nice to have - there is no "control" of the rooms, so you can't set desired temp or humidity
// todo: nice to have - Sensor readings are persisted every 3 seconds. Could buffer this somehow to reduce strain on DB

fun main(args: Array<String>) {
    runApplication<HomeOverviewApplication>(*args)
}
