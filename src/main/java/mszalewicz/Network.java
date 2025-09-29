package mszalewicz;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Network {
    private static Database database;
    private static final int PORT = 49169;
    private static final String SERVICE_TYPE = "_terminus._tcp.local.";
    private static String SERVICE_NAME;

    private Network() {}

//    public static synchronized void init(Database appDatabase) {
//            database = appDatabase;
//        // TODO: broadcast app on the local network via mDNS/DNS‑SD
//    }


//   public static localApplicationInstances()  {
//       try {
//           Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//       } catch (SocketException e) {
//           throw new RuntimeException(e);
//       }
//   }

}
