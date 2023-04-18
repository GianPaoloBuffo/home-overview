package com.buffo.gp.repository

import com.buffo.gp.domain.Home
import org.springframework.data.repository.CrudRepository

interface HomeRepository : CrudRepository<Home, Long>
