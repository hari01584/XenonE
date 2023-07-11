package cf.androefi.xenone;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class main extends Fragment {


    public main() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText ud=(EditText)getActivity().findViewById(R.id.editText);
        final EditText ps=(EditText)getActivity().findViewById(R.id.editText4);
        Util.init(getActivity(),ud,ps);
        Util.makeAnnouncements(getContext());
//        try {
//            Util.check_update(getContext());
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        Button sb = getActivity().findViewById(R.id.button);
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SharedPreferences save = getActivity().getSharedPreferences("uid", getActivity().MODE_PRIVATE);
                String txt = ud.getText().toString();
                String psd = ps.getText().toString();
                Util.userId = txt;
                Util.userPass = psd;
                SharedPreferences.Editor editor = save.edit();
                editor.putString("user", txt);
                editor.putString("pass", psd);
                editor.apply();
                if(txt.isEmpty()){
                    Toast.makeText(getActivity(), "ERROR! USERID CANT BE EMPTY!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(psd.isEmpty()){
                    Toast.makeText(getActivity(), "Automatic password login is no longer working due to recent blockman updates.. Please enter your password manually and try again! :(",
                        Toast.LENGTH_LONG).show();
                    return;
                }
                Captha(getContext());
            }
        });

    }

    private void Captha(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.captha_dialog, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
        ImageView imageView = dialog.findViewById(R.id.image);
        final EditText cod = dialog.findViewById(R.id.editTextcaptha);
        Button sb = dialog.findViewById(R.id.button);
        captha captcha = new captha(context);
        captcha.getCaptha(imageView);
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String code = cod.getText().toString();
                Util.capthacode = code;
                if(code.isEmpty()){
                    Toast.makeText(context, "ERROR! CODE CANNOT BE EMPTY!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Util.login(context);
                dialog.dismiss();
            }
        });
    }



}
