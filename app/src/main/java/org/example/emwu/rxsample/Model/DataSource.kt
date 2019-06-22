package org.example.emwu.rxsample.Model

import java.util.ArrayList

object DataSource {

    fun createTasksList(): List<Task> {
        val tasks = ArrayList<Task>()
        tasks.add(Task("Drink water", true, 1))
        tasks.add(Task("Eat Breakfast", true, 2))
        tasks.add(Task("Go to the library", true, 2))
        tasks.add(Task("Take a walk", false, 3))
        tasks.add(Task("Eat Lunch", false, 2))
        tasks.add(Task("Email midway studio", true, 2))
        return tasks
    }
}
