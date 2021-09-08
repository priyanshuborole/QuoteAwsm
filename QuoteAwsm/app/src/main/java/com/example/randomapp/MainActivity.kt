package com.example.randomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.randomapp.api.QuoteService
import com.example.randomapp.api.RetrofitHelper
import com.example.randomapp.models.QuoteList
import com.example.randomapp.repository.QuoteRepository
import com.example.randomapp.viewmodels.MainViewModel
import com.example.randomapp.viewmodels.MainViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()  {
    private val printText : TextView
        get()= findViewById(R.id.quote)
    private val nextBtn : TextView
        get()= findViewById(R.id.nextbtn)
    private val previousBtn : TextView
        get()= findViewById(R.id.prevbtn)

    private var index =0;

    lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RandomApp)
        setContentView(R.layout.activity_main)

        setQuote(index)
    }
    fun setQuote(index:Int){
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val repository = QuoteRepository(quoteService)
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        mainViewModel.quotes.observe(this, Observer{
            printText.text = it.results[index].content.toString()
        })
    }
    fun nextQuote(view : View){
        ++index
        setQuote(index)
    }
    fun prevQuote(view : View){
        --index
        setQuote(index)
    }

}