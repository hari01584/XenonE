package cf.androefi.xenone;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class gif extends Fragment {


    public gif() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gif, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText linke=(EditText)getView().findViewById(R.id.gifl);
        Button bc = getView().findViewById(R.id.gifclan);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!Util.toOpen)
                {
                    Toast.makeText(getContext(), "You Have Been Blocked Using This Feature!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Util.lpar(1,getContext(),linke);
            }
        });

        Button gif = getView().findViewById(R.id.gif);
        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!Util.toOpen)
                {
                    Toast.makeText(getContext(), "You Have Been Blocked Using This Feature!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Util.lpar(0,getContext(),linke);

            }
        });

    }

}
