package com.jarvis.jsonex;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DeleteActivity extends AppCompatActivity {


    EditText stdid;
    Button btndelstd;

    JSONParser jp = new JSONParser();
    JSONObject jo;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        stdid = findViewById(R.id.enno);

        btndelstd = findViewById(R.id.btndelstd);

        btndelstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Pros().execute(stdid.getText().toString());
            }
        });
    }


    class Pros extends AsyncTask<String, Void, String> {
        String success = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DeleteActivity.this);
            pd.setMessage("Deleting Data");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> nv = new ArrayList<NameValuePair>();
            nv.add(new BasicNameValuePair("stdid", params[0]));
            jo = jp.makeHttpRequest("http://192.168.43.11/jsonproject/delete.php", nv);
            try {

                success = jo.getString("yes");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pd.dismiss();

            if (s.equals("1")) {
            } else {
                Toast.makeText(DeleteActivity.this, "Error in fetching data.", Toast.LENGTH_LONG).show();
            }


        }
    }


}
