package com.test.supermarket.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator

import com.test.supermarket.ListAdapter
import com.test.supermarket.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var viewModel: ViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setting up Layout Manager
        binding.mainRecycler.layoutManager = LinearLayoutManager(this)

        //Setting up ViewModel and Giving context to it
        viewModel = ViewModelProvider(this, ViewModelFactory(application)).get(ViewModel::class.java)

        //Setting up adapter
        adapter = ListAdapter()
        binding.mainRecycler.adapter = adapter

        Toast.makeText(this,"Tap on item to delete", Toast.LENGTH_SHORT).show()


        viewModel.itemList.observe(this, Observer {

                adapter.submitList(it)
        })

        viewModel.total.observe(this, Observer {

            binding.total.text = "$it$"
        })

        binding.addBt.setOnClickListener{

            IntentIntegrator(this).initiateScan()

        }



        adapter.onItemClickListener = {
            //Removing Items from list
            viewModel.removeItem(it)
            adapter.notifyDataSetChanged()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val string = result.contents

        //Using this when QR codes were not available on weekend
        /*val itemArray = arrayOf("0001","0002","0003")
        val idRandom = itemArray[java.util.Random().nextInt(itemArray.size)]*/

        if (string!=null) {

            viewModel.addItem(string)
            adapter.notifyDataSetChanged()
            binding.mainRecycler.scrollToPosition(viewModel.list.size)

        }

    }

}
