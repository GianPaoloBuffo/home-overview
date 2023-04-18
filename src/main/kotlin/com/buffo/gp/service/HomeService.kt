package com.buffo.gp.service

import com.buffo.gp.domain.Home
import com.buffo.gp.extension.toEntity
import com.buffo.gp.model.HomeRequest
import com.buffo.gp.repository.HomeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class HomeService(private val homeRepository: HomeRepository) {

    fun create(request: HomeRequest): Home =
        homeRepository.save(request.toEntity())

    fun show(id: Long): Home? =
        homeRepository.findByIdOrNull(id)
}
