package cf.androefi.xenone;


import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.skydoves.powerspinner.PowerSpinnerView;

public class bcubes extends Fragment {

    private static final String TAG = "r/bcubes";
    private PowerSpinnerView powerSpin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bcubes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        powerSpin = getView().findViewById(R.id.powerSpinnerView);

        Button dm = getActivity().findViewById(R.id.bcw);
        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First To Use This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Util.bcollect(getContext());
                Toast.makeText(getContext(), "Done You Got MAX Coupons For TODAY!!!",
                        Toast.LENGTH_LONG).show();
            }
        });

        Button bt2 = getActivity().findViewById(R.id.button2);
        bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First To Use This Feature!!",
                        Toast.LENGTH_LONG).show();
                    return;
                }

                String[] keys = getResources().getStringArray(R.array.keys_minigames);
                int i = powerSpin.getSelectedIndex();
                Log.i(TAG, "->"+i);
                if(i<0){
                    Toast.makeText(getContext(), "Please select a minigame where you want bonus ads items for free!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Util.rickRollItems(getContext(), keys[i]);
            }
        });
    }
}
