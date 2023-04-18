package com.buffo.gp.config

import com.buffo.gp.service.SensorService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class BackgroundScheduler(
    private val messagingTemplate: SimpMessagingTemplate,
    private val sensorService: SensorService
) {

    @Scheduled(initialDelay = 5000, fixedDelay = 3000)
    fun run() {
        val readings = sensorService.generateReadings(HOME_ID)
        messagingTemplate.convertAndSend("/topic/readings", readings)
    }

    companion object {
        private const val HOME_ID: Long = 1
    }
}
