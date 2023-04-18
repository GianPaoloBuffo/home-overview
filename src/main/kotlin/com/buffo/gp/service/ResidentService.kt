package com.buffo.gp.service

import com.buffo.gp.domain.Resident
import com.buffo.gp.extension.toEntity
import com.buffo.gp.extension.update
import com.buffo.gp.model.ResidentRequest
import com.buffo.gp.repository.ResidentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ResidentService(private val residentRepository: ResidentRepository) {

    fun create(request: ResidentRequest): Resident =
        residentRepository.save(request.toEntity())

    fun delete(id: Long) =
        residentRepository.deleteById(id)

    fun update(id: Long, request: ResidentRequest): Resident? =
        residentRepository.findByIdOrNull(id)?.update(request)
}
