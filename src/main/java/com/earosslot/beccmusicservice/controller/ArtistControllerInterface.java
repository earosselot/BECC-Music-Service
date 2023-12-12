package com.earosslot.beccmusicservice.controller;

import com.earosslot.beccmusicservice.entity.Artist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Artist")
public interface ArtistControllerInterface {
    
    @Operation(
            description = "Obtain Artist details given an MBID",
            summary = "Artist metadata and albums plus links to the covers",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Artist not found for provided MBID",
                            responseCode = "400",
                            content = @Content
                    )

            }
    )
    ResponseEntity<Artist> getDetails(@PathVariable String mbid);
}
