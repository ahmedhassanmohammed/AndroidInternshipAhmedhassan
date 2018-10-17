package app.sunshine.com.example.android.androidinternshipahmedhassan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    TextView goLogin;
    AutoCompleteTextView fullname ;
    AutoCompleteTextView email;
    AutoCompleteTextView password;
    AutoCompleteTextView passwordConfirmation;
    Button register ;

    private static final String TAG = "Register";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname =findViewById(R.id.fullName);
        email =findViewById(R.id.email);
        password=findViewById(R.id.password);
        passwordConfirmation=findViewById(R.id.confirmPassword);
        register=findViewById(R.id.signup_btn);


        mAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myemail = email.getText().toString();
                String pass = password.getText().toString();
                String myname = fullname.getText().toString();
                String confirmation =passwordConfirmation.getText().toString();
                if (myemail.equals("") || pass.equals("") || myname.equals("") || confirmation.equals(""))
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else if (!pass.equals(confirmation))
                    Toast.makeText(Register.this, "password and confirmation not matched", Toast.LENGTH_SHORT).show();
                else
                {
                    mAuth.createUserWithEmailAndPassword(myemail, pass)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(Register.this, Home.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });


        goLogin=findViewById(R.id.login_link);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,LoginScreen.class));
            }
        });
    }


}
