package com.mexpense.m_expense;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.MapView;
import com.google.android.material.dialog.MaterialDialogs;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class TakePhotoFragrment extends Fragment {

    //This fragmnet allows user to take photo,
    //it also allow users to save data in the database after the taking the photo.

    ImageView imageView, imageView1, imageview2, imageView3;
    Button button;
    ImageDatabase imageDatabase;
    Bitmap bitmap;

    private  static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_takephoto, container, false);
        imageView = view.findViewById(R.id.imagevieww);
        imageView1 = view.findViewById(R.id.imagedb);
        imageview2 = view.findViewById(R.id.imagedb1);
        imageView3 = view.findViewById(R.id.imagedb2);
        button = view.findViewById(R.id.take);
        imageDatabase = new ImageDatabase(getContext());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            imageView.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PackageManager.PERMISSION_GRANTED);
        }
        else {
            imageView.setEnabled(true);
            imageView1.setEnabled(true);
            imageview2.setEnabled(true);
        }
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageFiles.class);
                startActivity(intent);
            }
        });
        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView3.setVisibility(View.VISIBLE);
                addDatabse(v);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("Set your image")
                        .items(R.array.uploadimages)
                        .itemsIds(R.array.itemsid)
                        .itemsCallback((dialog, view, which, text)->{
                            switch (which){
                                case 0: {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                                    imageview2.setVisibility(View.VISIBLE);
                                    startActivityForResult(intent, CAPTURE_PHOTO);
                                    break;
                                }
                                case 2: {
                                    imageView.setImageResource(R.drawable.ic_baseline_person_24);
                                    imageView1.setImageResource(R.drawable.ic_baseline_person_24);
                                    break;
                                }
                            }

                        }).show();

            }
        });

        return view;
    }
    private  void onCapture(Intent data){
        bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setMaxWidth(300);
        imageView.setImageBitmap(bitmap);
        imageView1.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.VISIBLE);
        imageView3.setImageBitmap(bitmap);
        imageview2.performClick();
    }
    public void addDatabse(View view){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        imageDatabase.addDatabase(data);

        Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();
    }
    public static class ImageProvider extends ContentProvider{

        private  static final int image = 100;
        private static final int image_id = 101;

        private static final String CONTENT_AUTHORITY = "com.mexpense.m_expense";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);
        private static final String PATH_IMAGES = "Image.path";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_IMAGES);
        private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        static {
            uriMatcher.addURI(CONTENT_AUTHORITY, PATH_IMAGES, image);
            uriMatcher.addURI(CONTENT_AUTHORITY, PATH_IMAGES + "/#", image_id);
        }
        ImageDatabase imageDatabase;
        @Override
        public boolean onCreate() {
            imageDatabase = new ImageDatabase(getContext());
            return true;
        }

        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
            SQLiteDatabase sqLiteDatabase = imageDatabase.getReadableDatabase();
            Cursor cursor;
            int match = uriMatcher.match(uri);
            switch (match){
                case image:{
                    cursor = sqLiteDatabase.query(ImageDatabase.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                    break;
                }

                default:
                    throw new IllegalStateException("Unexpected value: " + match);
            }
            return cursor;
        }

        @Nullable
        @Override
        public String getType(@NonNull Uri uri) {
            return null;
        }

        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
            return null;
        }

        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
            return 0;
        }

        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
            return 0;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]== PackageManager.PERMISSION_GRANTED){
                imageView.setEnabled(true);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO){
            if (requestCode == RESULT_OK){
                try {
                    final Uri imageuri = data.getData();
                    final InputStream imagestream = getActivity().getContentResolver().openInputStream(imageuri);
                    final Bitmap selectedimage  = BitmapFactory.decodeStream(imagestream);
                    imageView.setImageBitmap(selectedimage);
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == CAPTURE_PHOTO){
            if (resultCode == RESULT_OK){

                onCapture(data);
            }
        }
    }
}