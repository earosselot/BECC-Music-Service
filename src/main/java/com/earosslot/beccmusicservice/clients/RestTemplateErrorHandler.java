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
        return (
          httpResponse.getStatusCode().is4xxClientError()
          || httpResponse.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) {
        throw new ArtistNotFoundException(" Artist Not Found");
    }

}
