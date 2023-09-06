package com.sumit.rxjavademo

import com.sumit.rxjavademo.model.ProductItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface Api {
    @GET("/products")
    fun getProductList():Observable<List<ProductItem>>
}