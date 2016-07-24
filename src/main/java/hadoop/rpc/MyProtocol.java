package hadoop.rpc;

import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.VersionedProtocol;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by changhongzi on 2016/7/24.
 */
public interface MyProtocol extends VersionedProtocol{
    public static final long versionID = 1L;

    public String call(String params);
}
