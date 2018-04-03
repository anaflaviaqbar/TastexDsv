package com.example.anafl.projetofirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.anafl.projetofirebase.Activity.Usuarios;
import com.example.anafl.projetofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseAuthUserCollisionException;
//import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadNome;
    private EditText edtCadContato;
    private EditText edtCadDataNasc;
    private EditText edtCadSenha;
    private EditText edtConfSenha;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private EditText edtCadCep;
    private Button btnGravar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;
    private DatabaseReference database;

    private String uid;

    private boolean isFeminino = false;
    private boolean isMasculino = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        autenticacao = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        edtCadEmail = (EditText) findViewById(R.id.edtEmail);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadCep = (EditText) findViewById(R.id.edtCadCep);
        edtCadDataNasc = (EditText) findViewById(R.id.edtCadDataNasc);
        edtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtConfSenha = (EditText) findViewById(R.id.edtConfSenha);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        edtCadContato = (EditText) findViewById(R.id.edtCadContato);


        btnGravar = (Button) findViewById(R.id.btnGravar);
        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCadSenha.getText().toString().equals(edtConfSenha.getText().toString())){
                    cadastrarUsuario(edtCadEmail.getText().toString(), edtCadSenha.getText().toString());
                }else{
                    Toast.makeText(CadastroActivity.this, "Senhas diferentes!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
                    // usuarios = new Usuarios();
                    //usuarios.setNome(edtCadNome.getText().toString());
                    //usuarios.setEmail(edtCadEmail.getText().toString());
                    //usuarios.setCep(edtCadCep.getText().toString());
                    //usuarios.setDataNasc(edtCadDataNasc.getText().toString());
                    //usuarios.setSenha(edtCadSenha.getText().toString());
                    //if (rbFeminino.isChecked()) {
                    //  usuarios.setSexo("Feminino");
                    //} else {
                    //  usuarios.setSexo("Masculino");
                    //}
             //   } else {
               //     Toast.makeText(CadastroActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                //}
        //});
//}

    private void cadastrarUsuario(String email, String password) {
        autenticacao.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = autenticacao.getCurrentUser();
                            //updateUI(user);
                            novoUsuario(user.getUid());
                            Toast.makeText(CadastroActivity.this, "Cadastro feito sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });
    }

    public void abrirLoginUsuario() {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void AbreRedefine() {
        Intent intentAbreRedefine = new Intent(CadastroActivity.this, EsqueceuSenha.class);
        startActivity(intentAbreRedefine);
        finish();
    }

    public void checaSexo(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.rbFeminino:
                if (checked) {
                    isFeminino = true;
                    isMasculino = false;
                }
                break;
            case R.id.rbMasculino:
                if (checked) {
                    isFeminino = false;
                    isMasculino = true;
                }
        }
    }
    private void novoUsuario(String userId) {
        Usuarios usuarios = new Usuarios();

        usuarios.setNome(edtCadNome.getText().toString());
        usuarios.setEmail(edtCadEmail.getText().toString());
        usuarios.setCep(edtCadCep.getText().toString());
        usuarios.setTelefone(edtCadContato.getText().toString());
        usuarios.setDataNasc(edtCadDataNasc.getText().toString());
        usuarios.setSenha(edtCadSenha.getText().toString());
        if(isFeminino){
            usuarios.setSexo("Feminino");
        }else if(isMasculino){
            usuarios.setSexo("Masculino");
        }
        usuarios.setId(userId);

        database.child("users").child(userId).setValue(usuarios);
    }
}
