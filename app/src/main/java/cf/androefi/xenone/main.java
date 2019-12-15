package cf.androefi.xenone;


import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        try {
            Util.check_update(getContext());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
                Util.login(getActivity());
            }
        });


    }




}
