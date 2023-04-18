package com.buffo.gp.repository

import com.buffo.gp.domain.Room
import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, Long>
