package com.example.readmasteradmin.app;

import static android.content.Intent.createChooser;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readmasteradmin.Models.CategoryModels;
import com.example.readmasteradmin.app.databinding.ActivityUploadCategoryBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class UploadCategoryActivity extends AppCompatActivity {

    ActivityUploadCategoryBinding binding;
    FirebaseDatabase database;
    FirebaseStorage storage;

    private Uri imageUri,pdfUri;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
        }

        binding.fetchimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });

        binding.fetchPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(createChooser(intent,"Select PDF"),2);

            }
        });

        binding.btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadData();
            }
        });

    }

    private void uploadData() {

        dialog.show();
        final StorageReference reference = storage.getReference().child("pdf").child(new Date().getTime()+"");
        reference.putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        CategoryModels model = new CategoryModels();
                        model.setTitle(binding.editTitle.getText().toString());
                        model.setImage(imageUri.toString());
                        model.setPdf(uri.toString());

                        database.getReference().child("categories").push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        dialog.dismiss();
                                        Toast.makeText(UploadCategoryActivity.this, "category uploaded", Toast.LENGTH_SHORT).show();
                                        onBackPressed();

                                    }
                                });
                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){

            if (data!=null){

                imageUri = data.getData();

                StorageReference reference = storage.getReference().child("image/" + System.currentTimeMillis()+".jpg");
                reference.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                imageUri = uri;

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(UploadCategoryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
            }

        }
        else {

            pdfUri = data.getData();
        }

    }
}