package serde.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;

/**
 * Created by changhongzi on 2016/8/21.
 */
public class AvroWithoutCodeGen {
    public static void main(String[] args) throws Exception {
//        serializing();
//        deSerializing();
    }

    public static void serializing() throws Exception {
        Schema schema = new Schema.Parser().parse(new File("E:\\workspace\\github\\techlearning-demo\\src\\main\\java\\serde\\avro\\user.avsc"));
        GenericRecord user1 = new GenericData.Record(schema);
        user1.put("name", "Alyssa");
        user1.put("favorite_number", 256);

        GenericRecord user2 = new GenericData.Record(schema);
        user2.put("name", "Ben");
        user2.put("favorite_number", 7);
        user2.put("favorite_color", "red");

        // Serialize user1 and user2 to disk
        File file = new File("E:\\workspace\\github\\techlearning-demo\\src\\main\\java\\serde\\avro\\users.avro");
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.close();

    }

    public static void deSerializing() throws Exception {
        Schema schema = new Schema.Parser().parse(new File("E:\\workspace\\github\\techlearning-demo\\src\\main\\java\\serde\\avro\\user.avsc"));

        File file = new File("E:\\workspace\\github\\techlearning-demo\\src\\main\\java\\serde\\avro\\users.avro");
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
        GenericRecord user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user);
            System.out.println(user);
        }

    }
}
