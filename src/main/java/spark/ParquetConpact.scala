import org.apache.avro.Schema
import org.apache.spark.sql.catalyst.expressions.GenericRow
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{Row, SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

object ParquetConpact {
    def main(args: Array[String]) {

        val sparkConf = new SparkConf().setMaster("local[4]").setAppName("test1")
        val sc = new SparkContext(sparkConf)
        val sqlContext = new SQLContext(sc)
        val parquetFile = "/tmp/*.parquet"
        val df = sqlContext.read.parquet(parquetFile)

        val DATA_AVRO_SCHEMA_TEXT: String = "[avro scehma context]"
        val schema = new Schema.Parser().parse(DATA_AVRO_SCHEMA_TEXT)
        val dtype1 = df.schema
        val dtype2 = dtype1
                .add("month", StringType, true)
                .add("day", StringType, true)

        val rdd1 = df.rdd.cache()

        val rdd2 = rdd1.map[Row](r => {
            val ts = r.getLong(0)
            val month: String = "add month value"
            val day: String = "add day value"
            val size = r.toSeq.size

            val values1 = new Array[Any](size + 2)
            for (i <- 0 until size) {
                values1(i) = r.get(i)
            }
            values1(30) = month
            values1(31) = day
            new GenericRow(values1)
        })

        println("##############" + rdd2.count())
        val df2 = sqlContext.createDataFrame(rdd2, dtype2)
        df2.printSchema()
        df2.show(10)
        df2.write.partitionBy("devicetype", "month", "day").mode(SaveMode.Append).parquet("/tmp/kafka2hdfs/list/controlOperateInfo/")
        sc.stop()
    }
}
