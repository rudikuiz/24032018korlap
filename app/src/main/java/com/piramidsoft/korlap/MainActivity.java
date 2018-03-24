package com.piramidsoft.korlap;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.piramidsoft.korlap.adapters.NavigationBottomAdapter;
import com.piramidsoft.korlap.fragments.History;
import com.piramidsoft.korlap.fragments.Home;
import com.piramidsoft.korlap.fragments.Maps;
import com.piramidsoft.korlap.fragments.Profile;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.lin1)
    LinearLayout lin1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.lin2)
    LinearLayout lin2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.lin3)
    LinearLayout lin3;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.lin4)
    LinearLayout lin4;
    NavigationBottomAdapter pagerAdapter;
    @BindView(R.id.image5)
    ImageView image5;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.lin5)
    LinearLayout lin5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NavigationBottom();
    }

    private void NavigationBottom() {

        vpHome = (ViewPager) findViewById(R.id.vp_home);
        pagerAdapter = new NavigationBottomAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new Home(), "Home");
        pagerAdapter.addFragments(new Profile(), "Profile");
        pagerAdapter.addFragments(new Maps(), "Maps");
        pagerAdapter.addFragments(new History(), "History");
        vpHome.setAdapter(pagerAdapter);

        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(0);
            }
        });

        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(1);
            }
        });

        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(2);
            }
        });

        lin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(3);

            }
        });

        lin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
