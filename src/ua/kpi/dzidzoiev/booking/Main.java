package ua.kpi.dzidzoiev.booking;

import sun.misc.IOUtils;
import ua.kpi.dzidzoiev.booking.controller.dao.CityDao;
import ua.kpi.dzidzoiev.booking.controller.dao.MySqlCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.security.Security;

public class Main {

    public static void main(String[] args) throws Exception {
        String urlString = "https://sa-1236541526.ipaas.ipanematech.com/images/logo-1236541526.png";
        URL url = new URL(urlString);
        int _port = 443;
        int _timeout = 300;

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();

        Socket _socket = new Socket();
        _socket.connect(new InetSocketAddress(url.getHost(), _port), _timeout);

        SSLSocket sslsocket = (SSLSocket) factory.createSocket(_socket, "", _port, true);
        sslsocket.setUseClientMode(true);
        sslsocket.setSoTimeout(_timeout);
        sslsocket.startHandshake();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        InputStream is = connection.getInputStream();
        Image image = ImageIO.read(is);


        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }
}