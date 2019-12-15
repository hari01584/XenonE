package cf.androefi.xenone;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import cf.androefi.xenone.util.FileUtils;
import cf.androefi.xenone.util.HttpUtils;
import cf.androefi.xenone.util.LogUtils;
import cf.androefi.xenone.vservice.DnsChange;

import static android.support.constraint.Constraints.TAG;
import static cf.androefi.xenone.VhostsActivity.PREFS_NAME;

public class VPN extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vpn, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button vp = getView().findViewById(R.id.vpn);
        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                /*Ion.with(getContext())
                        .load("http://skullzbones.com/xcv/bmg/vpn.php?f="+Util.userId)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String res) {
                                if(res.startsWith("NO")){
                                    Toast.makeText(getContext(), res,
                                            Toast.LENGTH_LONG).show();
                                }
                                else{

                                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("IP", res);
                                    clipboard.setPrimaryClip(clip);

                                    Toast.makeText(getContext(), "Done! Server IP Pasted In Clipboard!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });*/


            }
        });


        Button vpreq = getView().findViewById(R.id.vpnac);
        vpreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Util.userId.isEmpty()){
                    Toast.makeText(getContext(), "Please Login First B4 Using This Feature!!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                final String url_fetch = "http://skullzbones.com/xeno/hosts.php";
                Ion.with(getContext())
                        .load(url_fetch+"?idr="+Util.userId)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                try {
                                    FileUtils.writeFile(getContext().openFileOutput(VhostsActivity.NET_HOST_FILE, Context.MODE_PRIVATE), result);
                                    Toast.makeText(getContext(), String.format(getString(R.string.down_success), DnsChange.handle_hosts(getContext().openFileInput(VhostsActivity.NET_HOST_FILE))), Toast.LENGTH_LONG).show();
                                    SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(VhostsActivity.HOSTS_URL,url_fetch);
                                    editor.apply();
                                } catch (Exception e1) {
                                    Toast.makeText(getContext(), getString(R.string.down_error), Toast.LENGTH_LONG).show();
                                    LogUtils.e(TAG, e1.getMessage(), e1);
                                }
                            }
                        });

                Intent i = new Intent(getContext(),VhostsActivity.class);
                startActivity(i);
            }
        });

    }
}
