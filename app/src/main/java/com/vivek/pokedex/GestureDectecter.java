package com.vivek.pokedex;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.vivek.pokedex.Db_Handler.DbHandler;

public class GestureDectecter extends GestureDetector.SimpleOnGestureListener {

    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    private PokeActivity activity = null;

    int add= PokeActivity.add;

    public PokeActivity getActivity() {
        return activity;
    }

    public void setActivity(PokeActivity activity) {
        this.activity = activity;
    }

    DbHandler db = new DbHandler(getActivity());



    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float deltaX = e1.getX() - e2.getX();
        float deltaY = e1.getY() - e2.getY();

        float deltaXAbs = Math.abs(e1.getX() - e2.getX());
        float deltaYAbs = Math.abs(e1.getY() - e2.getY());







        return true;
    }
}
