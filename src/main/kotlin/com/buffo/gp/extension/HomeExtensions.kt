package com.buffo.gp.extension

import com.buffo.gp.domain.Home
import com.buffo.gp.model.HomeRequest
import com.buffo.gp.model.HomeResponse
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

fun Home.toResponse(): HomeResponse =
    HomeResponse(id, rooms.toResponse())

fun HomeRequest.toEntity(): Home {
    val home = Home()
    home.rooms.addAll(rooms.toEntity(home))
    return home
}

fun HomeResponse.toLocationUri(): URI =
    UriComponentsBuilder.fromPath("/api/homes/{id}")
        .buildAndExpand(id)
        .toUri()
