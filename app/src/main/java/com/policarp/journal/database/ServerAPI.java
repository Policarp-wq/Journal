package com.policarp.journal.database;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAPI {
    public static final String REQUESTTAG = "REQUEST";
    public static final String BASEURL = "http://192.168.3.5:8080";
    //public static final String BASEURL = "http://192.168.177.179:8080";
    private static ServerAPI server;
    @Getter
    private final SchoolApi schoolApi;
    @Getter
    private final UserApi userApi;
    @Getter
    private final StudentApi studentApi;
    @Getter
    private final SchoolParticipantApi schoolParticipantApi;
    @Getter
    private final MarkApi markApi;
    @Getter
    private final LearningClassApi learningClassApi;
    @Getter
    private final TeacherApi teacherApi;
    private final Retrofit retrofit;
    private ServerAPI(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        schoolApi = retrofit.create(SchoolApi.class);
        userApi = retrofit.create(UserApi.class);
        schoolParticipantApi = retrofit.create(SchoolParticipantApi.class);
        studentApi = retrofit.create(StudentApi.class);
        markApi = retrofit.create(MarkApi.class);
        learningClassApi = retrofit.create(LearningClassApi.class);
        teacherApi = retrofit.create(TeacherApi.class);
    }
    public static ServerAPI getInstance(){
        if(server == null)
            server = new ServerAPI();
        return server;
    }
}
