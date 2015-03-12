package ua.kpi.dzidzoiev.booking;

import sun.misc.IOUtils;
import ua.kpi.dzidzoiev.booking.controller.dao.CityDao;
import ua.kpi.dzidzoiev.booking.controller.dao.MySqlCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.security.Security;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws Exception {
        Image image = null;
       // System.setProperty("jsse.enableSNIExtension", "false");
// http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
        String urlString = (args.length == 1) ?
                args[0] : "https://sa-1236541526.ipaas.ipanematech.com/images/logo-1236541526.png";
        URL url = new URL(urlString);
        String _host = url.getHost();
        int _port = 443;
        int _timeout = 300;

        Security.addProvider(
                new com.sun.net.ssl.internal.ssl.Provider());

        SSLSocketFactory factory =
                (SSLSocketFactory)SSLSocketFactory.getDefault();

        Socket _socket = new Socket();
        _socket.connect(new InetSocketAddress(_host, _port), _timeout);

        SSLSocket sslsocket = (SSLSocket) factory.createSocket(_socket, "", _port, true);
        sslsocket.setUseClientMode(true);
        sslsocket.setSoTimeout(_timeout);
        sslsocket.startHandshake();

        InputStream is = sslsocket.getInputStream();


        byte[] bytes = getBytes(is);
        System.out.println(Arrays.toString(bytes));
        image = ImageIO.read(sslsocket.getInputStream());


        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }

    static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }
}