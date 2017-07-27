package com.axtha.inputnote;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CREATE = 1;

    ArrayList<String> form_names;

    EditText edt_form_name;

    ArrayAdapter<String> arrayAdapter;

    void ErrorMessage(NameCheck.Result result) {
        if (result == NameCheck.Result.REQUIRED) {
            Toast.makeText(getApplicationContext(), R.string.tst_error_required, Toast.LENGTH_LONG).show();
        } else if (result == NameCheck.Result.INVALID) {
            Toast.makeText(getApplicationContext(), R.string.tst_error_invalid, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        form_names = new ArrayList<String>();

        edt_form_name = (EditText) findViewById(R.id.edt_form_name);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, form_names);

        ListView lst_form = (ListView) findViewById(R.id.lst_form);
        lst_form.setAdapter(arrayAdapter);

        lst_form.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), form_names.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });

        Button btn_create = (Button) findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edt_form_name = (EditText) findViewById(R.id.edt_form_name);
                String form_name = edt_form_name.getText().toString();

                NameCheck.Result result = NameCheck.Do(form_name);
                if (result != NameCheck.Result.OK) {
                    ErrorMessage(result);
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                intent.putExtra("form_name", form_name);
                startActivityForResult(intent, REQUEST_CODE_CREATE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CREATE) {
            if (resultCode != RESULT_OK) {
                return;
            }

            edt_form_name.getText().clear();
            findViewById(R.id.constraintLayout).requestFocus();

            String form_name = data.getExtras().getString("form_name");
            form_names.add(form_name);

            arrayAdapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), R.string.tst_input_form_created, Toast.LENGTH_LONG).show();
        }
    }
}
