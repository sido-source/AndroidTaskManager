package com.example.taskmanager

import android.annotation.SuppressLint
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.databinding.FragmentListBinding
import com.example.taskmanager.databinding.RecyclerViewItemBinding
import com.example.taskmanager.fragments.ListFragment
import java.text.DateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var listFragment: ListFragment
    private lateinit var binding: RecyclerViewItemBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainActivityCalendarSetUp()

        listFragment = ListFragment()

        //where R.id.mainContainer is the container inside FrameLayout in activity_main.xml
        supportFragmentManager.beginTransaction().
        add(R.id.mainContainer, listFragment, listFragment.javaClass.name).
        commit()
    }

    private fun mainActivityCalendarSetUp() {
        val calendar = Calendar.getInstance().time
        val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar)
        mainBinding.mainActivityDate.text = dateFormat
    }


}