package cn.chris.ml.spark.test;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

/**
 * Created by MCWH-1283 on 2018/2/11.
 */
public class Test {
    public static void main(String[] arg) {
        SparkConf conf = new SparkConf();
        conf.set("spark.testing.memory","2147480000");
        JavaSparkContext sc = new JavaSparkContext("local[*]", "Spark", conf);

        JavaRDD<String> data = sc.textFile("data.txt");
        JavaRDD<LabeledPoint>[] splits = data.map(new Function<String, LabeledPoint>() {
            public LabeledPoint call(String s) throws Exception {
                String[] parts = s.split(",");
                String[] part1 = parts[1].split(" ");
                double[] vector = new double[part1.length];
                for (int i = 0; i < vector.length; i++) {
                    vector[i] = NumberUtils.toDouble(part1[i]);
                }
                return new LabeledPoint(NumberUtils.toDouble(parts[0]), Vectors.dense(vector));
            }
        }).randomSplit(new double[]{0.5, 0.5});

        JavaRDD<LabeledPoint> training = splits[0];
        JavaRDD<LabeledPoint> test = splits[1];

        // 朴素贝叶斯
        final NaiveBayesModel nbModel = NaiveBayes.train(training.rdd());

        // 测试集
        JavaRDD<double[]> predictionAndLabel = test.map(new Function<LabeledPoint, double[]>() {
            public double[] call(LabeledPoint labeledPoint) throws Exception {
                double prediction = nbModel.predict(labeledPoint.features());
                double label = labeledPoint.label();
                return new double[] {prediction, label};
            }
        });

        double accuracy = 1.0d * predictionAndLabel.filter(new Function<double[], Boolean>() {
            public Boolean call(double[] doubles) throws Exception {
                return doubles[0] == doubles[1];
            }
        }).count() / test.count();

//        double[] d = {1.46, 4.78, 6.89, 1.47, 4.79, 6.81, 1.49, 4.81, 6.67};
        double[] d = {3.32, 3.01, 2.55, 3.31, 3.3, 2.52, 3.15, 3.15, 2.66};
        System.out.println(accuracy);
        System.out.println(nbModel.predict(Vectors.dense(d)));// 分类结果
    }
}
