package com.markiantorno.keddit.network.services

import com.google.gson.Gson
import com.markiantorno.keddit.RestServiceMockUtils
import com.markiantorno.keddit.network.model.RedditDataResponse
import com.markiantorno.keddit.network.model.RedditNewsResponse
import com.squareup.moshi.Moshi
import junit.framework.TestCase
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class RedditApiTest {

    private var top10PostRawJson: String? = null
    private var top10Post: RedditNewsResponse? = null

    private var server: MockWebServer? = null
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var redditApi: RedditApi? = null

    @Before
    fun setUp() {
        val gson = Gson()
        top10PostRawJson = RestServiceMockUtils.getStringFromFile(this.javaClass.classLoader, "top_ten.json")
        top10Post = gson.fromJson<RedditNewsResponse>(top10PostRawJson, RedditNewsResponse::class.java)

        server = MockWebServer()
        server!!.start()

        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                val endpoint = request.path.substring(1)
                val methodHTTP = request.method
                when (endpoint) {
                    "top.json?after=&limit=10" -> return if ("GET" == methodHTTP) {
                        MockResponse()
                                .setResponseCode(HttpURLConnection.HTTP_OK)
                                .addHeader("Content-Type", "application/json; charset=utf-8")
                                .setBody(top10PostRawJson)
                    } else {
                        MockResponse().setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
                    }
                    else -> return MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                }
            }
        }

        server!!.setDispatcher(dispatcher)
        val baseUrl = server!!.url("")//""https://www.reddit.com")

        okHttpClient = OkHttpClient.Builder()
                .readTimeout(RestServiceMockUtils.CONNECTION_TIMEOUT_SHORT, TimeUnit.SECONDS)
                .connectTimeout(RestServiceMockUtils.CONNECTION_TIMEOUT_SHORT, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl.toString())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        redditApi = retrofit!!.create(RedditApi::class.java)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server!!.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun fetch10TopPosts() {
        val latch = CountDownLatch(1)

        val call = redditApi!!.getTop("","10")
        call.enqueue(object : Callback<RedditNewsResponse> {
            override fun onResponse(call: Call<RedditNewsResponse>, response: retrofit2.Response<RedditNewsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Assert.assertNotNull(body)
                    System.out.println(body!!.toString())
                    latch.countDown()
                } else {
                    TestCase.fail("fetchMealContextsById !isSuccessful : " + response.message())
                }
            }

            override fun onFailure(call: Call<RedditNewsResponse>, t: Throwable) {
                TestCase.fail("fetchMealContextsById onFailure : " + t.message)
            }
        })

        Assert.assertTrue(latch.await(RestServiceMockUtils.CONNECTION_TIMEOUT_MED*10000, TimeUnit.SECONDS))
    }

}

