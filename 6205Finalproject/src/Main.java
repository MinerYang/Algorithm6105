
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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataGenerator da = new DataGenerator();
        Solution sol = new Solution(da.DataReader());
        sol.init_answer();
        sol.CalcWork();
        sol.PrintWork();

    }

}

