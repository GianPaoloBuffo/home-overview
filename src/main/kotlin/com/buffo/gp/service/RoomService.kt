package com.buffo.gp.service

import com.buffo.gp.domain.Room
import com.buffo.gp.extension.toEntity
import com.buffo.gp.extension.update
import com.buffo.gp.model.RoomRequest
import com.buffo.gp.repository.HomeRepository
import com.buffo.gp.repository.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RoomService(
    private val homeRepository: HomeRepository,
    private val roomRepository: RoomRepository
) {

    fun create(homeId: Long, request: RoomRequest): Room? =
        homeRepository.findByIdOrNull(homeId)
            ?.let { request.toEntity(it) }
            ?.let { roomRepository.save(it) }

    fun show(id: Long): Room? =
        roomRepository.findByIdOrNull(id)

    fun update(id: Long, request: RoomRequest): Room? =
        roomRepository.findByIdOrNull(id)?.update(request)

    fun delete(id: Long) =
        roomRepository.deleteById(id)
}
