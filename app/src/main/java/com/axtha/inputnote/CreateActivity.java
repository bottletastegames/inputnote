package com.axtha.inputnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_FIELD = 2;

    // my own var
    ArrayList<String> field_names;
    ArrayList<FieldType> field_types;

    // vars on requested intent
    String form_name;

    // adapter
    ArrayAdapter<String> arrayAdapter;

    // views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        field_names = new ArrayList<String>();
        field_types = new ArrayList<FieldType>();

        Intent intent = getIntent();
        form_name = intent.getExtras().getString("form_name");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, field_names);

        ListView lst_field = (ListView) findViewById(R.id.lst_field);
        lst_field.setAdapter(arrayAdapter);

        Button btn_add_field = (Button) findViewById(R.id.btn_add_field);
        btn_add_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FieldActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FIELD);
            }
        });

        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("form_name", form_name);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FIELD) {
            if (resultCode != RESULT_OK) {
                return;
            }

            String field_name = data.getExtras().getString("field_name");

            int size = field_names.size();
            for (int i = 0; i < size; ++i) {
                if (field_name.equals(field_names.get(i))) {
                    Toast.makeText(getApplicationContext(), R.string.tst_error_exists, Toast.LENGTH_LONG).show();
                    return;
                }
            }

            int field_type = data.getExtras().getInt("field_type");

            field_names.add(field_name);
            field_types.add(FieldType.values()[field_type - 1]);

            arrayAdapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), R.string.tst_field_added, Toast.LENGTH_LONG).show();
        }
    }
}
