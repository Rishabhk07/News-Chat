package me.rishabhkhanna.peopleword.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import me.rishabhkhanna.peopleword.Interfaces.ItemTouchHelperAdapter;

/**
 * Created by rishabhkhanna on 27/07/17.
 */

public class TouchHelper extends ItemTouchHelper.Callback {
    ItemTouchHelperAdapter mAdapter;
    public TouchHelper(ItemTouchHelperAdapter itemTouchHelperAdapter) {
        mAdapter = itemTouchHelperAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP  | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }



    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        mAdapter.onMoved(fromPos,toPos);
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }



    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
