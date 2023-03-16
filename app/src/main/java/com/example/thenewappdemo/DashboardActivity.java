package com.example.thenewappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {
    protected OnBackPressedListener onBackPressedListener;

    FirebaseAuth firebaseAuth;
    ActionBar actionBar;

    //view

    TextView mProfileTv;

    //Back listener

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        //setbat dau
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //init

        firebaseAuth = FirebaseAuth.getInstance();

        //
        BottomNavigationView navigationView = findViewById(R.id.navigation);
//        navigationView.setOnItemSelectedListener(selectedListener);

        actionBar.setTitle("Home");
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "");
        ft1.commit();


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                        case R.id.navi_home:
                            actionBar.setTitle("Home");
                            HomeFragment fragment1 = new HomeFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content, fragment1, "");
                            ft1.commit();
                            break;
//                            return true;

                        case R.id.navi_users:
                            actionBar.setTitle("Users");
                            UsersFragment fragment3 = new UsersFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content, fragment3, "");
                            ft3.commit();
                            break;

                    case R.id.navi_chat:
                        actionBar.setTitle("Chat");
                        ChatListFragment fragment4 = new ChatListFragment();
                        FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                        ft4.replace(R.id.content, fragment4, "");
                        ft4.commit();
                        break;


//                            return true;

                        case R.id.navi_profile:
                            actionBar.setTitle("Profile");
                            ProfileFragment fragment2 = new ProfileFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            break;
                }

                return true;
            }
        }

        );



        //init views

//        mProfileTv = findViewById(R.id.profileTv);

    }



    //loi code

//    private BottomNavigationView.OnItemSelectedListener  selectedListener =
//            new NavigationBarView.OnItemReselectedListener() {
//                @Override
//                public boolean OnItemSelected(@NonNull MenuItem menuItem) {
//                 switch (menuItem.getItemId()){
//                     case R.id.navi_home: {
//                         actionBar.setTitle("Home");
//                         HomeFragment fragment1 = new HomeFragment();
//                         FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//                         ft1.replace(R.id.content, fragment1, "");
//                         ft1.commit();
//
//                         return true;
//                     }
//                     case R.id.navi_users: {
//                         actionBar.setTitle("Users");
//                         UsersFragment fragment3 = new UsersFragment();
//                         FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
//                         ft3.replace(R.id.content, fragment3, "");
//                         ft3.commit();
//
//
//                         return true;
//                     }
//                     case R.id.navi_profile: {
//                         actionBar.setTitle("Profile");
//                         ProfileFragment fragment2 = new ProfileFragment();
//                         FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
//                         ft2.replace(R.id.content, fragment2, "");
//                         ft2.commit();
//
//
//                         return true;
//                     }
//                 }
//                    return false;
//                }
//            };

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){

            //da dang nhap
            //show email
//            mProfileTv.setText(user.getEmail());
        }
        else {
            //chua dang nhap
            startActivity(new Intent(DashboardActivity.this,MainActivity.class));
            finish();
        }

    }

    @Override
    protected void onStart() {
        //check khi app startup
        checkUserStatus();
        super.onStart();
    }








    }




