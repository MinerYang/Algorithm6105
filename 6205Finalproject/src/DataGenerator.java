/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkg6205_final_project;

/**
 *
 * @author prospace
 */
import java.util.*;
public class DataGenerator {//random 10 building which need use to calculate
    private int[] loc = new int [10];
    public DataGenerator(){
        int[] a = new int [1<<20];
        for (int i = 0 ; i<1024 ; i ++)
            for (int j = 0 ;j<1024; j ++)
                a[i*1024+j] = i*10000+j;//initial hashcode value of every point;

        Random x = new Random();
        for (int i = (1<<20) - 1 ; i>0; i --){//shuffle process
            int tmp = x.nextInt(i);
            int tmp2 = a[i];
            a[i] = a[tmp];
            a[tmp] =tmp2;
        }
        for (int i = 0 ; i<10 ; i ++){//select the first 10th point
            loc[i] = a[i];
            System.out.println(loc[i]/10000 + " " + loc[i]%10000);
        }
        for (int i = 0 ; i<10;i++)//sort
            for (int j = i;j>0;j--)
                if (loc[j]<loc[j-1])
                {
                    int tmp = loc[j-1];
                    loc[j-1] = loc[j];
                    loc[j] = tmp;
                }

    }
    public int[] DataReader(){
        return loc;
    }
}
