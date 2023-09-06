package com.sumit.rxjavademo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.jakewharton.rxbinding4.view.clicks
import com.sumit.rxjavademo.model.ProductItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn =  findViewById<Button>(R.id.Button)
        btn.clicks()
            .throttleFirst(1500,TimeUnit.MILLISECONDS)
            .subscribe{
                Log.d("Sumit RxJAva", "Button Clicked")
            }
        callAPI()
//        simpleObserver()
//        createObserver()
    }
    private fun callAPI() {
        val api = RetrofitServices.retrofit.create(Api::class.java)
        api.getProductList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{
//                Log.d("Data...", it.toString())
//            }
            .subscribe(this::handleResponse, this::handleError)
    }
        private fun handleResponse(data: List<ProductItem>){
                Log.d("OnSuccess.....", data.toString())
        }
        private fun handleError(error: Throwable) {
            Log.d("OnError.....", error.localizedMessage!!)
            Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
        }


    private fun simpleObserver(){
        val list = listOf("A","B","C","D")
        val observable =  Observable.fromIterable(list)

        observable.subscribe(object :Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d("Sumit RxJAva","onSubscribe is Called")
            }

            override fun onNext(t: String) {
                Log.d("Sumit RxJAva","onNext is Called")
            }

            override fun onError(e: Throwable) {
                Log.d("Sumit RxJAva","onError is Called")
            }

            override fun onComplete() {
                Log.d("Sumit RxJAva","onComplete is Called")
            }

        })
    }
    private fun createObserver(){
        val observable = Observable.create<String>{
            it.onNext("A")
            it.onNext("B")
//            it.onError(IllegalArgumentException("Error in Observable"))
            it.onNext("C")
            it.onComplete()
        }
        observable.subscribe(object :Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d("Sumit RxJAva","onSubscribe is Called")
            }

            override fun onNext(t: String) {
                Log.d("Sumit RxJAva","onNext is Called")
            }

            override fun onError(e: Throwable) {
                Log.d("Sumit RxJAva","onError is Called")
            }

            override fun onComplete() {
                    Log.d("Sumit RxJAva","onComplete is Called")
            }

        })
    }

}