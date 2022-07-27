package uz.oliymahad.oliymahadquroncourse.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.springframework.http.HttpStatus.OK;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class APIResponse implements Response{
    @JsonProperty("status_code")
    private int statusCode;

    private String message;

    private Object data;

    private String error;

    private Date timestamp;

    private APIResponse() {
    }

    public static APIResponse success(Object data){
        APIResponse response = new APIResponse();
        response.message = OK.name();
        response.statusCode = OK.value();
        response.data = data;
        return response;
    }

    public static APIResponse error(HttpStatus status){
        APIResponse response = new APIResponse();
        response.statusCode = status.value();
        response.error = status.name();
        response.timestamp = new Date();
        return response;
    }

    public static APIResponse error(String message, HttpStatus status){
        APIResponse response = new APIResponse();
        response.message = message;
        response.statusCode = status.value();
        response.error = status.name();
        response.timestamp = new Date();
        return response;
    }

}
