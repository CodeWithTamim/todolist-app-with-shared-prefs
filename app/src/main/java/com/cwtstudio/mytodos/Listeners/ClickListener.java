package com.cwtstudio.mytodos.Listeners;

import android.widget.CheckBox;

public interface ClickListener {
    void onItemClicked(int index);
    void onItemLongPressed(int index);
    void onCheckedListener(int index, CheckBox checkBox);


}
