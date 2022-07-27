package com.dguitarclassic.intermediate.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dguitarclassic.intermediate.*
import com.dguitarclassic.intermediate.Adapter.StoryListAdapter
import com.dguitarclassic.intermediate.Model.MainViewModel
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private val storyViewModel: StoryViewModel by viewModels {
        StoryViewModelFactory(this, AuthPref.getInstance(dataStore))
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var storyListAdapter: StoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.app_name)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreate.setOnClickListener{
            finish()
            startActivity(Intent(this, CreateStoryActivity::class.java))
        }
        binding.btnMaps.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthPref.getInstance(dataStore))
        )[MainViewModel::class.java]
        setAdapter()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.menu_lang ->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.menu_logout ->{
                mainViewModel.logout()
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
                true
            }
            else -> false

        }
    }

    fun setAdapter(){
        storyListAdapter = StoryListAdapter()
        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.adapter = storyListAdapter
        storyViewModel.getAllStory.observe(this){
            storyListAdapter.submitData(lifecycle,it)
        }
    }
}