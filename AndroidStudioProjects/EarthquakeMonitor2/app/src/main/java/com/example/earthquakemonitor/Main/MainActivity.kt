package com.example.earthquakemonitor.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.EarthquakeDetail
import com.example.earthquakemonitor.R
import com.example.earthquakemonitor.api.ApiResponseStatus
import com.example.earthquakemonitor.databinding.ActivityMainBinding

private const val SORT_TYPE_KEY = "sort_type"

class MainActivity : AppCompatActivity() {

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        val sortType = getSortType()

        model = ViewModelProvider(this, MainViewModelFactory(application, sortType)).get(MainViewModel::class.java)
        val adapter = EqAdapter(this)
        binding.eqRecycler.adapter = adapter

        model.eqlist.observe(this, Observer {

            adapter.submitList(it)
            handleEmptyView(it, binding)
        })

        model.status.observe(this, Observer {
            if (it == ApiResponseStatus.LOADING){
                binding.progress.visibility = View.VISIBLE
            }else if (it == ApiResponseStatus.DONE){
                binding.progress.visibility = View.GONE
            }else if (it == ApiResponseStatus.ERROR){
                binding.progress.visibility = View.GONE
            }
        })

        adapter.onItemClickListener = {

            openDetailActivity(it)

        }
    }

    private fun openDetailActivity(earthqueake:Earthquake) {

        val intent = Intent(this, EarthquakeDetail::class.java)
        intent.putExtra(EarthquakeDetail.EARTHQUAKE_KEY, earthqueake)
        startActivity(intent)

    }

    private fun getSortType(): Boolean {
        val prefs = getPreferences(MODE_PRIVATE)
        return prefs.getBoolean(SORT_TYPE_KEY, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId

        if (itemId == R.id.sort_by_magnitude){
            model.reloadEarquakesFromDb(true)
            saveSortType(true)
        } else if (itemId == R.id.sort_by_time){
            model.reloadEarquakesFromDb(false)
            saveSortType(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveSortType(sortByMagnitude:Boolean){

        val prefs = getPreferences(MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(SORT_TYPE_KEY, sortByMagnitude)
        editor.apply()

    }

    private fun handleEmptyView(

            it: MutableList<Earthquake>,
            binding: ActivityMainBinding
    ) {
        if (it.isEmpty()) {
            binding.emptyR.visibility = View.VISIBLE
        } else {
            binding.emptyR.visibility = View.GONE
        }
    }
}