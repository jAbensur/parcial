package com.example.parcial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView = findViewById(R.id.imageView);
        editTextUsername = findViewById(R.id.editTextText);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        user = getIntent().getParcelableExtra("user");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
        imageView.setImageBitmap(resizedBitmap);
    }

    public void login(View v){
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if(username.isEmpty()){
            toast("Ingrese su nombre de usuario");
            return;
        }

        if(password.isEmpty()){
            toast("Ingrese su contraseña");
            return;
        }

        if(username.length() > 10){
            toast("Su nombre de usuario no debe exceder los 10 caracteres");
            return;
        }

        if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        }else{
            toast("Credenciales incorrectas");
        }
    }

    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}