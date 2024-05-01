package com.policarp.journal.database;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAPI {
    public static final String REQUESTTAG = "REQUEST";
    public static final String BASEURL = "http://192.168.3.5:8080";
    //public static final String BASEURL = "http://192.168.207.183:8080";
    private static ServerAPI server;
    @Getter
    private final SchoolApi schoolApi;
    @Getter
    private final UserApi userApi;
    @Getter
    private final SchoolParticipantApi schoolParticipantApi;
    private final Retrofit retrofit;
    private ServerAPI(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        schoolApi = retrofit.create(SchoolApi.class);
        userApi = retrofit.create(UserApi.class);
        schoolParticipantApi = retrofit.create(SchoolParticipantApi.class);
    }
    public static ServerAPI getInstance(){
        if(server == null)
            server = new ServerAPI();
        return server;
    }
}
