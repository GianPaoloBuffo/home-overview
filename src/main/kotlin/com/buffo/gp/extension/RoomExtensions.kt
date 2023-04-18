package com.buffo.gp.extension

import com.buffo.gp.domain.Home
import com.buffo.gp.domain.Room
import com.buffo.gp.model.RoomRequest
import com.buffo.gp.model.RoomResponse
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

fun Collection<Room>.toResponse(): List<RoomResponse> =
    map { it.toResponse() }

fun Room.toResponse(): RoomResponse =
    RoomResponse(id, name, sensors.toResponse())

fun Collection<RoomRequest>.toEntity(home: Home): MutableSet<Room> =
    map { it.toEntity(home) }.toMutableSet()

fun RoomRequest.toEntity(home: Home): Room {
    if (name == null)
        throw IllegalArgumentException("name cannot be blank")

    val room = Room(name, home)
    room.sensors.addAll(sensors.toEntity(room))
    room.residents.addAll(residents.toEntity(room))

    return room
}

fun Room.update(request: RoomRequest): Room {
    if (request.name != null)
        name = request.name

    if (request.sensors.isNotEmpty())
        sensors = request.sensors.toEntity(this)

    if (request.residents.isNotEmpty())
        residents = request.residents.toEntity(this)

    return this
}

fun RoomResponse.toLocationUri(): URI =
    UriComponentsBuilder.fromPath("/api/homes/{homeId}/rooms")
        .buildAndExpand(id)
        .toUri()
