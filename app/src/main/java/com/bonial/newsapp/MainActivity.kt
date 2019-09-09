package com.bonial.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bonial.newsapp.di.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsFeedViewModel
    lateinit var navController: NavController
    lateinit var navHostFragment: NavHostFragment

    //TODO later replace with ApplicationContext!!!!
    private var component: AppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
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
}
