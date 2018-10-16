import org.apache.log4j.BasicConfigurator;
import tor_network.tor_onionproxy.JavaOnionProxyContext;
import tor_network.tor_onionproxy.JavaOnionProxyManager;
import tor_network.tor_onionproxy.OnionProxyManager;
import tor_network.tor_onionproxy.Utilities;
import tor_network.tor_jsocks.protocol.Socks5Proxy;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TorClientSocks5noAuth {

    public static void main(String[] args) throws IOException, InterruptedException {

        String fileStorageLocation = "src/main/gr/tor_network";
        BasicConfigurator.configure();
        OnionProxyManager onionProxyManager = new JavaOnionProxyManager(
                new JavaOnionProxyContext(new File(fileStorageLocation)));

        final int totalSecondsPerTorStartup = 4 * 60;
        final int totalTriesPerTorStartup = 5;

             // Start the Tor Onion Proxy
        if (onionProxyManager.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup) == false) {
            return;
        }
         // Start a hidden service listener
        final int hiddenServicePort = 80;
        final int localPort = 9150;
        String OnionAdress = "doqj3fyb5qjka7bb.onion";

        Socks5Proxy proxy = onionProxyManager.SetupSocks5Proxy(localPort);

        Socket clientSocket = Utilities.Socks5connection(proxy, OnionAdress, localPort);

        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        out.writeObject("i am workingg");
        out.flush();
    }
}