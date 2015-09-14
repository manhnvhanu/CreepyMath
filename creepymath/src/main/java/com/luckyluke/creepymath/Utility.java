package com.luckyluke.creepymath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Lucky Luke on 21/08/2015.
 */
public class Utility {

    public static int genNumber(int x) {

        int min = x - 1;
        int max = x + 1;

        Random r = new Random();


        return r.nextInt(max - min + 1) + min;
    }


    public static int genCeiling(int x){

        return (int) Math.floor(Math.random() * x);
    }

    //Create random integer array
    public static String genOperator() {

        ArrayList<String> initialList = new ArrayList<>();

        initialList.add("+"); //Add element
        initialList.add("-"); //Add element

        Collections.shuffle(initialList); //Random the position

        return initialList.get(0);
    }

}
