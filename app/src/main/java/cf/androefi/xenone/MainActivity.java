package cf.androefi.xenone;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.res();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new main(), "Main");
        adapter.addFragment(new Supp(), "Support!");
        adapter.addFragment(new gif(), "Gif");
        adapter.addFragment(new bcubes(), "Other!");
        //adapter.addFragment(new VPN(), "VPN And Data");
        adapter.addFragment(new Tools(), "Misc!");
        adapter.addFragment(new credits(),"Credits!");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
