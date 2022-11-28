package com.example.gabriel_sanchez_s5.ui.add;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.gabriel_sanchez_s5.R;
import com.example.gabriel_sanchez_s5.databinding.FragmentAddBinding;
import com.example.gabriel_sanchez_s5.db.DBHandler;
import com.example.gabriel_sanchez_s5.utilities.DbBitmapUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.Objects;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    private DBHandler dbHandler;
    private Bitmap personalBitMap;
    private ContentResolver resolver;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHandler = new DBHandler(root.getContext());
        resolver = root.getContext().getContentResolver();

        final Button btnPicture = binding.btnPicture;
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        final Button btnSubmit = binding.btnSubmit;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.txtName.getText().toString();
                String chargeType = binding.cbChargeType.getSelectedItem().toString();
                ImageView imageViewer = binding.imageViewer;

                if(name.isEmpty() || chargeType.isEmpty() || chargeType.toLowerCase() == "seleccione") {
                    Toast.makeText(root.getContext(), "Debe completar con los datos obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    Bitmap bm =((BitmapDrawable)imageViewer.getDrawable()).getBitmap();
                    storeImageInMediaStore(bm);
                    dbHandler.addNewPersonal(name, chargeType, DbBitmapUtility.getBytes(personalBitMap));

                    Toast.makeText(root.getContext(), "Registro creado correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private boolean storeImageInMediaStore(Bitmap image) {
        FileOutputStream fos;
        boolean response = false;
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "image_" + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "AppGSAS5Folder");
                Uri imageURI = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                fos = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(imageURI));
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);
                response = true;
            }

        } catch (Exception e) {
            response = false;
        }

        return response;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap= (Bitmap) data.getExtras().get("data");
        final ImageView imageViewer = binding.imageViewer;
        personalBitMap = bitmap;
        imageViewer.setImageBitmap(bitmap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}