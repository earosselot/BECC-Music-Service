package com.earosslot.beccmusicservice.clients;

import com.earosslot.beccmusicservice.exeptions.ArtistNotFoundException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().is4xxClientError()) {
            throw new ArtistNotFoundException("Client error getting artist information" + httpResponse.getBody());
        } else if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new ArtistNotFoundException("Server error getting artist information" + httpResponse.getBody());
        }
        throw new ArtistNotFoundException("Unknown error getting artist information" + httpResponse.getBody());
    }

}
