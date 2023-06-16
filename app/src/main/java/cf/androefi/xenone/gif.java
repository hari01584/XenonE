package cf.androefi.xenone;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.dhaval2404.imagepicker.ImagePicker;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


/**
 * A simple {@link Fragment} subclass.
 */
public class gif extends Fragment {

    private static final String TAG = "r/gif";
    private String selectedImg;
    private ActivityResultLauncher<Intent> startForProfileImageResult;
    private TextView seleText;
    public gif() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gif, container, false);
        seleText = v.findViewById(R.id.selectimginfo);

        startForProfileImageResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            selectedImg = intent.getData().toString();
                            seleText.setText(selectedImg);
                            break;
                        case Activity.RESULT_CANCELED:
                            break;
                    }
                }
            });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Button select= getView().findViewById(R.id.gifl);
        Button bc = getView().findViewById(R.id.gifclan);

        Button gif = getView().findViewById(R.id.gif);
        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First To Use This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(selectedImg==null || selectedImg.isEmpty())
                {
                    Toast.makeText(getContext(), "Please select a gif/image file using *Select Image From Gallery* button!",
                        Toast.LENGTH_LONG).show();
                    return;
                }

                Util.startUploadPersonal(getContext(), selectedImg, new onImageUploaded() {
                    @Override
                    public void returnImg(String text) {
                        Log.d(TAG, text);
                        Util.excGif(text, getContext());
                    }
                });
            }
        });

        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First To Use This Feature!!",
                        Toast.LENGTH_LONG).show();
                    return;
                }
                else if(selectedImg==null || selectedImg.isEmpty())
                {
                    Toast.makeText(getContext(), "Please select a gif/image file using *Select Image From Gallery* button!",
                        Toast.LENGTH_LONG).show();
                    return;
                }
                Util.startUploadPersonal(getContext(), selectedImg, new onImageUploaded() {
                    @Override
                    public void returnImg(String text) {
                        Log.d(TAG, text);
                        Util.excGifclan(text, getContext());
                    }
                });
            }
        });

        select.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchImagePicker();
            }
        });
    }

    private void launchImagePicker() {
        ImagePicker.with(this)
            .galleryOnly()
            .createIntent(new Function1<Intent, Unit>() {
                @Override
                public Unit invoke(Intent intent) {
                    startForProfileImageResult.launch(intent);
                    return null;
                }
            });

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startForProfileImageResult.launch(intent);
    }

}

