package cf.androefi.xenone;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class bcubes extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bcubes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Button dm = getActivity().findViewById(R.id.bcw);
        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Util.bcollect(getContext());
                Toast.makeText(getContext(), "Done You Got MAX Cubes For TODAY!!!",
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}
