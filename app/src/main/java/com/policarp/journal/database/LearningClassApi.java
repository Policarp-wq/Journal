package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.LearningClassEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LearningClassApi {
    @GET("/classes")
    Call<List<LearningClassEntity>> getAllClasses();
    @GET("/classes/{id}")
    Call<LearningClassEntity> getClassById(@Path("id") Long classId);
    @GET("/classes/school/{school_id}")
    Call<List<LearningClassEntity>> getSchoolClasses(@Path("school_id") Long schoolId);
    @GET("classes/school/{id}/name/{name}")
    Call<LearningClassEntity> getClassInSchoolByName(@Path("id") Long schoolId, @Path("name") String className);
    @GET("/classes/{class_id}/participants")
    Call<List<SchoolParticipantEntity>> getClassParticipants(@Path("class_id") Long classId);
    @POST("/classes")
    Call<LearningClassEntity> addClass(@Body LearningClassEntity learningClass);
    @DELETE("/classes")
    Call<Integer> deleteClass(@Body LearningClassEntity learningClass);
}
