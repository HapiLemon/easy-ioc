package org.example.demo.ioc.resources;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLResource implements Resource {

    private String configLocation;

    public URLResource(String configLocation) {
        this.configLocation = configLocation;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        URL url = this.getClass().getClassLoader().getResource(configLocation);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

}
