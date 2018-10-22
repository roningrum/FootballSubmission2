package com.roningrum.footballsubmission2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.roningrum.footballsubmission2.R.id.next
import com.roningrum.footballsubmission2.R.id.prev
import com.roningrum.footballsubmission2.nextmatch.NextFragment
import com.roningrum.footballsubmission2.prevmatch.PrevFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                prev ->{
                    loadPrevFragment(savedInstanceState)
                }
                next ->{
                    loadNextFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = prev
    }

    private fun loadNextFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    NextFragment(),
                    NextFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadPrevFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    PrevFragment(),
                    PrevFragment::class.java.simpleName)
                .commit()
        }

    }
}
