package com.example.countrycabinrentaltabletapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView confirmation;
    private RadioGroup cabinRadioGroup;
    private String selectedCabin;

    Calendar c = Calendar.getInstance();
    DateFormat fmtDate = DateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        confirmation = findViewById(R.id.confirmationText);
        Button scheduleButton = findViewById(R.id.scheduleButton);

        cabinRadioGroup = findViewById(R.id.cabinRadioGroup);

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, d, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Calendar secondNight = (Calendar) c.clone();
            secondNight.add(Calendar.DATE, 1);

            Calendar thirdNight = (Calendar) c.clone();
            thirdNight.add(Calendar.DATE, 2);

            String firstNight = fmtDate.format(c.getTime());
            String secondNightStr = fmtDate.format(secondNight.getTime());
            String thirdNightStr = fmtDate.format(thirdNight.getTime());

            int selectedCabinId = cabinRadioGroup.getCheckedRadioButtonId();
            if (selectedCabinId == R.id.standardRadioButton) {
                selectedCabin = "Standard";
            } else if (selectedCabinId == R.id.upgradedCabinButton) {
                selectedCabin = "Upgraded";
            }

            String dateRange = "Your reservation at the " + selectedCabin + " cabin is from " + firstNight + " to " + thirdNightStr;


            confirmation.setText(dateRange);
        }
    };
}
