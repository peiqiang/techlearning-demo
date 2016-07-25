package hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

/**
 * Created by changhongzi on 2016/7/24.
 */
public class RpcServer {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Server server = new RPC.Builder(conf)
                .setProtocol(MyProtocol.class)
                .setInstance(new MyProtocolImpl())
                .setBindAddress("127.0.0.1")
                .setPort(1002).build();
        server.start();
    }
}
