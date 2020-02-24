package com.example.handleliste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class addActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button btnSend = findViewById(R.id.btnSend);
        final EditText inName, inStatus;
        inName = findViewById(R.id.inName);
        final RadioGroup radioGroup = findViewById(R.id.radioGrp);

        final FirebaseDatabase reff = FirebaseDatabase.getInstance();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton buttonPressed = findViewById(radioGroup.getCheckedRadioButtonId());
                int status = getStatus(buttonPressed);
                Vare nyVare = new Vare(inName.getText().toString(), status);
                reff.getReference().child("varer").child(inName.getText().toString()).setValue(nyVare);
                Toast.makeText(getApplicationContext(), "Vare lagret!", Toast.LENGTH_SHORT).show();
                inName.setText("");
            }
        });
    }

    private int getStatus(RadioButton buttonPressed) {
        switch (buttonPressed.getText().toString()){
            case "Har": return 1;
            case "Har lite": return 2;
            case "Tom": return 3;
            default:return 1;
        }


    }
}
