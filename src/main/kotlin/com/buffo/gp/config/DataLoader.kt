package com.buffo.gp.config

import com.buffo.gp.domain.Home
import com.buffo.gp.domain.Resident
import com.buffo.gp.domain.Room
import com.buffo.gp.domain.sensor.HumiditySensor
import com.buffo.gp.domain.sensor.TemperatureSensor
import com.buffo.gp.repository.HomeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val homeRepository: HomeRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val home = Home()

        val john = Resident("John")
        val jane = Resident("Jane")

        val livingRoom = Room(name = "Living room", home = home)
        livingRoom.sensors.addAll(setOf(HumiditySensor(livingRoom), TemperatureSensor(livingRoom)))
        livingRoom.residents.add(john)

        val bedroom = Room(name = "Bedroom", home = home)
        bedroom.sensors.addAll(setOf(HumiditySensor(bedroom), TemperatureSensor(bedroom)))
        bedroom.residents.add(jane)

        home.rooms.addAll(setOf(livingRoom, bedroom))

        homeRepository.save(home)
    }
}
