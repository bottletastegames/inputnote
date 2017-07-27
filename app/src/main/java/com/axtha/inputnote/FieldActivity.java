package com.axtha.inputnote;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class FieldActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_field);

        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edt_field_name = (EditText) findViewById(R.id.edt_field_name);
                String field_name = edt_field_name.getText().toString();

                NameCheck.Result result = NameCheck.Do(field_name);
                if (result != NameCheck.Result.OK) {
                    ErrorMessage(result);
                    return;
                }

                RadioButton rdo_integer = (RadioButton) findViewById(R.id.rdo_integer);
                RadioButton rdo_real_num = (RadioButton) findViewById(R.id.rdo_real_num);
                RadioButton rdo_text = (RadioButton) findViewById(R.id.rdo_text);
                RadioButton rdo_datetime = (RadioButton) findViewById(R.id.rdo_datetime);

                FieldType field_type = FieldType.INTEGER;
                if (rdo_integer.isChecked()) {
                    field_type = FieldType.INTEGER;
                } else if (rdo_real_num.isChecked()) {
                    field_type = FieldType.REAL_NUM;
                } else if (rdo_text.isChecked()) {
                    field_type = FieldType.TEXT;
                } else if (rdo_datetime.isChecked()) {
                    field_type = FieldType.DATE_TIME;
                } else {
                    ErrorMessage(NameCheck.Result.REQUIRED);
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("field_name", field_name);
                intent.putExtra("field_type", field_type.getValue());

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
