package app.sunshine.com.example.android.androidinternshipahmedhassan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private static final String TAG = "LoginScreen";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    TextView goRegister;
    EditText useremail;
    EditText userpassword;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        goRegister = findViewById(R.id.goregister);
        useremail = findViewById(R.id.userEmail);
        userpassword = findViewById(R.id.userPassword);
        login = findViewById(R.id.signin_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = useremail.getText().toString();
                String pass = userpassword.getText().toString();
                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(LoginScreen.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginScreen.this, "Successfully Signed in ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginScreen.this, Home.class);
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginScreen.this, "Authentication failed",
                                                Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });
                }
            }
        });

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginScreen.this,Register.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
// Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginScreen.this, Home.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
