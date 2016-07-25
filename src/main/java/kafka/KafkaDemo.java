package kafka;

import kafka.admin.TopicCommand;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by changhongzi on 2016/7/15.
 */
public class KafkaDemo {
    public static void main(String[] args) {
        consume();
    }


    public static void consume() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "10.159.32.201:2181");
        props.put("group.id", "test_11111111113");
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
//        props.put("auto.offset.reset", "smallest");

        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);

        String topic = "MP_D___DEV_STATUS_REPORT_NORMAL";


        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> objs = it.next();
            String key = new String(objs.key());
            String message = new String(objs.message());
            System.out.println(key + "==============================" + message);
//            try {
//                FileUtils.write(new File("F:\\work\\空调节能\\airconditioning-energesaveing.txt"),message,"utf-8",true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    public static void list() {
        String[] options = new String[]{
                "--list",
                "--zookeeper",
                "10.159.32.203:2181"
        };
        TopicCommand.main(options);
        String a = "MP_D_01001001__DEV_STATUS_REPORT_NORMAL";
    }

    public static void describe() {
        String[] options = new String[]{
                "--describe",
                "--zookeeper",
                "10.159.32.203:2181",
                "--topic",
                "MP_D_01001001__DEV_STATUS_REPORT_NORMAL",
        };
        TopicCommand.main(options);
    }
}
