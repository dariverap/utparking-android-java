package com.example.finalproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproyect.login.LoginRequest;
import com.example.finalproyect.login.LoginResponse;
import com.example.finalproyect.usuario.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText correo, contraseña;
    Button btnlogin;

    Retrofit retrofit = new RetrofitClient().getRetrofit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iniciarsesion();
            }
        });
    }

    public void Iniciarsesion(){

        correo = findViewById(R.id.txtusuario);
        contraseña = findViewById(R.id.txtcontraseña);
        WebService webService = retrofit.create(WebService.class);
        String correo1 = correo.getText().toString();
        String contraseña1 = contraseña.getText().toString();

        // Crear objeto de tipo LoginRequest con los datos de correo y contraseña del usuario
        LoginRequest request = new LoginRequest(correo1, contraseña1);

        // Hacer la petición POST al servidor
        Call<LoginResponse> call = webService.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // cuando La petición se hae correctamente y se devolvió una respuesta válida
                    LoginResponse loginResponse = response.body();//obtiene la respuesta que envía el servidor
                    Usuario usuario = loginResponse.getUsuario(); //obtiene el objeto usuario
                    String mensaje = loginResponse.getMensaje(); //obteine un mensaje que el servidor haya mandado junto con la respuesta
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle args = new Bundle();
                    args.putString("NombreUsuario", usuario.getNombre());

                    startActivity(intent);
                    finish();
                    FancyToast.makeText(getApplicationContext(),"Bienvenido!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();


                } else {
                    //si falla al iniciar sesión
                    Toast.makeText(LoginActivity.this, "INGRESE LOS DATOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "FALLO EN LA PETICIÓN", Toast.LENGTH_SHORT).show();
            }
        });

    }
}