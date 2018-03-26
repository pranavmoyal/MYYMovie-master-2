package com.example.niraj.mapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import static com.example.niraj.mapplication.Utils.Login_Fragment_frag;

public class Main2Activity extends FragmentActivity {
    Login_Fragment myf;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    FragmentManager fragmentManager;
    static TextView tv_edittitle;
    SignUp_Fragment signUp_fragment;
    ForgotPassword_Fragment forgotPassword_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv_edittitle=(TextView) findViewById(R.id.tv_edittitle);

        myf = new Login_Fragment();

        ///setFragment(myf);

        Login_Fragment frg=new Login_Fragment();
        SignUp_Fragment frg1=new SignUp_Fragment();
        ForgotPassword_Fragment frg2=new ForgotPassword_Fragment();

        FragmentManager manager=getSupportFragmentManager();

        FragmentTransaction transaction=manager.beginTransaction();

        transaction.add(R.id.frameContainer, frg, "Frag_Top_tag");
        transaction.addToBackStack(null);
        //transaction.add(R.id.signup_fragment, frg1, "Frag_Middle_tag");
        //transaction.add(R.id.password_fragment, frg2, "Frag_Bottom_tag");


        transaction.commit();

    }
    /*protected void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frameContainer, fragment);
        t.addToBackStack("A_B_TAG");
        t.commit();
    }*/
    /*@Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }*/

   /* @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("FragmentC") != null) {
            // I'm viewing Fragment C
            getSupportFragmentManager().popBackStack("A_B_TAG",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }*/


    public void replaceLoginFragment(){
        FragmentManager mFragmentManager = ((FragmentActivity) getApplicationContext()).getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.addToBackStack(Login_Fragment_frag);
        ft.replace(R.id.frameContainer, signUp_fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = new Fragment();
        if (fragment instanceof SignUp_Fragment) {
            ((SignUp_Fragment) fragment).onBackPressed();
        } else if (fragment instanceof ForgotPassword_Fragment) {
            ((ForgotPassword_Fragment) fragment).onBackPressed();
        } else {
            this.finish();
            super.onBackPressed();

        }
    }

    /*public boolean popFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            return true;
        }
        return false;
    }*/
}


