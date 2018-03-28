package com.example.anafl.projetofirebase.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by anafl on 23/03/2018.
 */

public class ConfiguracaoFirebase {


    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;


    public static DatabaseReference getFirebase(){
        if(referenciaFirebase==null){
            referenciaFirebase= FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }
    public static FirebaseAuth getFirebaseautenticacao(){
        if(autenticacao==null){
            autenticacao=FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
