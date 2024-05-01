package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.SchoolParticipantEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SchoolParticipantApi {
    @GET("/schoolParticipants")
    Call<List<SchoolParticipantEntity>> getAll();
    @GET("/schoolParticipants/{id}")
    Call<SchoolParticipantEntity> getById(@Path("id") Long id);
    @GET("/schoolParticipants/school/{id}")
    Call<List<SchoolParticipantEntity>> getSchoolIdParticipants(@Path("id") Long id);
    @GET("/schoolParticipants/position/{pos}")
    Call<List<SchoolParticipantEntity>> getAllByPosition(@Path("pos") String pos);
    @GET("/schoolParticipants/name/{name}")
    Call<List<SchoolParticipantEntity>> getAllByName(@Path("name") String name);
    @POST("/schoolParticipants")
    Call<SchoolParticipantEntity> add(@Body SchoolParticipantEntity participant);
    @POST("/schoolParticipants/{user_id}")
    Call<SchoolParticipantEntity> add(@Path("user_id") Long user_id, @Body SchoolParticipantEntity participant);
    @DELETE("/schoolParticipants")
    Call<Void> delete(@Body SchoolParticipantEntity participant);
    @DELETE("/schoolParticipants/{id}")
    Call<Void> delete(@Path("id") Long id);
}
