package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/users")
    Call<List<UserEntity>> getAllUsers();
    @GET("/users/{id}")
    Call<UserEntity> getUserById(@Path("id") Long id);
    @GET("/users/login/{login}")
    Call<UserEntity> getUserByLogin(@Path("login") String login);
    @GET("/users/login/{login}/hash/{hash}")
    Call<UserEntity> getUserByLoginAndHash(@Path("login") String login, @Path("hash") Integer hash);
    @POST("/users")
    Call<UserEntity> addUser(@Body UserEntity user);
    @POST("/users/auth")
    Call<SchoolParticipantEntity> authUser(@Body UserEntity user);
    @PUT("/users")
    Call<UserEntity> updateUser(@Body UserEntity user);
    @DELETE("/users/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

}
