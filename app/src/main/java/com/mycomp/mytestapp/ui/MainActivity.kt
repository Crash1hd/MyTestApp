package com.mycomp.mytestapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mycomp.mytestapp.R
import com.mycomp.mytestapp.utilities.log.Logger
import com.mycomp.mytestapp.utilities.log.ui.LogViewModel
import com.mycomp.mytestapp.utilities.log.ui.LogViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class MainActivity : ScopedAppActivity(), KodeinAware {
    override val kodein by kodein()//This used to be closest Kodein

    private val logViewModelFactory: LogViewModelFactory by instance()

    private lateinit var logViewModel: LogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val foo:LogViewModel by instance()

        // Use ViewModelProviders class to create / get already created QuotesViewModel
        // for this view (activity)
        logViewModel = ViewModelProviders.of(this, logViewModelFactory)
            .get(LogViewModel::class.java)

        Logger.i(TAG, "$TAG onCreate was called")

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = logViewModel.getAllLiveLogs.await()
        currentWeather.observe(this@MainActivity, Observer {
            if(it == null) return@Observer

            hWorld.text = it.toString()
        })
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}

//Usually used on fragments but same applies here for activities (retaining a local scope for the activity)
abstract class ScopedAppActivity: AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}