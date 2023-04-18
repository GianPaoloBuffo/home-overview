package com.buffo.gp.repository

import com.buffo.gp.domain.Resident
import org.springframework.data.repository.CrudRepository

interface ResidentRepository : CrudRepository<Resident, Long>
