package cn.chris.ml.spark.test;

import java.io.Serializable;

/**
 * Created by MCWH-1283 on 2018/5/29.
 */
public class Rank implements Serializable {
    private int teamAmount;
    private int topN1;
    private int topN1Score;
    private int topN1Cost;
    private int topN2;
    private int topN2Score;
    private int topN2Cost;
    private int topN3;
    private int topN3Score;
    private int topN3Cost;
    private int latterN;
    private int latterNScore;
    private int latterNCost;
    private int round;
    private int score;
    private int relationship; // 与对手的关系 负值：友好 正值：敌视

    public double getScore() {
        return 0d;
    }
}
