package hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.net.NetUtils;

import java.net.InetSocketAddress;

/**
 * Created by changhongzi on 2016/7/24.
 */
public class RpcClient {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1",1002);
        MyProtocol proxy = RPC.getProxy(MyProtocol.class, MyProtocol.versionID, addr, conf);

        String ret = proxy.call("world");
        System.out.println(ret);
    }
}
