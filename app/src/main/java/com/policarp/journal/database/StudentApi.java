package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.StudentEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StudentApi {
    @GET("/students/participant/{id}")
    Call<StudentEntity> getStudentByParticipantId(@Path("id") Long participantId);
}
