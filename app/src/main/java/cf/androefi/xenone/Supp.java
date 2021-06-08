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
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.BillingResponseCode;
import com.android.billingclient.api.BillingClient.SkuType;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class Supp extends Fragment implements PurchasesUpdatedListener {

    private BillingClient billingClient;


    public Supp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
    }

    public void initialize() {
        billingClient = BillingClient
            .newBuilder(getActivity())
            .setListener(this)
            .enablePendingPurchases()
            .build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                makeSkuList();
                Toast.makeText(getContext(), "Successfully init billing services!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Logic from ServiceConnection.onServiceDisconnected should be moved here.
            }
        });
    }

    List<SkuDetails> skuList;
    public void makeSkuList(){
        List<String> s = new ArrayList<>();
        s.add("xenon_donate_01");
        s.add("xenon_donate_02");
        s.add("xenon_donate_03");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(s).setType(SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
            new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(BillingResult billingResult,
                    List<SkuDetails> skuDetailsList) {
                    skuList = new ArrayList<>(skuDetailsList);
                }
            });
    }

    @Override
    public void onPurchasesUpdated(@NonNull @NotNull BillingResult billingResult,
        @Nullable @org.jetbrains.annotations.Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingResponseCode.OK) {
            Toast.makeText(getContext(), "Thanks for this donation! :D", Toast.LENGTH_LONG).show();
            for (Purchase p : list) {
                ConsumeParams consumeParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(p.getPurchaseToken())
                    .build();
                billingClient.consumeAsync(consumeParams, null);
            }
        }
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
                donateInitiate(0);
            }

        });

        Button dn2 = getView().findViewById(R.id.dn02);

        dn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donateInitiate(1);
            }

        });


        Button dn3 = getView().findViewById(R.id.dn03);

        dn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donateInitiate(2);
            }

        });



    }

    private void donateInitiate(int i) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuList.get(i))
            .build();
        int responseCode = billingClient.launchBillingFlow(getActivity(), billingFlowParams).getResponseCode();

    }
}
