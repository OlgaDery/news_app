package com.bonial.newsapp

import io.mockk.*
import org.junit.Test

class CoroutinesTestWithMocks {

    @Test
    fun getDataFromWebServer_isRunning() {
        val viewModel = mockk<NewsFeedViewModel>()
        coEvery {
            viewModel.getDataFromWebServer(true)
        }just Runs
        viewModel.getDataFromWebServer(true)
        coVerify {
            viewModel.getDataFromWebServer(true)
        }
    }

    @Test
    fun getDataFromDB_isRunning() {
        val viewModel = mockk<NewsFeedViewModel>()
        coEvery {
            viewModel.getDataFromDB()
        }just Runs
        viewModel.getDataFromDB()
        coVerify {
            viewModel.getDataFromDB()
        }
    }

    @Test
    fun updateDatabase_isRunning() {
        val viewModel = mockk<NewsFeedViewModel>()
        coEvery {
            viewModel.updateDatabase(mutableListOf())
        }just Runs
        viewModel.updateDatabase(mutableListOf())
        coVerify {
            viewModel.updateDatabase(mutableListOf())
        }
    }
}