package com.example.navigationuidemo2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.databinding.DataBindingUtil


class AddTenant : AppCompatActivity() {
    private lateinit var binding: AddtenantActivityBinding
    private val viewModel: TenantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.addtenant_activity)
        binding.lifecycleOwner = this          // important for LiveData bindings
        binding.viewModel = viewModel          // connect the viewModel to layout

        val fullName = binding.fullName
        val unitNumber = binding.unitNumber
        val fullRent = binding.fullRent

        binding.button.setOnClickListener {
            val name = fullName.text.toString()
            val unit = unitNumber.text.toString()
            val rent = fullRent.text.toString()
            viewModel.addTenant(name, unit, rent)
            fullName.text.clear()
            unitNumber.text.clear()
            fullRent.text.clear()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}



    class TenantViewModel : ViewModel() {

        // Internal mutable storage for tenant info as a single text block
        private val _tenantInfo = MutableLiveData<String>("")
        val tenantInfo: LiveData<String> = _tenantInfo

        // Exposed transformed LiveData (uppercase display) — bonus step
        val capitalizedTenantInfo: LiveData<String> = tenantInfo.map { it.uppercase() }

        // Tenant counter (bonus)
        private val _tenantCount = MutableLiveData(0)
        val tenantCount: LiveData<Int> = _tenantCount

        fun addTenant(name: String, unit: String, rent: String) {
            val newEntry = "Name: $name\nUnit: $unit\nRent: $rent\n\n"
            _tenantInfo.value = (_tenantInfo.value ?: "") + newEntry

            // increment count
            _tenantCount.value = (_tenantCount.value ?: 0) + 1
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "SecondActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "SecondActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "SecondActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "SecondActivity - onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LIFECYCLE", "SecondActivity - onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "SecondActivity - onDestroy")
    }
}
