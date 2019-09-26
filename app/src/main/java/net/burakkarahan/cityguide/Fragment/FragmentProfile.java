package net.burakkarahan.cityguide.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.burakkarahan.cityguide.Account.Login;
import net.burakkarahan.cityguide.Model.ModelActivation;
import net.burakkarahan.cityguide.Model.ModelUserImage;
import net.burakkarahan.cityguide.Model.ModelUserInformation;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ImageView ChangePersonalInformationClose, ChangePasswordClose;
    CircleImageView profile_image;
    TextView ChangePassword, ChangePersonalInformation, TextViewNameSurname, TextViewEmail;
    CardView ChangePasswordCardview, ChangePersonalInformationCardview, ActivationCardview;
    EditText EditTextActivation;
    Button ButtonActivation, btn_sec, btn_yukle;
    Bitmap bitmap;
    private SwipeRefreshLayout yenileme_nesnesi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_profile,container,false);

        SharedPreferences sharedPreferencess = getContext().getSharedPreferences("login",0);
        final int id_user = Integer.valueOf(sharedPreferencess.getString("id_user",""));

        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        ChangePassword = (TextView) view.findViewById(R.id.ChangePassword);
        ChangePersonalInformation = (TextView) view.findViewById(R.id.ChangePersonalInformation);
        ChangePasswordCardview = (CardView) view.findViewById(R.id.ChangePasswordCardview);
        ChangePersonalInformationCardview = (CardView) view.findViewById(R.id.ChangePersonalInformationCardview);
        EditTextActivation = (EditText) view.findViewById(R.id.EditTextActivation);
        ButtonActivation = (Button) view.findViewById(R.id.ButtonActivation);
        ActivationCardview = (CardView) view.findViewById(R.id.ActivationCardview);
        TextViewNameSurname = (TextView) view.findViewById(R.id.TextViewNameSurname);
        TextViewEmail = (TextView) view.findViewById(R.id.TextViewEmail);
        btn_sec = (Button) view.findViewById(R.id.btn_sec);
        btn_yukle = (Button) view.findViewById(R.id.btn_yukle);
        ChangePersonalInformationClose = (ImageView) view.findViewById(R.id.ChangePersonalInformationClose);
        ChangePasswordClose = (ImageView) view.findViewById(R.id.ChangePasswordClose);

        UserInformation(id_user);

        yenileme_nesnesi = (SwipeRefreshLayout) view.findViewById(R.id.yenileme_nesnesi); // nesnemizi tanıttık
        yenileme_nesnesi.setOnRefreshListener(FragmentProfile.this);

        ChangePersonalInformationClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sec.setVisibility(View.GONE);
                btn_yukle.setVisibility(View.GONE);
                ChangePersonalInformation.setVisibility(View.VISIBLE);
                ChangePersonalInformationCardview.setVisibility(View.GONE);
            }
        });

        ChangePasswordClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword.setVisibility(View.VISIBLE);
                ChangePasswordCardview.setVisibility(View.GONE);
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword.setVisibility(View.GONE);
                ChangePasswordCardview.setVisibility(View.VISIBLE);
            }
        });

        ChangePersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sec.setVisibility(View.VISIBLE);
                btn_yukle.setVisibility(View.VISIBLE);
                ChangePersonalInformation.setVisibility(View.GONE);
                ChangePersonalInformationCardview.setVisibility(View.VISIBLE);
            }
        });

        ButtonActivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Actvtn = EditTextActivation.getText().toString();
                Activation(id_user, Actvtn);

            }
        });

        btn_sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGoster();
            }
        });

        btn_yukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gonder();
            }
        });



        return view;
    }

    public void Activation (int id_user, String activation)
    {
        Call<ModelActivation> request = ManagerAll.getInstance().Activation(id_user, activation);
        request.enqueue(new Callback<ModelActivation>() {
            @Override
            public void onResponse(Call<ModelActivation> call, Response<ModelActivation> response) {

                //Toast.makeText(getContext(), "Succesfull");
                ActivationCardview.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ModelActivation> call, Throwable t) {

            }
        });
    }

    public void UserInformation(int id_user)
    {

//        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();

        final Call<ModelUserInformation> request = ManagerAll.getInstance().UserInformation(id_user);
        request.enqueue(new Callback<ModelUserInformation>() {
            @Override
            public void onResponse(Call<ModelUserInformation> call, Response<ModelUserInformation> response) {

                if (response.isSuccessful())
                {

                    Picasso.with(getContext()).load("http://www.cityguide.ml/" + response.body().getImage()).into(profile_image);

                    TextViewNameSurname.setText(response.body().getName() + " " + response.body().getSurname());
                    TextViewEmail.setText(response.body().getEmail());

                    int status = Integer.valueOf(response.body().getStatus());

                    if (status == 1)
                    {
                        ActivationCardview.setVisibility(View.GONE);
                    }
                    else
                    {
                        ActivationCardview.setVisibility(View.VISIBLE);
                    }
                }

//                pDialog.cancel();

            }

            @Override
            public void onFailure(Call<ModelUserInformation> call, Throwable t) {

            }
        });
    }

    public void resimGoster()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,777);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 777 && resultCode == RESULT_OK && data != null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
                profile_image.setImageBitmap(bitmap);
                profile_image.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void gonder()
    {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        SharedPreferences sharedPreferencess = getContext().getSharedPreferences("login",0);
        final int id_user = Integer.valueOf(sharedPreferencess.getString("id_user",""));


        if (bitmap != null)
        {
            String imageToString =  imageToString();


            final Call<ModelUserImage> request = ManagerAll.getInstance().UserImage(id_user,imageToString);
            request.enqueue(new Callback<ModelUserImage>() {
                @Override
                public void onResponse(Call<ModelUserImage> call, Response<ModelUserImage> response) {

                    btn_sec.setVisibility(View.GONE);
                    btn_yukle.setVisibility(View.GONE);
                    ChangePassword.setVisibility(View.VISIBLE);
                    ChangePersonalInformation.setVisibility(View.VISIBLE);
                    ChangePasswordCardview.setVisibility(View.GONE);
                    ChangePersonalInformationCardview.setVisibility(View.GONE);

                    pDialog.cancel();

                }

                @Override
                public void onFailure(Call<ModelUserImage> call, Throwable t) {

                }
            });
        }
        else
        {
            pDialog.cancel();
            SweetAlertDialog sa = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            sa.setTitleText("Wrong!");
            sa.setContentText("Please Select An Image");
            sa.setConfirmText("OK");
            sa.setConfirmClickListener(null);
            sa.setCancelable(false);
            sa.show();
        }

    }

    public String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byte[] byt = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byt,Base64.DEFAULT);
    }

    @Override
    public void onRefresh() {
        SharedPreferences sharedPreferencess = getContext().getSharedPreferences("login",0);
        final int id_user = Integer.valueOf(sharedPreferencess.getString("id_user",""));
        UserInformation(id_user);
        yenileme_nesnesi.setRefreshing(false);
    }

}
