package com.mexpense.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageDisplay extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {



    RecyclerView recyclerView;
    public static final int IMAGES_INT = 0;
    ImageDisplay.ImagesAdapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imagesAdapter = new ImageDisplay.ImagesAdapter(this);
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
    public class ImagesAdapter extends RecyclerView.Adapter<ImageDisplay.ImagesAdapter.ImagesView> {

        private Cursor cursor;
        private Context context;

        public ImagesAdapter(Context context) {
            this.context = context;
        }


        @NonNull
        @Override
        public ImageDisplay.ImagesAdapter.ImagesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.imagedisplayer, parent, false);
            return new ImageDisplay.ImagesAdapter.ImagesView(view);

        }

        @Override
        // image display by camera
        public void onBindViewHolder(@NonNull ImageDisplay.ImagesAdapter.ImagesView holder, int position) {
            int images = cursor.getColumnIndex(ImageDatabase.COLUMN_Image);
            cursor.moveToPosition(position);
            byte[] image1 = cursor.getBlob(images);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image1, 0, image1.length);
            holder.image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 2000, 2000, false));
        }

        @Override
        public int getItemCount() {
            if (cursor == null) {
                return 0;
            }
            return cursor.getCount();
        }

        public Cursor swapCursor(Cursor c) {
            if (cursor == c) {
                return null;
            }
            Cursor temp = cursor;
            this.cursor = c;
            if (c != null) {
                this.notifyDataSetChanged();
            }
            return temp;
        }

        public class ImagesView extends RecyclerView.ViewHolder {

            ImageView image;
            CardView cardView;

            public ImagesView(View view) {
                super(view);
                image = view.findViewById(R.id.takephoto1);
                cardView = view.findViewById(R.id.card);

            }
        }
    }
}