package com.buffo.gp.extension

import com.buffo.gp.domain.Resident
import com.buffo.gp.domain.Room
import com.buffo.gp.model.ResidentRequest
import com.buffo.gp.model.ResidentResponse
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

fun Resident.toResponse(): ResidentResponse =
    ResidentResponse(id, name)

fun Collection<ResidentRequest>.toEntity(room: Room): MutableSet<Resident> =
    map { it.toEntity(room) }.toMutableSet()

fun ResidentRequest.toEntity(room: Room? = null): Resident {
    if (name == null)
        throw IllegalArgumentException("name cannot be blank")

    val resident = Resident(name)

    if (room != null)
        resident.rooms.add(room)

    return resident
}

fun Resident.update(request: ResidentRequest): Resident {
    if (request.name != null)
        name = request.name

    return this
}

fun ResidentResponse.toLocationUri(): URI =
    UriComponentsBuilder.fromPath("/api/residents")
        .buildAndExpand(id)
        .toUri()
