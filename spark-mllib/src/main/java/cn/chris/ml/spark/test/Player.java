package cn.chris.ml.spark.test;

import java.io.Serializable;

/**
 * Created by MCWH-1283 on 2018/5/25.
 */
public class Player implements Serializable {
    private float ability; // 评分
    private float tired; // 疲劳
    private float status; // 状态
    private boolean starting; // 首发

    public double getScore() {
        return 0;
    }
}
