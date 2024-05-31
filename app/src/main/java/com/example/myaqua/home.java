package com.example.myaqua;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        //Button pertama
        TextView textd = findViewById(R.id.textd);
        applyGradientToTextView(textd);

        TextView texd = findViewById(R.id.texd);
        applyGradientToTextView(texd);

        //Button kedua
        TextView text1 = findViewById(R.id.text1);
        applyGradientToTextView(text1);

        TextView texd2 = findViewById(R.id.texd2);
        applyGradientToTextView(texd2);

        //Button ketiga
        TextView text3 = findViewById(R.id.text3);
        applyGradientToTextView(text3);

        TextView texd3 = findViewById(R.id.texd3);
        applyGradientToTextView(texd3);

    }

    private void applyGradientToTextView(TextView textView) {
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        0xFF1A529D,// start color 000080
                        0xFF2E9271,
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
    }

    public void hotelz(View view) {
        Intent hotelz = new
                Intent(home.this, selamatvgz.class);
        startActivity(hotelz);
    }

    public void halaman6(View view) {
        Intent halaman6 = new
                Intent(home.this, halamanscan.class);
        startActivity(halaman6);
    }
    public void hotelzs(View view) {
        Intent hotelzs = new
                Intent(home.this, halredeem.class);
        startActivity(hotelzs);
    }
}