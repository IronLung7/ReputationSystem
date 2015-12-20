package com.rs.server;

import com.rs.algorithm.CB_TB_L1_AVG;
import com.rs.algorithm.CB_TB_L1_MAX;
import com.rs.algorithm.TB_L1_AVG;
import com.rs.data.repository.Repository;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by leo on 15/3/13.
 */
public class ReputationSystemTest {

    private ConfigurableApplicationContext context;
    private ReputationCalculation reputationCalculation;
    Repository repository = null;

    public ReputationSystemTest(ConfigurableApplicationContext context) {
        this.context = context;
        repository = new Repository(context);
        //this.reputationCalculation = new ReputationCalculation();
    }

    public static PrintWriter pw;

    public void run() {
        //log输出文件
        File outFile = new File("/Users/leo/Desktop/0520 KNN another2.txt");
        if (outFile.exists()) {
            try {
                outFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            pw = new PrintWriter(new FileWriter(outFile));
            pw.println("test?");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.getAvgAsEscore();


//        //每次测试一个算法，先new一个ReputationCalculation，设置lamada大小，然后run
//        pw.println("KNN-TB-L1-AVG" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN(new CB_TB_L1_AVG());
//
//        pw.println("KNN-TB-L1-MAX" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN(new CB_TB_L1_MAX());
//
//        pw.println("KNN-TB-L1-MIN" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN(new CB_TB_L1_AVG());
//
//        pw.println("KNN_TB_SQUARE_AVG" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN(new CB_TB_SQUARE_AVG());
//
//
//        pw.println("KNN_TB_SQUARE_MAX" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN(new CB_TB_SQUARE_MAX());
//
//
//        pw.println("KNN-TB_SQUARE_MIN" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN(new CB_TB_SQUARE_MIN());

//        pw.println("NEAR 3");
//        pw.println("KNN-CB-TB-L1-AVG" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN_3near(new CB_TB_L1_AVG());
//
//        pw.println("KNN-CB-TB-L1-MAX" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN_3near(new CB_TB_L1_MAX());
//
//        pw.println("KNN-CB-TB-L1-MIN" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN_3near(new CB_TB_L1_MIN());
//
//        pw.println("KNN-CB_TB_SQUARE_AVG" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN_3near(new CB_TB_SQUARE_AVG());
//
//
//        pw.println("KNN-CB_TB_SQUARE_MAX" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN_3near(new CB_TB_SQUARE_MAX());
//
//        pw.println("KNN-CB-TB_SQUARE_MIN" + "  0.03");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.runKNN_3near(new CB_TB_SQUARE_MIN());

//
//
//
//        pw.println("KNN-CB-TB-L1-AVG" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_L1_AVG(i / 100f));
//        }
//
//        pw.println("KNN-CB-TB-L1-MAX" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_L1_MAX(i / 100f));
//        }
//
//        pw.println("KNN-CB-TB-L1-MIN" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_L1_MIN(i / 100f));
//        }
//
//        pw.println("KNN-CB_TB_SQUARE_AVG" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_SQUARE_AVG(i / 100f));
//        }
//
//        pw.println("KNN-CB_TB_SQUARE_MAX" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_SQUARE_MAX(i / 100f));
//        }
//
//        pw.println("CB_TB_SQUARE_MIN" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_SQUARE_MIN(i / 100f));
//        }


        //TODO MORE

//        pw.println("TB_L1_AVG");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runKNN(new TB_L1_AVG());
//        }
//
//        pw.println("TB_L1_MAX");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runKNN(new TB_L1_MAX());
//        }
//
//        pw.println("TB_L1_MIN");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runKNN(new TB_L1_MIN());
//        }
//
//        pw.println("TB_SQUARE_AVG");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runKNN(new TB_SQUARE_AVG());
//        }


//        pw.println("TB_SQUARE_MAX");
//        for(int i =7;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runKNN(new TB_SQUARE_MAX());
//        }

//        pw.println("TB_SQUARE_MIN");
//        for(int i =4;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runKNN(new TB_SQUARE_MIN());
//        }
//


//        pw.println("CB-TB-L1-AVG" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            if (i == 96) continue;
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_L1_AVG(i / 100f));
//        }
//
//        pw.println("CB-TB-L1-MAX" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            if (i == 99) continue;
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_L1_MAX(i / 100f));
//        }
//
//        pw.println("CB-TB-L1-MIN" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            if (i == 94) continue;
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_L1_MIN(i / 100f));
//        }
//
//        pw.println("CB_TB_SQUARE_AVG" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            if (i == 97) continue;
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_SQUARE_AVG(i / 100f));
//        }
//
//        pw.println("CB_TB_SQUARE_MAX" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            if (i == 99) continue;
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_SQUARE_MAX(i / 100f));
//        }
//
//        pw.println("CB_TB_SQUARE_MIN" + "  " + 0.03);
//        for (int i = 100; i > 89; i--) {
//            if (i == 93) continue;
//            pw.println("beta " + i / 100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.runKNN(new CB_TB_SQUARE_MIN(i / 100f));
//        }


