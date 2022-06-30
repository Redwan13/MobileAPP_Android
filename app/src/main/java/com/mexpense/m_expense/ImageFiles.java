package com.mexpense.m_expense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageFiles extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    
    RecyclerView recyclerView;
    public static final int IMAGES_INT = 0;
    ImagesAdapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_files);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imagesAdapter = new ImagesAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imagesAdapter);

        getLoaderManager().initLoader(IMAGES_INT, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {ImageDatabase.COLUMN_Image};
        return new android.content.CursorLoader(this, TakePhotoFragrment.ImageProvider.CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        imagesAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        imagesAdapter.swapCursor(null);

    }
    public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesView>{

        private Cursor cursor;
        private Context context;

        public ImagesAdapter(Context context) {
            this.context = context;
        }


        @NonNull
        @Override
        public ImagesAdapter.ImagesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view =inflater.inflate(R.layout.list_images, parent, false);
            return new ImagesView(view);

        }

        @Override
        public void onBindViewHolder(@NonNull ImagesAdapter.ImagesView holder, int position) {
            int images1 = cursor.getColumnIndex(ImageDatabase.COLUMN_Image);
            cursor.moveToPosition(position);
            byte[] image1 = cursor.getBlob(images1);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image1, 0, image1.length);
            holder.image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 800, 800, false));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ImageFiles.this, ImageDisplay.class);
                    startActivity(intent);
                }
            });
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ImageDatabase imageDatabase = new ImageDatabase(ImageFiles.this);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ImageFiles.this);
                    builder.setTitle("Confirm the entities");
                    builder.setMessage("Are you sure you want to delete the image ?");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imageDatabase.deletedatabase(images1);
                            Intent intent = new Intent(ImageFiles.this, ImageFiles.class);
                            startActivity(intent);
                            notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            if (cursor == null){
                return 0;
            }
            return cursor.getCount();
        }
        public Cursor swapCursor(Cursor c){
            if (cursor == c){
                return null;
            }
            Cursor temp = cursor;
            this.cursor = c;
            if (c != null){
                this.notifyDataSetChanged();
            }
            return temp;
        }

        public  class ImagesView extends RecyclerView.ViewHolder{

            ImageView image;
            CardView cardView;

            public ImagesView(View view) {
                super(view);
               image = view.findViewById(R.id.takephoto1);
               cardView=view.findViewById(R.id.card);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to delete all ?");
            builder.setMessage("Are you sure you want to delete all ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ImageDatabase db = new ImageDatabase(ImageFiles.this);
                    db.deleteAll();
                    Intent intent = new Intent(ImageFiles.this, ImageFiles.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();


        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}