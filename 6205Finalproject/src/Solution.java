/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkg6205_final_project;

import java.util.Random;

/**
 *
 * @author prospace
 */
public class Solution {

    int[] answer_list = new int[1000];
    int[] loc_list = new int[10];
    double[] rat = new double[1000];
    int[] bel = new int[26000];
    int alen = 1000;
    int [] f= new int[10];
    public Solution(int [] a){
        for (int i = 0; i < 10; i++)
            loc_list[i] = a[i];
    }

    public void init_answer(){//random 1000 initial individual

        int[] a = new int [1<<20];
        for (int i = 0 ; i<1024 ; i ++)
            for (int j = 0; j<1024; j++)
                a[i*1024+j] = i*10000+j;

        Random x = new Random();

        for (int i = 0 ; i < 10; i++)
            f[i] = x.nextInt(1024);

        for (int i = (1<<20) - 1 ; i>0; i --){
            int tmp = x.nextInt(i+1);
            int tmp2 = a[i];
            a[i] = a[tmp];
            a[tmp] =tmp2;
        }
        for (int i = 0 ; i<alen ; i ++){
            answer_list[i] = a[i];
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0 ; i < 1000; i++)
            System.out.println(answer_list[i]/10000+" "+answer_list[i]%10000+" "+FuncCalc(answer_list[i]));
        //debug();
    }

    public void CalcWork(){//repeat  evolving 100000 times.
        for (int ci = 0; ci<100000; ci ++){
            SelectWork();
            HybridWork();
            VaryWork();
        }
        SelectWork();
    }

    /**
     *  Natural selection process select another 1000 individual based on each's possibility.
     *  the Specific probability calculation method is described at the report.
     */
    public void SelectWork(){
        ratfunc(rat);
        int l = 0;
        for (int i = 0; i<alen; i++){
            for (int j = l; j< rat[i] * 25000 + l; j++)
                bel[j] = i;
            l = (int)Math.floor(rat[i] * 25000) + l;
        }
        int[] tmpanslist = new int[alen];
        Random x = new Random();
        for (int i = 0;i<alen; i++){
            tmpanslist[i] = answer_list[bel[x.nextInt(25000)]];
        }
        for (int i = 0; i<alen; i++)
            answer_list[i] = tmpanslist[i];
    }

    /**
     *  parameter double[] r is the array of initial ratio
     *  return an arrary after we computing the fitness as ratio2
     */
    public double[] ratfunc(double[] r){//possibility calculation
        double sumrat=0;
        for (int i = 0 ; i<r.length; i++){ //calculate distance from points to points
            r[i] = FuncCalc(answer_list[i]); // every total distance from target points to other 10 points
            sumrat += r[i];
        }
        double sumrat2 = 0;

        for (int i = 0 ; i<r.length; i++){
            r[i] = 1.0/(rat[i]/sumrat); //ratio1
            sumrat2 += rat[i];
        }
        for (int i = 0; i<r.length; i++)
            r[i] = r[i]/sumrat2; //ratio2
        return r;
    }

    /**
     *  Hybridization/crossover process
     */
    public void HybridWork(){
        Random x = new Random();
        for (int i = alen-1; i>0; i--)//shuffle make i and i+1 Random pairing
        {
            int tmp = x.nextInt(i+1);
            int tmp2 = answer_list[i];
            answer_list[i] = answer_list[tmp];
            answer_list[tmp] = tmp2;
        }

        for (int i = 0 ; i<alen; i+=2){//take the i and i+1 as a pair change one of their genus randomly
            int tmpx = x.nextInt(10);
            int k = x.nextInt(2);
            if (k == 1) {
                int tmpx1 = answer_list[i]%10000;
                int tmpx2 = answer_list[i+1]%10000;
                int[] p=exchange(tmpx1,tmpx2,tmpx);
                answer_list[i] = (answer_list[i]/10000)*10000 + p[1];
                answer_list[i+1] = (answer_list[i+1]/10000)*10000 + p[0];

            }
            if (k == 0) {
                int tmpx1 = answer_list[i]/10000;
                int tmpx2 = answer_list[i+1]/10000;
                int[] p=exchange(tmpx1,tmpx2,tmpx);
                answer_list[i] = (answer_list[i]%10000) + p[1] * 10000;
                answer_list[i+1] = (answer_list[i+1]%10000) + p[0] * 10000;

            }
        }
    }

    /**
     *parameter a and b is individual
     */
    public int[] exchange(int a,int b,int bit){//change the genus
        int tmpx11 = (b % (1<<bit)) + ((b / (1<<(bit+1))) * (1<<(bit+1))) + (a & (1<<bit));
        int tmpx22 = (a % (1<<bit)) + ((a / (1<<(bit+1))) * (1<<(bit+1))) + (b & (1<<bit));
        int[] re=new int[2];
        re[0]=tmpx11;
        re[1]=tmpx22;
        return re;
        //  System.out.println(answer_list[i] + " " + answer_list[i+1] + " " + a + " " + b + " " + tmpx11 + " " + tmpx22 + " " + bit);
    }

    public void VaryWork(){//Variation process choose one genus of n randomly and it have a 2% possibility to take inverse
        Random x = new Random();
        for (int i = 0 ; i<alen; i++){
            int tmploc = x.nextInt(20);
            int tmpvar = x.nextInt(50);
            if (tmpvar == 1){
                int tmpx = answer_list[i]%10000;
                int tmpy = answer_list[i]/10000;
                if (tmploc < 10){
                    tmpx =  mutate(tmpx,tmploc);
                    answer_list[i] = tmpy * 10000 + tmpx;
                }
                else{
                    tmpy =  mutate(tmpx,tmploc-10);
                    answer_list[i] = tmpy * 10000 + tmpx;
                }
            }
        }
    }

    public int mutate(int x,int loc){// the function use to take the inverse
        return x^(1<<loc);
    }

    public double fx(double x, double maxf){//Default function of distance and weight
        return (x-maxf)*(x-maxf)+100 + 96*x;

    }
    public double FuncCalc(int x){//value calculation
        double sum = 0;
        for (int i = 0; i <10; i++){
            sum += fx(Math.sqrt(1.0*((x/10000) - (loc_list[i]/10000))*((x/10000) - (loc_list[i]/10000)) + 1.0*((x%10000) - (loc_list[i]%10000))*((x%10000) - (loc_list[i]%10000))),f[i]);
        }
        return 100*sum;
    }

    char[][] s = new char[1024][1024];

    public void PrintWork(){//Print the related data

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        /*for (int i = 0;i<1024;i++,System.out.println())
            for (int j =0 ; j<1024;j++)
                System.out.print(FuncCalc(i*10000+j)+" ");

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/

        for (int i = 0 ; i<1000; i++)
            System.out.println(answer_list[i]/10000 +  " " + answer_list[i]%10000 + " " + FuncCalc(answer_list[i]));
    }


    public void debug(){
        for (int i = 0; i<alen; i++)
            System.out.println(answer_list[i]);
    }

}
