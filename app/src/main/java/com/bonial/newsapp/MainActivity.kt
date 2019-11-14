package com.bonial.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bonial.newsapp.database.AppDatabase
import com.bonial.newsapp.di.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private var component: AppComponent = DaggerAppComponent.builder().appModule(AppModule(App.getApplicationContext())).build()
    private var subComponent: ViewModelSubComponent = component.addComponent(ViewModelModule())

    override fun onCreate(savedInstanceState: Bundle?) {

        //http://newsapi.org
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        viewModel = ViewModelProviders.of(this).get(NewsFeedViewModel::class.java)
        component.inject(this)
        subComponent.inject(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyDatabase()
    }
}
