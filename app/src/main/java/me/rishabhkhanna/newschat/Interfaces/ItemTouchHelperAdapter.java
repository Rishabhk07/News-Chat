package me.rishabhkhanna.newschat.Interfaces;

/**
 * Created by rishabhkhanna on 27/07/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onMoved(int fromPos, int toPosition);
}
