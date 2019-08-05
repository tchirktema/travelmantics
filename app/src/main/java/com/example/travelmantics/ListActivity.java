package com.example.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.travelmantics.Model.TravelDeal;
import com.example.travelmantics.Util.FirebaseUtil;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<TravelDeal> deals;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu,menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.insert_menu:
                Intent intent = new Intent(this, DealActivity.class);
                startActivity(intent);
                return  true;
            case R.id.logout_menu:
                logout();
                return  true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    protected void logout(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Logout","User logged Out");
                        FirebaseUtil.attachListener();
                    }
                });
        FirebaseUtil.detachListener();

    }

    @Override
    protected void onPause(){
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        FirebaseUtil.openFirebaseReference("traveldeals",this);
        RecyclerView rvDeals = (RecyclerView) findViewById(R.id.rvDeals);
        final  DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);

        LinearLayoutManager dealsLayoutManager =
                new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        rvDeals.setLayoutManager(dealsLayoutManager);
        FirebaseUtil.attachListener();
    }
}
