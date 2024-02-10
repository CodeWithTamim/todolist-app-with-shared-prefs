package com.cwtstudio.mytodos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cwtstudio.mytodos.Listeners.ClickListener;
import com.cwtstudio.mytodos.Models.Todo;
import com.cwtstudio.mytodos.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    Context context;
    List<Todo> todoList;
    ClickListener onClickListener;

    public TodoAdapter(Context context, List<Todo> todoList, ClickListener onClickListener) {
        this.context = context;
        this.todoList = todoList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.txtTitle.setText(todoList.get(position).getTitle());
        holder.txtDescription.setText(todoList.get(position).getDescription());
        holder.txtStartTime.setText(todoList.get(position).getStartTime());
        holder.txtEndTime.setText(todoList.get(position).getEndTime());
        if (todoList.get(position).isFinished()) {
            holder.cbisCompleated.setChecked(true);

        } else {
            holder.cbisCompleated.setChecked(false);
            holder.txtEndTime.setText("End time : Running");

        }


        holder.itemView.setOnClickListener(v -> {
            onClickListener.onItemClicked(position);

        });
        holder.itemView.setOnLongClickListener(v -> {
            onClickListener.onItemLongPressed(position);

            return false;
        });


           onClickListener.onCheckedListener(holder.getAdapterPosition(),holder.cbisCompleated);



    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
    public void filterList(List<Todo> filteredlist){
        todoList = filteredlist;
        notifyDataSetChanged();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDescription, txtStartTime, txtEndTime;
        private CheckBox cbisCompleated;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtEndTime = itemView.findViewById(R.id.txtEndTime);
            cbisCompleated = itemView.findViewById(R.id.cbisCompleated);



        }
    }


}
