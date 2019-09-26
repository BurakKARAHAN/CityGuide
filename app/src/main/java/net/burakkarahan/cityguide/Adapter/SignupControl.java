package net.burakkarahan.cityguide.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.burakkarahan.cityguide.Account.Login;
import net.burakkarahan.cityguide.Account.Signup;
import net.burakkarahan.cityguide.MainPage.MainActivity;
import net.burakkarahan.cityguide.Model.ModelLogin;
import net.burakkarahan.cityguide.Model.ModelPHPMailer;
import net.burakkarahan.cityguide.Model.ModelSignup;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class SignupControl extends AppCompatDialogFragment{


    public Context context;
    public SignupControl(Context context)
    {
        this.context=context;

    }

    String Mail;
    int sayac = 0;
    RingProgressBar ringProgressBar;
    private EditText ActivationCode;
    TextView First, Second;

    int progress = 0;
    Handler myHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
            {
                if (progress<1000)
                    progress++;
                ringProgressBar.setProgress(progress);
            }
        }
    };



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.signupcontrol,null);

        ActivationCode = view.findViewById(R.id.EditTextActivationCode);
        ringProgressBar = view.findViewById(R.id.ProgressBar);
        First = view.findViewById(R.id.first);
        Second = view.findViewById(R.id.second);
        final Signup activity=(Signup) context;

        Random r = new Random();
        final int a = r.nextInt(99);
        final int b = r.nextInt(9);

        final int Plus = a + b;

        First.setText(String.valueOf(a));
        Second.setText(String.valueOf(b));

        final SweetAlertDialog sd = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sd.setCancelable(false);
        sd.setCustomView(view);
        sd.setTitleText("Write the result of the operation below to continue");
        sd.setContentText("Write the result of the operation below to continue");
        sd.setConfirmText("Ok");
        sd.setCancelText("Cancel");
        sd.setCancelable(false);
        sd.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sayac = 1;
                sd.cancel();
            }
        });
        sd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {

                sayac = 1;
                String activationCode = ActivationCode.getText().toString();

                if (!activationCode.equals(""))
                {
                    int Operation = Integer.valueOf(activationCode);

                    if (Operation == Plus)
                    {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences",0);
                        String Email = sharedPreferences.getString("Email","");
                        String Password = sharedPreferences.getString("Password","");
                        Mail = Email;
                        Signup(activity,Email,Password);

                        sd.cancel();


                    }
                    else
                    {
                        sd.cancel();
                        SweetAlertDialog sd2 = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                        sd2.setCancelable(false);
                        sd2.setTitleText("Wrong!");
                        sd2.setContentText("Please try again");
                        sd2.setConfirmText("OK");
                        sd2.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sd2.setConfirmClickListener(null);
                        sd2.show();
                    }
                }
                else
                {
                    sDialog.setTitleText("Wrong!");
                    sDialog.setContentText("Please try again");
                    sDialog.setConfirmText("OK");
                    sDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    sDialog.setConfirmClickListener(null);
                }
            }
        });
        sd.show();

        ringProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {
            @Override
            public void progressToComplete() {

                if (sayac == 1)
                {

                }
                else
                {
                    sd.cancel();
                    SweetAlertDialog sd3 = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                    sd3.setTitleText("Wrong!");
                    sd3.setContentText("Please try again");
                    sd3.setConfirmText("OK");
                    sd3.setCancelable(false);
                    sd3.setConfirmClickListener(null);
                    sd3.show();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<100; i++)
                {
                    try {
                        Thread.sleep(100);
                        myHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return builder.create();
    }

    public void Signup(final Activity activity, final String email, final String password)
    {

        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<ModelSignup> request = ManagerAll.getInstance().register(email,password);
        request.enqueue(new Callback<ModelSignup>() {
            @Override
            public void onResponse(Call<ModelSignup> call, Response<ModelSignup> response) {

                //Toast.makeText(context,response.body().toString(),Toast.LENGTH_LONG).show();

                if (response.body().getResult().toString().equals("Boyle bir kayit var"))
                {
                    SweetAlertDialog sd4 = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    sd4.setTitleText("Attention!");
                    sd4.setContentText("This email is already registered");
                    sd4.setConfirmText("OK");
                    sd4.setCancelable(false);
                    sd4.setConfirmClickListener(null);
                    sd4.show();
                }
                else
                {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences",0);
                    String Email = sharedPreferences.getString("Email","");

                    String ActivationCodee = response.body().getActivationCode().toString();
                    PHPMailer(activity,Email,ActivationCodee);
                    Login(activity,email,password);

                }

            }

            @Override
            public void onFailure(Call<ModelSignup> call, Throwable t) {
                pDialog.cancel();
                SweetAlertDialog sa = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                sa.setTitleText("Wrong!");
                sa.setContentText("An unknown error has occurred, please try again later");
                sa.setConfirmText("OK");
                sa.setConfirmClickListener(null);
                sa.setCancelable(false);
                sa.show();
            }
        });

    }

    public void PHPMailer(final Activity activity, String email, String activationcode)
    {
        Call<ModelPHPMailer> request = new ManagerAll().PHPMailer(email,activationcode);
        request.enqueue(new Callback<ModelPHPMailer>() {
            @Override
            public void onResponse(Call<ModelPHPMailer> call, Response<ModelPHPMailer> response) {

                if (response.isSuccessful())
                {

                    final SweetAlertDialog sd5 = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    sd5.setTitleText("Başarılı!");
                    sd5.setContentText("Aktivasyon kodu e-posta adresinize gönderilmiştir, Lütfen profil bölümünde ilgili yere aktivasyon kodunu yazınız");
                    sd5.setConfirmText("Tamam");
                    sd5.setCancelable(false);
                    sd5.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent i = new Intent(activity,MainActivity.class);
                            activity.startActivity(i);
                        }
                    });
                    sd5.show();

                }

            }

            @Override
            public void onFailure(Call<ModelPHPMailer> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Login(final Activity activity, String email, String password)
    {
        Call<ModelLogin> request = ManagerAll.getInstance().login(email,password);
        request.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {

                String id_user = response.body().getIduser().toString();
                String email = response.body().getEmail().toString();

                SharedPreferences sharedPref = context.getSharedPreferences("login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("id_user",id_user);
                editor.putString("email",email);
                editor.commit();
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {

            }
        });
    }

    public void openDialog()
    {
        SignupControl signupControl = new SignupControl(context);
        signupControl.show(getFragmentManager(), "Verification");
    }

}
