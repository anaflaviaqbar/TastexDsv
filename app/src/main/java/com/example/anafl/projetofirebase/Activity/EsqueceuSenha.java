package com.example.anafl.projetofirebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.anafl.projetofirebase.R;

/**
 * Created by anafl on 28/03/2018.
 */

public class EsqueceuSenha extends AppCompatActivity{


    FirebaseAuth auth;
    EditText emailEsqueceuSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        auth = FirebaseAuth.getInstance();
        emailEsqueceuSenha = (EditText) findViewById(R.id.email_esqueceu_cenha);
    }

    public void redefinirSenha (View view){

        auth.sendPasswordResetEmail(emailEsqueceuSenha.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EsqueceuSenha.this, "Email enviado", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(EsqueceuSenha.this, "Falha no envio", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
