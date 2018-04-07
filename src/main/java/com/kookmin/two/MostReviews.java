package com.kookmin.two;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
 
/**
 * JobConf를 통해서 Hadoop의 MapReduce를 이용할 것이며, JobConf가 알아서 map function과 reducer funtion call
 * JobConf를 통해서 Mapper와 Reducer의 input 그리고 output Type을 설정
 */
public class MostReviews {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        if (args.length != 2) {
            System.out.println("Usage: MostReviews <input> <output>");
            System.exit(2);
            
        }
        Job job = new Job(conf, "MostReviews");
        job.setJarByClass(MostReviews.class); 
        job.setMapperClass(MostReviewsMapper.class);
        job.setReducerClass(MostReviewsReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
         
        job.setOutputKeyClass(Text.class); //맵에서 받아올 key value type 정해주기
        job.setOutputValueClass(IntWritable.class);
         
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true); // 실행
    }
}
