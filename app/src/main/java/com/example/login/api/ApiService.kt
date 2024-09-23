package com.example.login.api

import com.example.login.Comment
import com.example.login.model.KeywordDto
import com.example.login.model.KeywordProductDto
import com.example.login.model.LoginRequestDto
import com.example.login.model.LoginResponseDto
import com.example.login.model.RegisterResponseDto
import com.example.login.model.RegisterRequestDto
import com.example.login.model.LogoutResponseDto
import com.example.login.model.Post
import com.example.login.model.WishlistDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    // 사용자의 wishlist 조회
    @GET("wishlist/user")
    fun getCurrentUserWishlist(): Call<List<WishlistDto>>

    // wishlist 항목 추가
    @POST("wishlist/add")
    fun addWishlistItem(@Query("productId") productId: Long): Call<String>

    // wishlist 항목 삭제
    @DELETE("wishlist/remove/{wishlistId}")
    fun removeWishlistItem(@Path("wishlistId") wishlistId: Long): Call<String>

    // 키워드 추가
    @POST("keywords/add")
    fun addKeyword(@Body keywordDto: KeywordDto): Call<KeywordDto>

    // 키워드 수정
    @PUT("keywords/{id}")
    fun updateKeyword(@Path("id") id: Long, @Body keywordDto: KeywordDto): Call<KeywordDto>

    // 키워드 삭제
    @DELETE("keywords/remove")
    fun deleteKeyword(@Query("keywordId") keywordId: Long): Call<String>

    // 사용자의 키워드 목록 조회
    @GET("keywords/user")
    fun getUserKeywords(): Call<List<KeywordDto>>
    //fun getUserKeywords(@Query("userId") userId: Long): Call<List<KeywordDto>>

    // 키워드 포함한 상품 목록 조회
    @GET("keywords/")
    fun getProductsByKeyword(@Query("keyword") keyword: String): Call<List<KeywordProductDto>>

    // 사용자의 키워드별 상품 목록 조회
    @GET("keywords/products")
    fun getProductsByUserKeywords(): Call<Map<String, List<KeywordProductDto>>>



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

