package com.suman.custom_viewmodel

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.suman.custom_viewmodel.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialise()
    }

    private fun initialise() {
//        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
//        viewModel = MyViewModel()
        viewModel = (application as App).getViewModel("MainActivity") as MyViewModel

        binding.increaseBtn.setOnClickListener {
            viewModel.increaseCounter()
        }
        binding.next.setOnClickListener {
            startActivity(Intent(this, NextActivity::class.java))
            finish()
        }

        lifecycleScope.launch {
            viewModel.counterStateFlow.collect {
                binding.counter = it.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations){
            Toast.makeText(this,"Activity Switched", Toast.LENGTH_LONG).show()
            (application as App).clearViewModel("MainActivity")
        }else{
            Toast.makeText(this,"Configuration changed", Toast.LENGTH_LONG).show()
        }
    }
}