package com.jarvis.jsonex;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {


    public ProgressDialog pd;
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject;
    private EditText enno, studname, sem;
    private Button btninsert, btnselect, btndelete, btnupdate, btnmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enno = findViewById(R.id.enno);
        studname = findViewById(R.id.studname);
        sem = findViewById(R.id.sem);
        btninsert = findViewById(R.id.btninsert);
        btnselect = findViewById(R.id.btnselect);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnmap = findViewById(R.id.btnmap);

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Pros().execute(enno.getText().toString(), studname.getText().toString(), sem.getText().toString());
            }
        });


        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(i);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(i);
            }
        });


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, UpdateActivity.class);
                startActivity(i);
            }
        });

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }


    class Pros extends AsyncTask<String, Void, String> {
        String success = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Inserting wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> nv = new ArrayList<NameValuePair>();
            nv.add(new BasicNameValuePair("enno", params[0]));
            nv.add(new BasicNameValuePair("studname", params[1]));
            nv.add(new BasicNameValuePair("sem", params[2]));

            jsonObject = jsonParser.makeHttpRequest("http://192.168.43.11/jsonproject/insert.php", nv);

            try {
                success = jsonObject.getString("yes");
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
               /*Intent i1 = new Intent(StudentsDetails.this, DisplayAllDetails.class);
                startActivity(i1);*/
                Toast.makeText(MainActivity.this, " Data is Inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Error in inserting data.", Toast.LENGTH_LONG).show();
            }
        }

    }

}
