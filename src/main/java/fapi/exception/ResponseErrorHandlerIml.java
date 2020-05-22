package fapi.exception;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;


import java.io.IOException;
import java.io.InputStreamReader;


@Component
public class ResponseErrorHandlerIml implements ResponseErrorHandler {
    private MappingJackson2CborHttpMessageConverter messageConverter;


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String message;
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(new InputStreamReader(response.getBody()));
            message = object.get("message").toString();
        } catch (ParseException e) {
            throw new ApiRequestException("");
        }
        throw new ApiRequestException(message);
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().equals(HttpStatus.OK)){
            return false;
        }
        else {
            return true;
        }
    }
}