//        repository.eraseAllData();
//        repository.testData();
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.getAvgAsEscore();

//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(1/100f);
//        reputationCalculation.run(new TB_L1_AVG());
//
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(5/100f);
//        reputationCalculation.run(new TB_L1_AVG());
//
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(9/100f);
//        reputationCalculation.run(new TB_L1_AVG());

//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(1/100f);
//        reputationCalculation.run(new TB_SQUARE_AVG());
//
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(5/100f);
//        reputationCalculation.run(new TB_SQUARE_AVG());
//
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(9/100f);
//        reputationCalculation.run(new TB_SQUARE_AVG());

//        pw.println("CB_TB_L1_MIN"+"  "+0.03);
//        for(int i =100;i>89;i--){
//            pw.println("beta "+i/100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.run(new CB_TB_L1_MIN(i / 100f));
//        }

//        pw.println("L1_AVG");
//        for(int i =4;i<5;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runNotTB(new L1_AVG());
//        }

//        pw.println("CB-TB-L1-AVG"+"  "+0.03);
//        for(int i =94;i>89;i--){
//            pw.println("beta "+i/100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.run(new CB_TB_L1_AVG(i / 100f));
//        }
//
//        pw.println("CB-TB-L1-MAX"+"  "+0.03);
//        for(int i =94;i>89;i--){
//            pw.println("beta "+i/100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.run(new CB_TB_L1_MAX(i / 100f));
//        }
//
//        pw.println("CB_TB_SQUARE_AVG"+"  "+0.03);
//        for(int i =94;i>89;i--){
//            pw.println("beta "+i/100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.run(new CB_TB_SQUARE_AVG(i / 100f));
//        }
//
//        pw.println("CB_TB_SQUARE_MAX"+"  "+0.03);
//        for(int i =94;i>89;i--){
//            pw.println("beta "+i/100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.run(new CB_TB_SQUARE_MAX(i / 100f));
//        }
//
//        pw.println("CB_TB_SQUARE_MIN"+"  "+0.03);
//        for(int i =94;i>89;i--){
//            pw.println("beta "+i/100f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.run(new CB_TB_SQUARE_MIN(i / 100f));
//        }
//
//
////TODO RUN AGAIN
//        pw.println("TB_L1_MIN important");
//        pw.println("TB_L1_MIN");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_L1_MIN());
//        }


//        pw.println("TB_L1_AVG");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_L1_AVG());
//        }
//
//        pw.println("TB_L1_MAX");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_L1_MAX());
//        }
//
//        pw.println("TB_L1_MIN");
//        for(int i =2;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_L1_MIN());
//        }

        //        pw.println("L1_AVG");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.runNotTB(new L1_AVG());
//        }


//
//
//        pw.println("TB_SQUARE_AVG");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_SQUARE_AVG());
//        }
//

//        pw.println("TB_SQUARE_MAX");
//        for(int i =5;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_SQUARE_MAX());
//        }
//
//        pw.println("TB_SQUARE_MIN");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_SQUARE_MIN());
//        }

//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.getAvgAsEscore();
//
        pw.println("TB_L1_AVG"+"  lamda 0.03");
        reputationCalculation = new ReputationCalculation();
        reputationCalculation.setLamda(0.03f);
        reputationCalculation.run(new TB_L1_AVG());
