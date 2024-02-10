package com.cwtstudio.mytodos.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cwtstudio.mytodos.Adapters.TodoAdapter;
import com.cwtstudio.mytodos.Listeners.ClickListener;
import com.cwtstudio.mytodos.Models.Todo;
import com.cwtstudio.mytodos.R;
import com.cwtstudio.mytodos.Utils.SPManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private AppCompatButton btnAdd, btnClear;
    private TodoAdapter adapter;
    private List<Todo> todoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setRecyclerView();
        btnAdd.setOnClickListener(v -> {
            showAddDialog();


        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSearch(newText);
                return false;
            }
        });

        btnClear.setOnClickListener(v -> {
            SPManager.Clear(this);
            todoList.clear();
            adapter.notifyDataSetChanged();
        });


    }

    private void filterSearch(String text) {
        List<Todo> filteredList = new ArrayList<>();
        for (Todo todo : todoList) {
            if (todo.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                    todo.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(todo);
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No data found !", Toast.LENGTH_SHORT).show();
            } else {
                adapter.filterList(filteredList);
            }

        }

    }

    private void showAddDialog() {
        EditText edtTitle, edtDescription;
        AppCompatButton btnAction;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        edtTitle = dialog.findViewById(R.id.edtTitle);
        edtDescription = dialog.findViewById(R.id.edtDescription);
        btnAction = dialog.findViewById(R.id.btnAction);


        btnAction.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
            Date date = new Date();
            String currentTime = simpleDateFormat.format(date);
            Todo todo = new Todo();
            todoList.add(todo);
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setStartTime(currentTime);
            todo.setEndTime("Running");
            todo.setFinished(false);

            SPManager.saveTodoList(this, todoList);
            adapter.notifyDataSetChanged();
            dialog.dismiss();


        });

        dialog.show();


    }

    private void setRecyclerView() {
        todoList = SPManager.getTodoList(this);
        adapter = new TodoAdapter(MainActivity.this, todoList, clickListener);
        recyclerView.setAdapter(adapter);


    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        btnAdd = findViewById(R.id.btnAdd);
        btnClear = findViewById(R.id.btnClear);


    }

    ClickListener clickListener = new ClickListener() {
        @Override
        public void onItemClicked(int index) {
            EditText edtTitle, edtDescription;
            AppCompatButton btnAction;

            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_dialog);
            edtTitle = dialog.findViewById(R.id.edtTitle);
            edtDescription = dialog.findViewById(R.id.edtDescription);
            btnAction = dialog.findViewById(R.id.btnAction);
            edtTitle.setText(todoList.get(index).getTitle());
            edtDescription.setText(todoList.get(index).getDescription());
            btnAction.setText("Update");


            btnAction.setOnClickListener(v -> {
                String title = edtTitle.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();
                String currentTime = simpleDateFormat.format(date);
                Todo todo = new Todo();
                todoList.set(index, todo);
                todo.setTitle(title);
                todo.setDescription(description);
                todo.setStartTime(currentTime);
                todo.setEndTime("Running");
                todo.setFinished(false);

                SPManager.saveTodoList(MainActivity.this, todoList);
                adapter.notifyDataSetChanged();
                dialog.dismiss();


            });

            dialog.show();


        }

        @Override
        public void onItemLongPressed(int index) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Perform Actions")
                    .setMessage("Are you sure you want to delete this ?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        todoList.remove(index);
                        SPManager.saveTodoList(MainActivity.this, todoList);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();

                    }).show();
        }

        @Override
        public void onCheckedListener(int index, CheckBox checkBox) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                todoList.get(index).setFinished(isChecked);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();
                String currentTime = simpleDateFormat.format(date);
                todoList.get(index).setEndTime(currentTime);
                SPManager.saveTodoList(MainActivity.this, todoList);
                adapter.notifyDataSetChanged();

            });


        }
    };


}