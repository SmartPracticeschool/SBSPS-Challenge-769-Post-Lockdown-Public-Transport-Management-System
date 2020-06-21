package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class search_option extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
 private Spinner spinner_mode;
 Button searc_btn;
 private ProgressBar loading;
 EditText from,to;
 String modeid,from_text,to_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_option);
        spinner_mode = (Spinner)findViewById(R.id.spinner_search);
        searc_btn = (Button)findViewById(R.id.search_btn);
        from = (EditText)findViewById(R.id.editText_from);
        to = (EditText)findViewById(R.id.editText_to);
        loading = (ProgressBar)findViewById(R.id.loader);
        searc_btn.setOnClickListener(this);
        spinner_mode.setOnItemSelectedListener(this);
        List<String> searchablespinnermode_list = new ArrayList<String>();
        searchablespinnermode_list.add("Select Mode Of Transport");
        searchablespinnermode_list.add("Bus");
        searchablespinnermode_list.add("Train");
        searchablespinnermode_list.add("Metro");
        spinner_mode.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, searchablespinnermode_list));

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            modeid = "";
        }
        if (position == 1) {
            String smode = parent.getItemAtPosition(position).toString();
            modeid = smode;
        }
        if (position == 2) {
            String smode = parent.getItemAtPosition(position).toString();
            modeid = smode;
        }
        if (position == 3) {
            String smode = parent.getItemAtPosition(position).toString();
            modeid = smode;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        loading.setVisibility(View.VISIBLE);
        from_text = from.getText().toString();
        to_text = to.getText().toString();
        if(from_text.isEmpty()){
            from.setError("Please input starting point");
            from.requestFocus();
            loading.setVisibility(View.GONE);
            return;
        }
        if(to_text.isEmpty()){
            to.setError("Please input final point");
            to.requestFocus();
            loading.setVisibility(View.GONE);
            return;
        }
        if(modeid.isEmpty()){
            spinner_mode.requestFocus();
            Toast.makeText(getApplicationContext(),"Please select your mode",Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            return;
        }
        Intent i = new Intent(search_option.this,mode_details.class);
        i.putExtra("passed_modeid", modeid);
        i.putExtra("passed_from", from_text);
        i.putExtra("passed_to", to_text);
        startActivity(i);

    }
}