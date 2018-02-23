package cn.chris.ml.spark.test;

import com.google.common.collect.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.util.List;

/**
 * Created by MCWH-1283 on 2018/2/11.
 */
public class Test {
    public static void main(String[] arg) {
        SparkConf conf = new SparkConf();
        conf.set("spark.testing.memory","2147480000");
        JavaSparkContext sc = new JavaSparkContext("local[*]", "Spark", conf);

        // 训练集
        LabeledPoint pos = new LabeledPoint(0.0, Vectors.dense(4.0, 100.0, 30.0));
        LabeledPoint neg = new LabeledPoint(1.0, Vectors.sparse(3, new int[]{0, 2}, new double[]{1.0, 3.0}));
        LabeledPoint neg2 = new LabeledPoint(2.0, Vectors.sparse(3, new int[]{1, 2}, new double[]{4.0, 7.0}));
        List list = Lists.newLinkedList();
        list.add(pos);
        list.add(neg);
        list.add(neg2);
        JavaRDD<LabeledPoint> training = sc.parallelize(list);

        // 测试集
        double[] d = {1, 0, 3};
        Vector v = Vectors.dense(d);

        // 朴素贝叶斯
        final NaiveBayesModel nbModel = NaiveBayes.train(training.rdd());
        System.out.println(nbModel.predict(v));// 分类结果
        System.out.println(nbModel.predictProbabilities(v)); // 计算概率值
    }
}
