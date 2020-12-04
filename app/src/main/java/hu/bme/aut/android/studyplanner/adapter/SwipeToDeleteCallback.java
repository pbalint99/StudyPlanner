package hu.bme.aut.android.studyplanner.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import hu.bme.aut.android.studyplanner.R;
import hu.bme.aut.android.studyplanner.viewmodel.TaskViewModel;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private SimpleItemRecyclerViewAdapter mAdapter;
    private TaskViewModel taskViewModel;

    public SwipeToDeleteCallback(SimpleItemRecyclerViewAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteRow(position);
    }


}