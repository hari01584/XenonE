package cf.androefi.xenone;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;


/**
 * A simple {@link Fragment} subclass.
 */
public class Supp extends Fragment implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;

    public Supp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bp = new BillingProcessor(getContext(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0QvAges3cM0mlHxbxMboENV1cTO0DirM4D/LfVwcnY/gmwONn21RsQjDCM8vCOMVhOcqvZdjx1Qam3EW8WUeVI1B8e8kE4k+xU0qdMxl4WMivhld61qpGK60nwGWVhWZAxC9BC6aeUipcGn9mHhsLEo4sZzjwdfARnyTty2zSKfGtbfAwBnjytEYzgVU2zJ/S9Iyxfy/GYRDPYFzUYFuGzIL5wTubN89nExtW03CXigsZ397AS7P5BxsmVQ3NhzT+rb6ObcACQHy//Sud5/sWIpoLyOV+KtgtmdGHoSTfCiTX5uPnVgwIMuwplMopslOh9IsN2KJhy3Tmhd1OvnW2wIDAQAB", this);
        bp.initialize();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supp, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Button vp = getView().findViewById(R.id.srank);

        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bp.subscribe(getActivity(), "xen_s_rank");
                Toast.makeText(getActivity(), "Sorry But S Ranks Are Not Working!!",
                        Toast.LENGTH_LONG).show();
            }

        });

        Button dn1 = getView().findViewById(R.id.dn01);

        dn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(getActivity(), "xenon_donate_01");
                bp.consumePurchase("xenon_donate_01");
            }

        });

        Button dn2 = getView().findViewById(R.id.dn02);

        dn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(getActivity(), "xenon_donate_02");
                bp.consumePurchase("xenon_donate_02");
            }

        });


        Button dn3 = getView().findViewById(R.id.dn03);

        dn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(getActivity(), "xenon_donate_03");
                bp.consumePurchase("xenon_donate_03");
            }

        });



    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(getActivity(), "Thanks For Purchasing :)!!",
                Toast.LENGTH_LONG).show();
        pToken();

    }

    public static String ptok = "";

    public void pToken()
    {
        @Nullable  TransactionDetails td = bp.getSubscriptionTransactionDetails("xen_s_rank");
        if (td != null && !TextUtils.isEmpty(td.purchaseInfo.purchaseData.purchaseToken)) {
            ptok = td.purchaseInfo.purchaseData.purchaseToken;
        }

    }

    @Override
    public void onPurchaseHistoryRestored() {
        for(String sku : bp.listOwnedSubscriptions()) {
            Log.d("PURCHASE", "Owned Subscription: " + sku);
            Toast.makeText(getActivity(), "Owned Subscription: " + sku,
                    Toast.LENGTH_LONG).show();
        }
        pToken();
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {
        pToken();
    }
}
