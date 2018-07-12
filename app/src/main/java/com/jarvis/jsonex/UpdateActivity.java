package com.jarvis.jsonex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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

/**
 * Created by JARVIS on 2/6/2018.
 */

public class UpdateActivity extends AppCompatActivity {

    public ProgressDialog pd;
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject;

    EditText edittextEnno, edittextstdname, edittextseme;

    private EditText stdid;

    private Button btnselectstdupdate, btnconfirmdupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        stdid = findViewById(R.id.stdid);

        edittextEnno = findViewById(R.id.edittextEnno);
        edittextstdname = findViewById(R.id.edittextstdname);
        edittextseme = findViewById(R.id.edittextseme);
        btnselectstdupdate = findViewById(R.id.btnselectstdupdate);
        btnconfirmdupdate = findViewById(R.id.btnconfirmdupdate);
        //Invisible all
        edittextEnno.setVisibility(View.GONE);
        edittextstdname.setVisibility(View.GONE);
        edittextseme.setVisibility(View.GONE);
        btnconfirmdupdate.setVisibility(View.GONE);


        btnselectstdupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProsSelect().execute(stdid.getText().toString());
            }
        });

        btnconfirmdupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProsUpdate().execute(edittextEnno.getText().toString(), edittextstdname.getText().toString(), edittextseme.getText().toString());

            }
        });

    }


    class ProsSelect extends AsyncTask<String, Void, String> {
        String success = null, txtEnno, txtStudent, txtSemester;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(UpdateActivity.this);
            pd.setMessage("Selecting Data");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> nv = new ArrayList<NameValuePair>();
            nv.add(new BasicNameValuePair("stdid", params[0]));
            jsonObject = jsonParser.makeHttpRequest("http://192.168.43.11/jsonproject/select.php", nv);
            try {

                success = jsonObject.getString("yes");
                txtEnno = jsonObject.getString("EnNo");
                txtStudent = jsonObject.getString("StudentName");
                txtSemester = jsonObject.getString("Sem");


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

                edittextEnno.setVisibility(View.VISIBLE);
                edittextstdname.setVisibility(View.VISIBLE);
                edittextseme.setVisibility(View.VISIBLE);
                btnconfirmdupdate.setVisibility(View.VISIBLE);


                edittextEnno.setText(txtEnno);
                edittextstdname.setText(txtStudent);
                edittextseme.setText(txtSemester);

                edittextEnno.setTextSize(30);
                edittextstdname.setTextSize(30);
                edittextseme.setTextSize(30);

                edittextEnno.setTextColor(Color.BLACK);
                edittextstdname.setTextColor(Color.GREEN);
                edittextseme.setTextColor(Color.BLUE);

                //Toast.makeText(UpdateActivity.this,"Enno:  "+txtenno,Toast.LENGTH_LONG).show();
                //Toast.makeText(UpdateActivity.this,"Student :  "+txtstdname,Toast.LENGTH_LONG).show();
                //Toast.makeText(UpdateActivity.  this,"Sem:  "+txtseme,Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(UpdateActivity.this, "Error in Selecting data.", Toast.LENGTH_LONG).show();
            }


        }
    }

    class ProsUpdate extends AsyncTask<String, Void, String> {
        String success = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(UpdateActivity.this);
            pd.setMessage("Updating Data");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> nv = new ArrayList<NameValuePair>();
            nv.add(new BasicNameValuePair("edittextEnno", params[0]));
            nv.add(new BasicNameValuePair("edittextstdname", params[1]));
            nv.add(new BasicNameValuePair("edittextseme", params[2]));

            jsonObject = jsonParser.makeHttpRequest("http://192.168.43.11/jsonproject/update.php", nv);
            try {

                success = jsonObject.getString("yes");
                //utxtEnno = jsonObject.getString("EnNo");
                // utxtStudent=jsonObject.getString("StudentName");
                // utxtSemester=jsonObject.getString("Sem");


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
               /* edittextEnno.setText(txtEnno);
                edittextstdname.setText(txtStudent);
                edittextseme.setText(txtSemester);

                edittextEnno.setTextSize(30);
                edittextstdname.setTextSize(30);
                edittextseme.setTextSize(30);

                edittextEnno.setTextColor(Color.BLACK);
                edittextstdname.setTextColor(Color.GREEN);
                edittextseme.setTextColor(Color.BLUE);
              */
                //Toast.makeText(UpdateActivity.this,"Enno:  "+txtenno,Toast.LENGTH_LONG).show();
                //Toast.makeText(UpdateActivity.this,"Student :  "+txtstdname,Toast.LENGTH_LONG).show();
                //Toast.makeText(UpdateActivity.  this,"Sem:  "+txtseme,Toast.LENGTH_LONG).show();
                Toast.makeText(UpdateActivity.this, "Updated Successfully.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);


            } else {
                Toast.makeText(UpdateActivity.this, "Error in Selecting data.", Toast.LENGTH_LONG).show();
            }


        }
    }


}
