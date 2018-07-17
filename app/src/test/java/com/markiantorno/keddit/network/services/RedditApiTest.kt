package com.markiantorno.keddit.network.services

import com.google.gson.Gson
import com.markiantorno.keddit.RestServiceMockUtils
import com.markiantorno.keddit.RxImmediateSchedulerRule
import com.markiantorno.keddit.network.model.RedditNewsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class RedditApiTest {

    companion object {
        @ClassRule @JvmField
        val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()
    }

    private var top10PostRawJson: String? = null
    private var top10Post: RedditNewsResponse? = null

    private var server: MockWebServer? = null
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var redditApi: RedditService? = null


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
                return when (endpoint) {
                    "top.json?after=&limit=10" -> if ("GET" == methodHTTP) {
                        MockResponse()
                                .setResponseCode(HttpURLConnection.HTTP_OK)
                                .addHeader("Content-Type", "application/json; charset=utf-8")
                                .setBody(top10PostRawJson)
                    } else {
                        MockResponse().setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
                    }
                    else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                }
            }
        }

        server!!.setDispatcher(dispatcher)
        val baseUrl = server!!.url("")//"https://www.reddit.com")

        okHttpClient = OkHttpClient.Builder()
                .readTimeout(RestServiceMockUtils.CONNECTION_TIMEOUT_SHORT, TimeUnit.SECONDS)
                .connectTimeout(RestServiceMockUtils.CONNECTION_TIMEOUT_SHORT, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl.toString())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        redditApi = retrofit!!.create(RedditService::class.java)
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

        redditApi!!.getTop("","10")
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe({ t: RedditNewsResponse? ->
                    Assert.assertNotNull(t)
                    System.out.println(t!!.toString())
                    latch.countDown()
                }, {t ->
                    TestCase.fail("getTop !isSuccessful : " + t.localizedMessage)
                })

        Assert.assertTrue(latch.await(RestServiceMockUtils.CONNECTION_TIMEOUT_MED*10000, TimeUnit.SECONDS))
    }

}