//
//

//        pw.println("CB_TB_L1_MAX" + "  " + 0.03f);
//        float beta = 0.99f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MAX(beta));
//
//        beta = 0.98f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MAX(beta));
//
//        beta = 0.97f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MAX(beta));
//
//        beta = 0.96f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MAX(beta));
//
//        beta = 0.95f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MAX(beta));

//        float beta = 0.99f;
//
//        pw.println("CB_TB_L1_AVG" + "  " + 0.03f);
//        beta = 0.99f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_AVG(beta));
//
//        beta = 0.98f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_AVG(beta));
//
//        beta = 0.97f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_AVG(beta));
//
//        beta = 0.96f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_AVG(beta));
//
//        beta = 0.95f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_AVG(beta));
//
//        pw.println("CB_TB_L1_MIN" + "  " + 0.03f);
//        beta = 0.99f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MIN(beta));
//
//        beta = 0.98f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MIN(beta));
//
//        beta = 0.97f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MIN(beta));
//
//        beta = 0.96f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MIN(beta));
//
//        beta = 0.95f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_L1_MIN(beta));
//
//
//
//        pw.println("CB_TB_SQUARE_AVG" + "  " + 0.03f);
//        beta = 0.99f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_AVG(beta));
//
//        beta = 0.98f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_AVG(beta));
//
//        beta = 0.97f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_AVG(beta));
//
//        beta = 0.96f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_AVG(beta));
//
//        beta = 0.95f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_AVG(beta));
//
//
//
//        pw.println("CB_TB_SQUARE_MAX" + "  " + 0.03f);
//        beta = 0.99f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MAX(beta));
//
//        beta = 0.98f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MAX(beta));
//
//        beta = 0.97f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MAX(beta));
//
//        beta = 0.96f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MAX(beta));
//
//        beta = 0.95f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MAX(beta));
//
//
//        pw.println("CB_TB_SQUARE_MIN" + "  " + 0.03f);
//        beta = 0.99f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MIN(beta));
//
//        beta = 0.98f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MIN(beta));
//
//        beta = 0.97f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MIN(beta));
//
//        beta = 0.96f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MIN(beta));
//
//        beta = 0.95f;
//        pw.println("beta" + beta);
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.03f);
//        reputationCalculation.run(new CB_TB_SQUARE_MIN(beta));

//
//        pw.println("TB_L1_AVG"+"  lamda 0.7");
//        reputationCalculation = new ReputationCalculation();
//        reputationCalculation.setLamda(0.3f);
//        reputationCalculation.run(new TB_L1_AVG());
//        pw.println("CB_TB_L1_MIN" + "  " + 0.04f);
//        for (int i = 9; i > 4; i--) {
//            pw.println("beta" + i / 10f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.04f);
//            reputationCalculation.run(new CB_TB_L1_MIN(i / 10f));
//        }
//
//        pw.println("CB_TB_L1_AVG" + "  " + 0.03f);
//        for (int i = 7; i > 4; i--) {
//            pw.println("beta" + i / 10f);
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(0.03f);
//            reputationCalculation.run(new CB_TB_L1_AVG(i / 10f));
//        }

//        pw.println("TB_L1_AVG");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_L1_AVG());
//        }
//
//        pw.print("CB_TB_L1_AVG"+"  "+0.9f);
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new CB_TB_L1_AVG(0.9f));
//        }
//
//        pw.print("CB_TB_L1_AVG"+"  "+0.5f);
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new CB_TB_L1_AVG(0.9f));
//        }
//
//        pw.print("CB_TB_L1_AVG"+"  "+0.1f);
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new CB_TB_L1_AVG(0.9f));
//        }

//
//        pw.println("TB_L1_MAX");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_L1_MAX());
//        }
//
//        pw.println("TB_L1_MIN");
//        for(int i =1;i<10;i++){
//            reputationCalculation = new ReputationCalculation();
//            reputationCalculation.setLamda(i/100f);
//            reputationCalculation.run(new TB_L1_MIN());
//        }

    }

}
