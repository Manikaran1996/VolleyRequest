package com.mk.volleynetworkrequests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestQueue {
    private RequestQueue requestQueue;
    private static VolleyRequestQueue volleyRequestQueue;
    private Context context;

    private VolleyRequestQueue(Context ctxt) {
        this.context = ctxt;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyRequestQueue getInstance(Context context) {
        if(volleyRequestQueue == null) {
            volleyRequestQueue = new VolleyRequestQueue(context.getApplicationContext());
        }
        return volleyRequestQueue;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public<T> void addToRequestQueue(Request<T> request) {
        if(requestQueue == null) {
            requestQueue = getRequestQueue();
        }
        requestQueue.add(request);
    }
}
