package com.mk.volleynetworkrequests;

public class Application {
    private String url;
    private static Application mInstance;
    private Application() {
        url = "http://192.168.60.17:8000";
    }

    public static synchronized Application getInstance() {
        if(mInstance == null) {
            mInstance = new Application();
        }
        return mInstance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
