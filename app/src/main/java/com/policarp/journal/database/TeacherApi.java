package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.TeacherEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TeacherApi {
    @GET("/teachers/participant/{participant_id}")
    Call<TeacherEntity> getTeacherByParticipantId(@Path("participant_id") Long participantId);
}
