package com.example.myaqua;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class hal16 extends AppCompatActivity {

    private static final String[] sectors = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final int[] sectorsDegrees = new int[sectors.length];
    private static final Random random = new Random();
    private int degree = 0;
    private boolean isSpinning = false;
    private ImageView Spinners;
    private ImageView Panahkuning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hal16);

        Spinners = findViewById(R.id.Spinners);
        Panahkuning = findViewById(R.id.Panahkuning); // Assuming there is a button with id spin_button to trigger spinning

        getDegreesForSectors();

        Spinners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSpinning) {
                    Spinners();
                    isSpinning = true;
                }
            }
        });
    }

    private void Spinners() {
        degree = random.nextInt(sectors.length);

        RotateAnimation rotateAnimation = new RotateAnimation(0, (360 * sectors.length) + sectorsDegrees[degree],
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(hal16.this, "Selamat Untuk Penukaran Pada Bulan Ini Senilai " + sectors[sectors.length - (degree + 1)] + " point.", Toast.LENGTH_SHORT).show();
                isSpinning = false;

                // Menunda sebelum pindah ke halaman selanjutnya
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Pindah ke halaman selanjutnya setelah jeda
                        Redeems();
                    }
                }, 2000); // Jeda selama 2 detik (2000 milidetik)
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        Spinners.startAnimation(rotateAnimation);
    }

    private void getDegreesForSectors() {
        int sectorDegree = 360 / sectors.length;
        for (int i = 0; i < sectors.length; i++) {
            sectorsDegrees[i] = (i + 1) * sectorDegree;
        }
    }

    private void Redeems() {
        Intent intent = new Intent(hal16.this, scanktp.class);
        startActivity(intent);
    }


}
