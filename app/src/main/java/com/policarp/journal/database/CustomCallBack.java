package com.policarp.journal.database;

import android.util.Log;

import com.policarp.journal.models.JSONable;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomCallBack<T> implements Callback<T> {
    public CustomCallBack(Success<T> success, Bad<T> bad, Fail<T> fail) {
        this.success = success;
        this.bad = bad;
        this.fail = fail;
    }

    public Success<T> success;
    public Bad<T> bad;
    public Fail<T> fail;

    public interface Success<T>{
        void onSuccessfulResponse(Call<T> call, Response<T> response);
    }
    public interface Bad<T>{
        void onBadResponse(Call<T> call, Response<T> response);
    }
    public interface Fail<T>{
        void onFailResponse(Call<T> call, Throwable throwable);
    }
    @SneakyThrows
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            if(success != null)
                success.onSuccessfulResponse(call, response);
            Log.i(ServerAPI.REQUESTTAG, "Successful response " + call.request().url());
        }else{
            if(bad != null)
                bad.onBadResponse(call, response);
            if(response.errorBody() != null){
                RequestError error = ((RequestError) JSONable.fromJSON(response.errorBody().string(), RequestError.class));
                if(error != null)
                    Log.e(ServerAPI.REQUESTTAG, "Bad response with code " + error.getStatus() + error.getMessage());
            }

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        if(fail != null)
            fail.onFailResponse(call, throwable);
        Log.e(ServerAPI.REQUESTTAG, throwable.getMessage());
    }
}
