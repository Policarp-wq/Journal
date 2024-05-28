package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.ParticipantStudentEntity;
import com.policarp.journal.database.response.entities.StudentEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentApi {
    @GET("/students/participant/{id}")
    Call<StudentEntity> getStudentByParticipantId(@Path("id") Long participantId);
    @GET("/students/unattached/{school_id}")
    Call<List<ParticipantStudentEntity>> getUnattached(@Path("school_id")Long schoolId);
    @PUT("/students")
    Call<StudentEntity> updateStudent(@Body StudentEntity student);
}
