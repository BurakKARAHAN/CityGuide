package net.burakkarahan.cityguide.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.burakkarahan.cityguide.MainPage.MainActivity;
import net.burakkarahan.cityguide.Model.ModelLogin;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView TextViewSignup;
    EditText EditTextEmail, EditTextPassword;
    Button ButtonLogin;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getApplicationContext().getSharedPreferences("login",0);
        if(sharedPref.getString("id_user",null) != null && sharedPref.getString("email",null) != null)
        {
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
        }

        Tanimla();
        Butonislevver();

    }

    public void Tanimla()
    {
        EditTextEmail = (EditText) findViewById(R.id.EditTextEmail);
        EditTextPassword = (EditText) findViewById(R.id.EditTextPassword);
        ButtonLogin = (Button) findViewById(R.id.ButtonLogin);
        TextViewSignup = (TextView) findViewById(R.id.TextViewSignup);
    }

    public void Butonislevver()
    {

        TextViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Signup.class);
                startActivity(i);
                finish();
            }
        });

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = EditTextEmail.getText().toString();
                String Password = EditTextPassword.getText().toString();

                if (!Email.equals("") && !Password.equals(""))
                {
                    Login(Email,Password);
                }
                else
                {
                    SweetAlertDialog sal = new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE);
                    sal.setTitleText("Wrong!");
                    sal.setContentText("Please do not leave your e-mail address and password empty");
                    sal.setConfirmText("OK");
                    sal.setConfirmClickListener(null);
                    sal.setCancelable(false);
                    sal.show();
                }

            }
        });
    }

    public void Login(String email, String password)
    {

        final SweetAlertDialog pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

       Call<ModelLogin> request = ManagerAll.getInstance().login(email,password);
       request.enqueue(new Callback<ModelLogin>() {
           @Override
           public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {

               if (response.isSuccessful())
               {
                   if (response.body().getIduser() != null && response.body().getEmail() != null)
                   {
                       String id_user = response.body().getIduser().toString();
                       String email = response.body().getEmail().toString();

                       sharedPref = getApplicationContext().getSharedPreferences("login",0);
                       SharedPreferences.Editor editor = sharedPref.edit();
                       editor.putString("id_user",id_user);
                       editor.putString("email",email);
                       editor.commit();

                       Intent i = new Intent(Login.this,MainActivity.class);
                       startActivity(i);
                       finish();
                       pDialog.cancel();
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(), "response boş döndü", Toast.LENGTH_LONG).show();
                       pDialog.cancel();
                       SweetAlertDialog sa = new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE);
                       sa.setTitleText("Wrong!");
                       sa.setContentText("incorrect entry");
                       sa.setConfirmText("Try Again");
                       sa.setConfirmClickListener(null);
                       sa.setCancelable(false);
                       sa.setCancelText("Signup");
                       sa.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sweetAlertDialog) {
                               Intent i = new Intent(Login.this,Signup.class);
                               startActivity(i);
                           }
                       });
                       sa.show();
                   }
               }


           }

           @Override
           public void onFailure(Call<ModelLogin> call, Throwable t) {
               pDialog.cancel();
               SweetAlertDialog sa = new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE);
               sa.setTitleText("Wrong!");
               sa.setContentText("An unknown error has occurred, please try again later");
               sa.setConfirmText("OK");
               sa.setConfirmClickListener(null);
               sa.setCancelable(false);
               sa.show();
           }
       });
    }

}
