package com.example.login.api

import com.example.login.Comment
import com.example.login.model.LoginRequestDto
import com.example.login.model.LoginResponseDto
import com.example.login.model.RegisterResponseDto
import com.example.login.model.RegisterRequestDto
import com.example.login.model.LogoutResponseDto
import com.example.login.model.Post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // 로그인
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequestDto): Call<LoginResponseDto>

    // 로그아웃
    @POST("auth/logout")
    fun logout(): Call<LogoutResponseDto>

    // 사용자 조회
    @GET("users/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<RegisterResponseDto>

    // 회원가입
    @POST("users/register")
    fun registerUser(@Body registerRequest: RegisterRequestDto): Call<RegisterResponseDto>


    // 기존

    @GET("getPosts")
    fun getPosts(@Query("community_id") communityId: Int): Call<List<Post>>

    // 댓글 가져오기
    @GET("posts/{postId}/comments")
    fun getComments(@Path("postId") postId: Int): Call<List<Comment>>

    // 댓글 추가하기
    @POST("posts/{postId}/comments")
    fun addComment(@Path("postId") postId: Int, @Body comment: Comment): Call<Void>
}

