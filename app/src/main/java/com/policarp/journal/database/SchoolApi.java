package com.policarp.journal.database;

import com.policarp.journal.database.response.entities.SchoolEntity;
import com.policarp.journal.models.School;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SchoolApi {
    @GET("schools")
    Call<List<SchoolEntity>> getAllSchools();
    @GET("schools/{id}")
    Call<SchoolEntity> getSchoolById(@Path("id") Long id);
    @GET("schools/name/{name}")
    Call<SchoolEntity> getSchoolByName(@Path("name") String name);
    @POST("schools")
    Call<SchoolEntity> addSchool(@Body SchoolEntity school);
    @DELETE("schools/{id}")
    void deleteSchoolById(@Path("id") Long id);
    @DELETE("schools/name/{name}")
    void deleteSchoolByName(@Path("name") String name);
}
