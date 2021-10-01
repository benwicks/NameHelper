package com.exsilicium.namehelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import com.exsilicium.namehelper.databinding.FragmentNavHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameHelperActivity : AppCompatActivity() {
    private lateinit var binding: FragmentNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.apply {
            title = this@NameHelperActivity.title
        })
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()
}
