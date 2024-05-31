package com.example.myaqua;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class halamanscan extends AppCompatActivity {
    private ImageButton camera;

    // ActivityResultLauncher for starting ScannerActivity
    private ActivityResultLauncher<Intent> scanBarcodeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        int scanCount = data.getIntExtra("scanCount", 0);
                        if (scanCount < 2) {
                            // Barcode scanned less than 2 times, direct to the correct page
                            Intent nextIntent = new Intent(halamanscan.this, halscan2.class);
                            startActivity(nextIntent);
                        } else {
                            // Barcode scanned 2 times or more, direct to the wrong page
                            Intent wrongIntent = new Intent(halamanscan.this, halscan1.class);
                            startActivity(wrongIntent);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halamanscan);

        camera = findViewById(R.id.camera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start ScannerActivity and wait for the result
                Intent intent = new Intent(halamanscan.this, scanactivity.class);
                scanBarcodeLauncher.launch(intent);
            }
        });
    }
    public void submit(View view) {
        Intent submit = new
                Intent(halamanscan.this, halscan1.class);
        startActivity(submit);
    }

}