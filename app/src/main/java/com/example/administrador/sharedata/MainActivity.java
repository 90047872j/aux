package com.example.administrador.sharedata;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {



    EditText et_source;
    Button b_obrir;
    Button b_compartir;
    Button b_web;
    Button b_email;
    Button b_maps;
    Button b_save;
    Button b_date;
    SharedPreferences prefs;


    Calendar avui = Calendar.getInstance();
    Calendar c = Calendar.getInstance();

    int pDay = avui.get(Calendar.DAY_OF_MONTH);
    int pMonth = avui.get(Calendar.MONTH) + 1;
    int pYear = avui.get(Calendar.YEAR);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_source = (EditText) findViewById(R.id.editTextMain);
        b_obrir = (Button)findViewById(R.id.obrirButton);
        b_compartir = (Button)findViewById(R.id.shareButton);
        b_web = (Button)findViewById(R.id.webButton);
        b_email = (Button)findViewById(R.id.emailButton);
        b_maps = (Button)findViewById(R.id.mapsButton);
        b_save = (Button)findViewById(R.id.saveButton);
        b_date = (Button)findViewById(R.id.dateButton);


        c.set(Calendar.YEAR, 1986);
        c.set(Calendar.MONTH, 7);
        c.set(Calendar.DAY_OF_MONTH, 6);



        prefs = getSharedPreferences("MyPreferencies", Context.MODE_PRIVATE);
        et_source.setText(prefs.getString("savedText",""));

        b_obrir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplication(),R.string.obrirButtonClicked, Toast.LENGTH_SHORT).show();
                passText(et_source.getText().toString().trim());
            }
        });


        b_compartir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, et_source.getText().toString().trim());
                startActivity(Intent.createChooser(intent,""));
            }
        });

        b_web.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String url = "https://www.google.es/search?q=";
                String final_url = url.concat(et_source.getText().toString().trim());
                Uri uri = Uri.parse(final_url);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        b_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, et_source.getText().toString().trim());
                startActivity(i);
            }
        });

        b_maps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               String url = "geo:0,0?q=";
                String final_url = url.concat(et_source.getText().toString().trim());
                Uri uri = Uri.parse(final_url);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(i);
            }
        });



        b_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("savedText", et_source.getText().toString());
                editor.commit();
                Toast.makeText(getApplication(), getString(R.string.savedButtonClicked).replace("NOMBRE",et_source.getText()), Toast.LENGTH_SHORT).show();
            }
        });

        b_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog dateDialog = new DatePickerDialog(MainActivity.this, pDateSetListener, pYear, pMonth, pDay);
                dateDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                dateDialog.show();
            }
        });
    }

    public void passText (String message){
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("result_text", message);
        startActivity(intent);
    }


    private DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            pYear = year;
            pMonth = monthOfYear;
            pDay = dayOfMonth;
            String fromDay = Integer.toString(pDay);
            String fromMonth = Integer.toString(pMonth);
            if (fromDay.length() == 1) fromDay = "0" + fromDay;
            if (fromMonth.length() == 1) fromMonth = "0" + fromMonth;
            et_source.setText(fromDay + "/" + fromMonth + "/" + pYear);
        }
    };
  }



