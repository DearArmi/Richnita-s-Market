package com.example.exercise.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise.Api.ApiResponseStatus
import com.example.exercise.Item
import com.example.exercise.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setting up Layout Manager
        binding.mainRecycler.layoutManager = LinearLayoutManager(this)

        //Setting up ViewModel and Giving context to it
        viewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)

        //Setting up adapter
        val adapter = ListAdapter()
        binding.mainRecycler.adapter = adapter


        viewModel.itemList.observe(this, Observer {

            adapter.submitList(it)
        })

        viewModel.status.observe(this, Observer {

            when (it) {
                ApiResponseStatus.LOADING -> {
                    binding.mainProgressBar.visibility = View.VISIBLE
                }
                ApiResponseStatus.DONE -> {
                    binding.mainProgressBar.visibility = View.GONE
                }
                ApiResponseStatus.ERROR -> {
                    binding.mainProgressBar.visibility = View.GONE
                }
            }

        })
    }

}