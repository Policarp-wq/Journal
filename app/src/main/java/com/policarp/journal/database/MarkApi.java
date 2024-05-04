package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.MarkEntity;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MarkApi {
    @GET("/marks/student/{id}")
    Call<List<MarkEntity>> getStudentsMarks(@Path("id") Long studentId);
//    @GET("/marks/{id}")
//    @GET("/marks/val/{val}")
//    @GET("/marks/teacher/{id}")
    @GET("/marks/student/{id}/name/{name}")
    Call<List<MarkEntity>> getMarksByStudentAndSubject(@Path("id") Long studentId, @Path("name") String subjectName);
    @GET("/marks/student/{id}/name/{name}/date/{date}")
    Call<List<MarkEntity>> getMarksByStudentAndSubjectAndDate(@Path("id") Long studentId, @Path("name") String subjectName, @Path("date")Date date);
    @POST("/marks")
    Call<MarkEntity> addMark(@Body MarkEntity mark);
}
