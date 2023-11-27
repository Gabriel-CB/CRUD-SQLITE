package com.example.permuta_crud

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.permuta_crud.databinding.ActivityHomeBinding
import com.example.permuta_crud.main.FormFornecedores
import com.example.permuta_crud.main.FormProdutos
import com.example.permuta_crud.main.SectionsPagerAdapter

import com.google.android.material.tabs.TabLayout

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

    }

    override fun onDestroy() {
        super.onDestroy()

        finish();
    }


}