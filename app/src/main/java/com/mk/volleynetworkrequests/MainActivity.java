package com.mk.volleynetworkrequests;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText numberEt;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberEt = findViewById(R.id.editText1);
        result = findViewById(R.id.result);
        Button sendRequestBtn = findViewById(R.id.sendRequest);
        Button navigateToJSONRequestBtn = findViewById(R.id.navigateToJSONRequest);
        sendRequestBtn.setOnClickListener(this);
        navigateToJSONRequestBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendRequest: {
                int n;
                try {
                    n = Integer.parseInt(String.valueOf(numberEt.getText()).trim());
                    sendRequest(n);
                } catch (NumberFormatException exception) {
                    Toast.makeText(this, "Enter a valid Integer", Toast.LENGTH_SHORT).show();
                }

            }
                break;

            case R.id.navigateToJSONRequest: {
                Intent intent = new Intent(this, JSONRequest.class);
                startActivity(intent);
            }
                break;
        }
    }

    private void sendRequest(final int n) {
        String url = Application.getInstance().getUrl();
        if(url != null)
                url += "/factorial";
        else
            return;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("number", String.valueOf(n));
                return map;
            }
        };
        VolleyRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);
    }

}
