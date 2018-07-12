package com.jarvis.jsonex;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    EditText stdid;
    Button btngetstd;
    TextView txtstdname, txtseme;

    JSONParser jp = new JSONParser();
    JSONObject jo;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        stdid = findViewById(R.id.enno);
        txtstdname = findViewById(R.id.txtstdname);
        txtseme = findViewById(R.id.txtseme);

        btngetstd = findViewById(R.id.btngetstd);

        btngetstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Pros().execute(stdid.getText().toString());
            }
        });
    }

    class Pros extends AsyncTask<String, Void, String> {
        String success = null, txtStudent, txtSemester;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SelectActivity.this);
            pd.setMessage("Fetching Data");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> nv = new ArrayList<NameValuePair>();
            nv.add(new BasicNameValuePair("stdid", params[0]));
            jo = jp.makeHttpRequest("http://192.168.43.11/jsonproject/select.php", nv);
            try {

                success = jo.getString("yes");
                txtStudent = jo.getString("StudentName");
                txtSemester = jo.getString("Sem");


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
                txtstdname.setText(txtStudent);
                txtseme.setText(txtSemester);

                txtstdname.setTextSize(30);
                txtseme.setTextSize(30);
                txtstdname.setTextColor(Color.GREEN);
                txtseme.setTextColor(Color.BLUE);
                Toast.makeText(SelectActivity.this, "First Name Temp :  " + txtstdname, Toast.LENGTH_LONG).show();
                Toast.makeText(SelectActivity.this, "Last Name Temp:  " + txtseme, Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(SelectActivity.this, "Error in fetching data.", Toast.LENGTH_LONG).show();
            }


        }
    }


}

