package com.example.taskmanager

import android.annotation.SuppressLint
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.Navigable.Destination.*
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.databinding.RecyclerViewItemBinding
import com.example.taskmanager.fragments.ARG_EDIT_ID
import com.example.taskmanager.fragments.EditFragment
import com.example.taskmanager.fragments.ListFragment
import com.example.taskmanager.fragments.ViewFragment
import com.example.taskmanager.model.Task
import java.text.DateFormat

class MainActivity : AppCompatActivity(), Navigable {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var listFragment: ListFragment

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


    override fun navigate(to: Navigable.Destination, taskId: Long?) {
        supportFragmentManager.beginTransaction().apply {
            when (to){
                List -> {
                    replace(R.id.mainContainer, listFragment, listFragment.javaClass.name)
                    addToBackStack(EditFragment::javaClass.name)
                }
                Add -> {
                    replace(
                        R.id.mainContainer,
                        EditFragment::class.java,
                        Bundle().apply { putLong(ARG_EDIT_ID, taskId ?: -1L) },
                        EditFragment::class.java.name)
                    //if user would like to see or edit specific shopping list and then get back to previous view such as MainShoppingLists
                    addToBackStack(EditFragment::javaClass.name)
                }

                View -> {
                    replace(
                        R.id.mainContainer,
                        ViewFragment::class.java,
                        Bundle().apply { putLong(ARG_EDIT_ID, taskId ?: -1L) },
                        ViewFragment::class.java.name)
                    addToBackStack(EditFragment::javaClass.name)
                }
            }.commit()
        }
    }
}