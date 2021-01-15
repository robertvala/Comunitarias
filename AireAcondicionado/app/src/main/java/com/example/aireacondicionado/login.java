

        package com.example.aireacondicionado;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;

        import com.google.android.gms.auth.api.signin.GoogleSignIn;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInClient;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.common.api.ApiException;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthCredential;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.GoogleAuthProvider;

        import java.util.HashMap;

        /**
 * Demonstrate Firebase Authentication using a custom minted token. For more information, see:
 * https://firebase.google.com/docs/auth/android/custom-auth
 */
        public class CustomAuthActivity extends AppCompatActivity{
                //Varibales públicas
                static final int GOOGLE_SIGN_IN = 123;
                FirebaseAuth mAuth;
                GoogleSignInClient mGoogleSignInClient;
                Button btn_login;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_main);
                        mAuth = FirebaseAuth.getInstance();
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

                        Intent intent = getIntent();
                        String msg = intent.getStringExtra("msg");
                        if(msg != null){
                                if(msg.equals("cerrarSesion")){
                                        cerrarSesion();
                                }
                        }
                }
                public void iniciarSesion(View view) {
                        System.out.println("Presionado Iniciar Sesion");
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
                }

                private void cerrarSesion() {
                        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                                task -> updateUI(null));
                }

                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                        System.out.println("corriendo onActivity Result");
                        super.onActivityResult(requestCode, resultCode, data);
                        if (requestCode == GOOGLE_SIGN_IN) {
                                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                                try {
                                        GoogleSignInAccount account = task.getResult(ApiException.class);
                                        if (account != null) firebaseAuthWithGoogle(account);
                                }
                                catch (ApiException e) {
                                        Log.w("TAG", "Fallo el inicio de sesión con google.", e);
                                }
                        }
                }
                private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
                        System.out.println("Corriendo firebase Auth");
                        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());
                        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                        mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(this, task -> {
                                        if (task.isSuccessful()) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                updateUI(user);
                                        } else {
                                                System.out.println("error");
                                                updateUI(null);
                                        }
                                });
                }
                private void updateUI(FirebaseUser user) {
                        if (user != null) {

                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                        } else {
                                System.out.println("sin registrarse");
                        }
                }

        }

