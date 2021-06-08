package cf.androefi.xenone;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View.OnClickListener;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tools extends Fragment {


    public Tools() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tools, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button vp = getView().findViewById(R.id.sexec);
        Button adMe = getView().findViewById(R.id.addmef);
        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(true) {
                    Toast.makeText(getContext(), "Feature not working!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    Util.executeSSS(getContext());
                }


            }
        });


        adMe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
                        Toast.LENGTH_LONG).show();
                    return;
                }

                Util.addMeFriend(getContext());
            }
        });
//
//        Button gv = getView().findViewById(R.id.freac);
//        gv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                if(Util.userId.isEmpty()){
//                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
//                            Toast.LENGTH_LONG).show();
//                    return;
//                }
//                final EditText ud=(EditText)getActivity().findViewById(R.id.email);
//                String txt = ud.getText().toString();
//                if(txt.isEmpty()){
//                    Toast.makeText(getActivity(), "ERROR! EMAIL CANT BE EMPTY!!",
//                            Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                Ion.with(getContext())
//                        .load("GET","http://skullzbones.com/xcv/bmg/give_away.php")
//                        .setHeader("userId", Util.userId)
//                        .setHeader("email", txt)
//                        .setHeader("dvID", Util.android_id)
//                        .asString()
//                        .setCallback(new FutureCallback<String>() {
//                            @Override
//                            public void onCompleted(Exception e, String res) {
//                                JSONObject jObject = null;
//                                int code = 0;
//                                String message = "ERROR IN JSON PARSING!!";
//                                try {
//                                    jObject = new JSONObject(res);
//                                    code = jObject.getInt("code");
//                                    message = jObject.getString("message");
//                                } catch (JSONException e1) {
//                                    e1.printStackTrace();
//                                }
//
//                                if (code == 0) {
//                                    Toast.makeText(getContext(), message,
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                                else if (code==1)
//                                {
//                                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                                    alert.setTitle("Message!!");
//                                    alert.setMessage(message);
//                                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                            // Canceled.
//                                        }
//                                    });
//                                    alert.show();
//                                }
//                            }
//                        });
//
//
//            }
//        });


    }



}
