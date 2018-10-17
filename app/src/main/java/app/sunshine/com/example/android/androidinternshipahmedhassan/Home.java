package app.sunshine.com.example.android.androidinternshipahmedhassan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements AsyncResponse {

    private FirebaseAuth mAuth;
    Button sign_out;
   public ListView listView;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        listView=findViewById(R.id.lv);
        sign_out = findViewById(R.id.sign_Out);


        MyAsyncTask task = new MyAsyncTask(this);
        task.execute("http://countryapi.gear.host/v1/Country/getCountries");




        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Home.this, LoginScreen.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }



    @Override
    public void getCountries(ArrayList<String> countryobj) {


        myadapter=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, countryobj);
        listView.setAdapter(myadapter);
    }
}
