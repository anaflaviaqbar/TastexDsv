package com.example.anafl.projetofirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.anafl.projetofirebase.DAO.ConfiguracaoFirebase;
import com.example.anafl.projetofirebase.Entidades.Usuarios;
import com.example.anafl.projetofirebase.Helper.Base64Custom;
import com.example.anafl.projetofirebase.Helper.Preferencias;
import com.example.anafl.projetofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadNome;
    private EditText edtCadDataNasc;
    private EditText edtCadSenha;
    private EditText edtConfSenha;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private EditText edtCadCep;
    private Button btnGravar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadEmail = (EditText) findViewById(R.id.edtEmail);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadCep = (EditText) findViewById(R.id.edtCadCep);
        edtCadDataNasc = (EditText) findViewById(R.id.edtCadDataNasc);
        edtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtConfSenha = (EditText) findViewById(R.id.edtConfSenha);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        btnGravar = (Button) findViewById(R.id.btnGravar);

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCadSenha.getText().toString().equals(edtConfSenha.getText().toString())) {
                    usuarios = new Usuarios();
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setCep(edtCadCep.getText().toString());
                    usuarios.setDataNasc(edtCadDataNasc.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());
                    if (rbFeminino.isChecked()) {
                        usuarios.setSexo("Feminino");
                    } else {
                        usuarios.setSexo("Masculino");
                    }

                    cadastrarUsuario();

                } else {
                    Toast.makeText(CadastroActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();

            }
            }
        });
}

    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseautenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Usuario cadastrado com sucesso", Toast.LENGTH_LONG).show();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferenciasAndroid = new Preferencias(CadastroActivity.this);
                    preferenciasAndroid.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    abrirLoginUsuario();

                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-email digitado é inválido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já está cadastrado no sistema";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, "Erro" + erroExcecao, Toast.LENGTH_LONG).show();
                }
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

    }