package com.example.nalot.service.data;

import com.example.nalot.dao.LocationDao;
import com.example.nalot.dao.TrendDao;
import com.example.nalot.model.data.TrendData;
import com.example.nalot.model.data.TrendDto;
import com.example.nalot.model.weather.LocationDto;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.expressions.GenericRow;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import scala.collection.Seq;
import scala.collection.Seq$;

import java.util.ArrayList;
import java.util.List;

import static org.apache.spark.sql.functions.*;


@Service
public class DataServiceImpl implements DataService{

    private final TrendDao trendDao;
    private final LocationDao locationDao;
    private final JavaSparkContext sc;

    public DataServiceImpl(TrendDao trendDao, LocationDao locationDao, JavaSparkContext sc) {
        this.trendDao = trendDao;
        this.locationDao = locationDao;
        this.sc = sc;
    }

    SparkSession spark = SparkSession
            .builder()
            .appName("Java Spark SQL basic example")
            .config("spark.some.config.option", "some-value")
            .getOrCreate();

    @Override
    public Dataset<Row> getWeatherDataset() {
        Dataset<Row> peopleDFCsv = spark.read().format("csv")
                .option("sep", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("backend/src/main/resources/data/temperature1.csv")
                .filter(expr("hour>0"));

        return peopleDFCsv;
    }


    @Override
    public Dataset<Row> refineDataSet(Dataset<Row> df) {

        Dataset<Row> df2 = df.withColumn(" format: day",col(" format: day").cast("int"))
                .withColumnRenamed(" format: day","day")
                .withColumnRenamed("value location:91_78 Start : 20170801 ","value")
                .withColumn("month",expr("month*100+day"))
                .withColumnRenamed("month","date")
                .groupBy("date", "location").agg(avg("value").alias("avg"),
                min("value").alias("min"), max("value").alias("max"));

        return df2;
    }

    @Override
    public Dataset<Row> getLocationDataset(Dataset<Row> ds){

        Dataset<Row> avg = ds.groupBy("date", "location").agg(avg("value").alias("avg"),
                min("value").alias("min"), max("value").alias("max"));


        return avg;
    }

    @Override
    public Dataset<Row> getClothesDataset() {
        Dataset<Row> peopleDFCsv = spark.read().format("csv")
                .option("sep", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("backend/src/main/resources/data/clothes.csv");

        return peopleDFCsv;
    }

    @Override
    public Dataset<Row> refineClothesData(Dataset<Row> df) {
        Dataset<Row> df2 = df.withColumnRenamed("날짜","date")
                .withColumnRenamed("검색횟수","search")
                .withColumnRenamed("옷종류","clothes")
                .withColumn("date", expr("regexp_replace(date,\"-\",\"\")"))
                .withColumn("date",col("date").cast("int"));
        return df2;
    }


    public Dataset<Row> getAvgSearch(Dataset<Row> a) {
        Dataset<Row> df = a.groupBy(col("clothes")).avg("search");
        df.show();
        return null;
    }

    @Override
    public Dataset<Row> refineTrainData(Dataset<Row> result) {

        Dataset<Row> avg = result.groupBy(col("clothes")).agg(avg("search").alias("avg2"));

        Dataset<Row> join = result.join(avg,"clothes");
        Dataset<Row> df5 = join.withColumn("search",col("search").cast("double")).filter("search > avg2");

        Dataset<Row> midhigh = df5.groupBy(col("clothes")).agg(avg("search").alias("midhigh"));
        Dataset<Row> df57 = join.withColumn("search",col("search").cast("double")).filter("search < avg2");

        Dataset<Row> midlow = df57.groupBy(col("clothes")).agg(avg("search").alias("midlow"));
        Dataset<Row> df58 = join.join(midhigh,"clothes").join(midlow,"clothes");

        Dataset<Row> trend = df58.select(col("clothes"),col("date"),col("location"),
                col("avg"), col("search"),
                when(expr("search > midhigh"),3)
                        .when(expr("search > avg2 AND search <= midhigh"),2)
                        .when(expr("search > midlow  AND search <= avg2"),1)
                        .when(expr("search < midlow"),0).cast("double").alias("trend"));


        Dataset<Row> df52 = trend.withColumn("month",df5.col("date").substr(5,2).cast("int"))
                .select(col("clothes"), col("date"), col("month"), col("location"), col("avg")
                        , col("search") , col("trend")
                        ,when(expr("month == 12 OR month == 1 OR month == 2"),"winter")
                                .when(expr("month == 3 OR month == 4 OR month == 5"),"spring")
                                .when(expr("month == 6 OR month == 7 OR month == 8"),"summer")
                                .when(expr("month == 9 OR month == 10 OR month == 11"),"fall").alias("season"));
//                        ,when(expr("location == '서울특별시'"),1.0)
//                                .when(expr("location == '부산광역시'"),2.0)
//                                .when(expr("location == '대구광역시'"),3.0)
//                                .when(expr("location == '인천광역시'"),4.0)
//                                .when(expr("location == '광주광역시'"),5.0)
//                                .when(expr("location == '대전광역시'"),6.0)
//                                .when(expr("location == '울산광역시'"),7.0)
//                                .when(expr("location == '세종특별자치시'"),8.0)
//                                .when(expr("location == '경기도'"),9.0)
//                                .when(expr("location == '강원도'"),10.0)
//                                .when(expr("location == '충청북도'"),11.0)
//                                .when(expr("location == '충청남도'"),12.0)
//                                .when(expr("location == '전라북도'"),13.0)
//                                .when(expr("location == '전라남도'"),14.0)
//                                .when(expr("location == '경상북도'"),15.0)
//                                .when(expr("location == '경상남도'"),17.0)
//                                .when(expr("location == '제주특별자치도'"),18.0)
//                                .when(expr("location == '이어도'"),19.0).alias("locationNum"));
        //x-평균 / 표준편차
        //평균 -> 일평균의 평균
        Dataset<Row> dayavgavg = df52.select(avg(col("avg")));

        //표준편차 공식 -> 일평균의 표준편차
        Dataset<Row> stddev = df52.select(stddev(col("avg")));


        //x-평균 / 표준편차 적용 -> Z정규화

        //column 생성
        Dataset<Row> zscore = df52.withColumn("dayavgavg", lit(dayavgavg.collectAsList().get(0).get(0)))
                .withColumn("stddev", lit(stddev.collectAsList().get(0).get(0)))
                .withColumn("zscore", expr("(avg-dayavgavg)/stddev"));

        Dataset<Row> processed = zscore.withColumnRenamed("avg", "value")
                .drop("dayavgavg", "stddev");
//        result.coalesce(1).write().format("csv").option("header", "true").
//                save("backend/src/main/resources/data/data.csv");
        return processed;
    }

    @Override
    public LinearRegressionModel makeTrainModel(Dataset<Row> train) {

        LinearRegression linearRegression = new LinearRegression();
        LinearRegressionModel model =  linearRegression.setMaxIter(30)
                .setRegParam(0.3)
                .setLabelCol("trend")
                .setFeaturesCol("feature")
                .fit(train);


        return model;
    }

    @Override
    public Dataset<Row> extractFeature(Dataset<Row> dataset) {
        StringIndexer stringIndexer = new StringIndexer();
        Dataset<Row> df6 = stringIndexer.setInputCol("location")
                .setOutputCol("locationIndex")
                .fit(dataset).transform(dataset);

        Dataset<Row> df67 = stringIndexer.setInputCol("clothes")
                .setOutputCol("clothesIndex")
                .fit(df6).transform(df6);

        Dataset<Row> df68 = stringIndexer.setInputCol("season")
                .setOutputCol("seasonIndex")
                .fit(df67).transform(df67);

        Dataset<Row> location = modifyLocation(df68);

        OneHotEncoder oneHotEncoder = new OneHotEncoder();

        Dataset<Row> df7 = oneHotEncoder.setInputCol("locationIndex")
                .setOutputCol("locationVex")
                .fit(location).transform(location);

        Dataset<Row> df77 = oneHotEncoder.setInputCol("clothesIndex")
                .setOutputCol("clothesVex")
                .fit(df7).transform(df7);

        Dataset<Row> df78 = oneHotEncoder.setInputCol("seasonIndex")
                .setOutputCol("seasonVex")
                .fit(df77).transform(df77);

        return df78;
    }

    @Override public Dataset<Row> modifyLocation(Dataset<Row> dataset){
        Dataset<Row> location = spark.read().format("csv")
                .option("sep", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("backend/src/main/resources/data/location/location.csv");
        Dataset<Row> loc = location.withColumnRenamed("locationIndex","locationIndex2");
        Dataset<Row> result = dataset.filter(expr("trend>=10")).join(loc,"location");
        result.drop("locationIndex").withColumnRenamed("locationIndex2","locationIndex");
        result.show();

        return result;
    }

    @Override
    public Dataset<Row> getPrediction(LinearRegressionModel model, Dataset<Row> dataset) {
        VectorAssembler vectorAssembler = new VectorAssembler();
        vectorAssembler.setInputCols(new String[]{"locationVex","clothesVex","seasonVex","month", "zscore"})
                .setOutputCol("feature");

        Pipeline pipeline =new Pipeline();
        Dataset<Row> df8 = pipeline.setStages(new PipelineStage[]{(PipelineStage) vectorAssembler}).fit(dataset).transform(dataset)
                .na().fill(0);


        Dataset<Row> finalData = df8.select("trend","feature");

        return model.transform(df8);
    }

    @Override
    public double getAccuracy(Dataset<Row> prediction) {
        BinaryClassificationEvaluator binaryClassificationEvaluator = new BinaryClassificationEvaluator();
        double accuracy = binaryClassificationEvaluator.setRawPredictionCol("prediction")
                .setLabelCol("trend")
                .evaluate(prediction);

        return accuracy;
    }

    @Override
    public Dataset<TrendDto> addDataSet(String date, String location, float temperature ) {
        int month = Integer.parseInt(date.substring(4,6));
        double value = (double) temperature;
        double zScore = this.calcZscore(value, this.getAverage(), this.getStdDev());


        List<TrendDto> trendDataList = trendDao.selectTrendList();
        List<String> list = trendDao.selectClothesList();

        Encoder<TrendDto> encoder = Encoders.bean(TrendDto.class);

        int i = 1;

        for(String clothes : list) {
            int j = 10 * i;
            i++;
            TrendDto trendData = new TrendDto();
            trendData.setClothes(clothes);
            trendData.setLocation(location);
            trendData.setValue(value);
            trendData.setMonth(month);
            trendData.setZscore(zScore);
            trendData.setTrend(j);
            trendData.setDate(date);


            if(month == 12 || month == 1 || month == 2) { trendData.setSeason("winter"); }
            else if(month == 3 || month == 4 || month == 5) { trendData.setSeason("spring"); }
            else if(month == 6 || month == 7 || month == 8) { trendData.setSeason("summer"); }
            else if(month == 9 || month == 10 || month == 11) { trendData.setSeason("fall"); }

            trendDataList.add(trendData);
        }
        Dataset<TrendDto> dataset = spark.createDataset(trendDataList, encoder);
        return dataset;
    }

    public double calcZscore(double x, double avg, double stddev) {
        return (x-avg) / stddev;
    }

    @Override
    public double getAverage() { return trendDao.getAverage(); }

    @Override
    public double getStdDev() { return trendDao.getStdDev(); }

    @Override
    public Dataset<Row> recommendTrend(LinearRegressionModel model, Dataset<TrendDto> trend) {
        Dataset<Row> dataset = trend.select("*");
        Dataset<Row> test = this.extractFeature(dataset);
        return this.getPrediction(model, test);
    }

    @Override
    public LinearRegressionModel loadLinearRegressionModel() {
        return LinearRegressionModel.load("backend/src/main/resources/data/model");

    }
    public List<TrendDto> selectTrendList(){ return trendDao.selectTrendList();}

    @Override
    public Dataset<TrendDto> makeDataset(){
        //select to list
        List<TrendDto> selectvalue = selectTrendList();
        //list to dataset
        Encoder<TrendDto> encoder = Encoders.bean(TrendDto.class);
        Dataset<TrendDto> dataset = spark.createDataset(selectvalue, encoder);

        return dataset;
    }
}
