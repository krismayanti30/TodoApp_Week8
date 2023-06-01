package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.ListTodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class TodoListFragment : Fragment() {
    private lateinit var viewModel:ListTodoViewModel
    private val todoListAdapter  = TodoListAdapter(arrayListOf(),{item -> doClick(item)})
    fun doClick(item:Any){
        viewModel.clearTask(item as Todo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()

        view.findViewById<FloatingActionButton>(R.id.fabAddToDo).setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateToDo()
            Navigation.findNavController(it).navigate(action)
        }
        fun observeViewModel() {
            viewModel.todoLD.observe(viewLifecycleOwner, Observer {
                todoListAdapter.updateTodoList(it)
                var txtEmpty = view?.findViewById<TextView>(R.id.txtEmpty)
                if(it.isEmpty()) {
                    txtEmpty?.visibility = View.VISIBLE
                } else {
                    txtEmpty?.visibility = View.GONE
                }
            })
        }
        observeViewModel()
    }
}