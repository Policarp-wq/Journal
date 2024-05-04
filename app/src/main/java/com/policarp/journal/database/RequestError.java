package com.policarp.journal.database;

import android.util.Log;

import com.policarp.journal.models.JSONable;

import lombok.*;
import retrofit2.Call;
import retrofit2.Response;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestError {
    private String message;
    private int status;
    public static final int HTTP_NOT_FOUND = 404;
    public RequestError(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public RequestError(RequestError error) {
        this.status = error.status;
        this.message = error.message;
    }
    @SneakyThrows
    public RequestError(Response response){
        if(response.isSuccessful()){
            message = "Response successful!";
            status = 200;
        }
        RequestError error = ((RequestError)JSONable.fromJSON(response.errorBody().string(), RequestError.class));
        message = error.message;
        status = error.status;
    }
    @SneakyThrows
    public static String handleBadResponse(Response response){
        if(response.isSuccessful())
            return "Successful";
        RequestError error = ((RequestError)JSONable.fromJSON(response.errorBody().string(), RequestError.class));
        return error.getMessage();
    }
    public static String handleFailResponse(Call call, Throwable throwable){
        String message = "Fail request " + call.request().url() + "\n with message " + throwable.getMessage();
        return message;
    }
    public boolean isNotFound(){return status == HTTP_NOT_FOUND;}

}
