package com.example.myaqua;


import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class scanactivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanactivity);
        initViews(); // Panggil metode initViews() di dalam onCreate()
        initialiseDetectorsAndSources(); // Panggil metode initialiseDetectorsAndSources() di dalam onCreate()
    }

    // Metode untuk inisialisasi tampilan (views)
    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
    }

    // Override metode onRequestPermissionsResult untuk menangani hasil permintaan izin kamera
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin kamera diberikan, panggil metode untuk menginisialisasi detektor dan sumber kamera
                initialiseDetectorsAndSources();
            } else {
                // Izin kamera ditolak oleh pengguna, lakukan penanganan sesuai kebutuhan
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void initialiseDetectorsAndSources () {
        Toast.makeText(getApplicationContext(), "Camera is on", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(scanactivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(scanactivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Metode release
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    final String scannedBarcode = barcodes.valueAt(0).displayValue;

                    // Check if barcode has been scanned before
                    SharedPreferences sharedPreferences = getSharedPreferences("ScannedBarcodes", MODE_PRIVATE);
                    int scanCount = sharedPreferences.getInt(scannedBarcode, 0);

                    if (scanCount < 1) {
                        // Barcode has not been scanned before or scanned less than once
                        // Increment scan count and save to SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(scannedBarcode, scanCount + 1);
                        editor.apply();

                        // Get the current scanned count
                        int currentCount = scanCount + 1;

                        // Update TextView to display scannedBarcode and currentCount
                        final String displayText = scannedBarcode + " (Scanned: " + currentCount + " times)";
                        txtBarcodeValue.post(new Runnable() {
                            @Override
                            public void run() {
                                txtBarcodeValue.setText(displayText);
                            }
                        });

                        // Pass intentData back to MainActivity
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("intentData", scannedBarcode);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish(); // Close the current activity and return to MainActivity
                    } else {
                        // Barcode has been scanned more than once
                        // Show error message or notification
                        Toast.makeText(getApplicationContext(), "Barcode has already been scanned", Toast.LENGTH_SHORT).show();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Update TextView to display scannedBarcode and currentCount
                                final String displayText = scannedBarcode + " (Scanned: " + (scanCount + 1) + " times)";
                                txtBarcodeValue.setText(displayText);
                            }
                        });

                        // Pass intentData back to MainActivity
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("intentData", scannedBarcode);
                        setResult(Activity.RESULT_OK, resultIntent);

                        // Call finish() after all processes are done
                        finish();

                    }

                }
            }
        });
    }
}
