package com.example.login.api

import com.example.login.Comment
import com.example.login.model.LoginRequest
import com.example.login.model.LoginResponse
import com.example.login.Post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("getPosts")
    fun getPosts(@Query("community_id") communityId: Int): Call<List<Post>>

    // 댓글 가져오기
    @GET("posts/{postId}/comments")
    fun getComments(@Path("postId") postId: Int): Call<List<Comment>>

    // 댓글 추가하기
    @POST("posts/{postId}/comments")
    fun addComment(@Path("postId") postId: Int, @Body comment: Comment): Call<Void>
}

