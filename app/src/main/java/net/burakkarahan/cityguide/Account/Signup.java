package net.burakkarahan.cityguide.Account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import net.burakkarahan.cityguide.Adapter.SignupControl;
import net.burakkarahan.cityguide.MainPage.MainActivity;
import net.burakkarahan.cityguide.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Signup extends AppCompatActivity {

    SweetAlertDialog sweetAlert;
    EditText EditTextEmail, EditTextPassword, EditTextConfirmPassword;
    Button ButtonSignup;
    ImageButton ImageButtonLogin;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedPref = getApplicationContext().getSharedPreferences("login",0);
        if(sharedPref.getString("id_user",null) != null && sharedPref.getString("email",null) != null)
        {
            Intent i = new Intent(Signup.this,MainActivity.class);
            startActivity(i);
        }

        Tanimla();
        Butonislevver();

        /*
        if(isOnline())
        {

        }
        else
        {
            connectionMessage();
        }
        */
    }

    public void Tanimla()
    {
        EditTextEmail = (EditText) findViewById(R.id.EditTextEmail);
        EditTextPassword = (EditText) findViewById(R.id.EditTextPassword);
        EditTextConfirmPassword = (EditText) findViewById(R.id.EditTextConfirmPassword);
        ButtonSignup = (Button) findViewById(R.id.ButtonSignup);
        ImageButtonLogin = (ImageButton) findViewById(R.id.ImageButtonLogin);
    }

    public void Butonislevver()
    {

        ImageButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        ButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = EditTextEmail.getText().toString();
                String Password = EditTextPassword.getText().toString();
                String ConfirmPassword = EditTextConfirmPassword.getText().toString();


                if(!Email.equals("") && !Password.equals("") && !ConfirmPassword.equals(""))
                {

                    if (Password.equals(ConfirmPassword))
                    {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedPreferences",0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Email",Email);
                        editor.putString("Password",Password);
                        editor.commit();

                        openDialog();
                    }
                    else
                    {
                        sweetAlert = new SweetAlertDialog(Signup.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlert.setTitleText("Attention!");
                        sweetAlert.setContentText("Confirm password is incorrect");
                        sweetAlert.setConfirmText("OK");
                        sweetAlert.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sweetAlert.setConfirmClickListener(null);
                        sweetAlert.show();
                    }
                }
                else
                {
                    sweetAlert = new SweetAlertDialog(Signup.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlert.setTitleText("Attention!");
                    sweetAlert.setContentText("Please fill in the required fields");
                    sweetAlert.setConfirmText("OK");
                    sweetAlert.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    sweetAlert.setConfirmClickListener(null);
                    sweetAlert.show();
                }


            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

//        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
//            return true;
//        }
        return false;
    }

    public void connectionMessage() {


        sweetAlert = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sweetAlert.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlert.setTitleText("Attention!");
        sweetAlert.setContentText("Please check your internet connection");
        sweetAlert.setConfirmText("Ok");
        sweetAlert.setCancelable(false);
        sweetAlert.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                System.exit(0);
            }
        });
        sweetAlert.show();

    }

    public void openDialog()
    {
        SignupControl signupControl = new SignupControl(Signup.this);
        signupControl.show(getSupportFragmentManager(), "Verification");
    }

}
