package serde.parquet;

import com.google.common.collect.ImmutableMap;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericFixed;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by changhongzi on 2016/8/21.
 */
public class AvroParquetWriterReader {
    private final static String BASE_PATH = "E:\\workspace\\github\\techlearning-demo\\src\\main\\java\\serde\\parquet";

    public static void main(String[] args) throws Exception {
        System.setProperty("hadoop.home.dir", "D:\\Program Files\\Java\\jdk1.7.0_80");
        write();
//        read();
    }

    private static void read() throws Exception {
        Path file = new Path(BASE_PATH + "\\all.parquet");
        AvroParquetReader<GenericRecord> reader = new AvroParquetReader<GenericRecord>(file);
        GenericRecord nextRecord = reader.read();
        System.out.println(nextRecord);
    }

    private static void write() throws Exception {
        Schema schema = new Schema.Parser().parse(new FileInputStream(BASE_PATH + "\\all.avsc"));
        Path file = new Path(BASE_PATH + "\\all.parquet");
        AvroParquetWriter<GenericRecord> writer = new AvroParquetWriter<GenericRecord>(file, schema, CompressionCodecName.SNAPPY,AvroParquetWriter.DEFAULT_BLOCK_SIZE,AvroParquetWriter.DEFAULT_PAGE_SIZE);

        GenericData.Record nestedRecord = new GenericRecordBuilder(
                schema.getField("mynestedrecord").schema())
                .set("mynestedint", 1).build();

        List<Integer> integerArray = Arrays.asList(1, 2, 3);
        GenericData.Array<Integer> genericIntegerArray = new GenericData.Array<Integer>(
                Schema.createArray(Schema.create(Schema.Type.INT)), integerArray);

        GenericFixed genericFixed = new GenericData.Fixed(
                Schema.createFixed("fixed", null, null, 1), new byte[]{(byte) 65});

        List<Integer> emptyArray = new ArrayList<Integer>();
        ImmutableMap<String, Integer> emptyMap = new ImmutableMap.Builder<String, Integer>().build();

        GenericData.Record record = new GenericRecordBuilder(schema)
                .set("mynull", null)
                .set("myboolean", true)
                .set("myint", 1)
                .set("mylong", 2L)
                .set("myfloat", 3.1f)
                .set("mydouble", 4.1)
                .set("mybytes", ByteBuffer.wrap("hello".getBytes()))
                .set("mystring", "hello")
                .set("mynestedrecord", nestedRecord)
                .set("myenum", "a")
                .set("myarray", genericIntegerArray)
                .set("myemptyarray", emptyArray)
                .set("myoptionalarray", genericIntegerArray)
                .set("mymap", ImmutableMap.of("a", 1, "b", 2))
                .set("myemptymap", emptyMap)
                .set("myfixed", genericFixed)
                .build();
        writer.write(record);
        writer.close();
    }
}
