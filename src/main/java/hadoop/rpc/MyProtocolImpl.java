package hadoop.rpc;

import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.VersionedProtocol;

import java.io.IOException;

/**
 * Created by changhongzi on 2016/7/24.
 */
public class MyProtocolImpl implements MyProtocol{

    public String call(String params) {
        return "hello "+params;
    }

    public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
        return MyProtocol.versionID;
    }

    public ProtocolSignature getProtocolSignature(String protocol, long clientVersion, int clientMethodsHash) throws IOException {
        return new ProtocolSignature(MyProtocol.versionID, null);
    }
}
