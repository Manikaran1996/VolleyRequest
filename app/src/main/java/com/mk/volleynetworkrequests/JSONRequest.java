package com.mk.volleynetworkrequests;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONRequest extends AppCompatActivity implements View.OnClickListener {

    private EditText editText1, editText2;
    private TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_request_activity);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        result = findViewById(R.id.result);
        Button navigateToImageRequestBtn = findViewById(R.id.navigateToImageRequest);
        Button sendRequestBtn = findViewById(R.id.sendRequest);
        navigateToImageRequestBtn.setOnClickListener(this);
        sendRequestBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sendRequest:
                int n=0, r=0;
                try {
                    n = Integer.parseInt(String.valueOf(editText1.getText()));
                }
                catch (NumberFormatException e) {
                    Toast.makeText(this, "Enter valid value of n (an Integer)", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    r = Integer.parseInt(String.valueOf(editText2.getText()));
                }
                catch (NumberFormatException e) {
                    Toast.makeText(this, "Enter valid value of n (an Integer)", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendRequest(n, r);
                break;
            case R.id.navigateToImageRequest:
                Intent intent = new Intent(this, VolleyImageRequest.class);
                startActivity(intent);
        }
    }

    private void sendRequest(int n, int r) {
        String url = Application.getInstance().getUrl();
        if(url != null)
            url += "/combination";
        else
            return;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("n", n);
            jsonObject.put("r", r);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            result.setText(response.getString("result"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JSONRequest.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
