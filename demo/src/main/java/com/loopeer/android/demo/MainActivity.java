package com.loopeer.android.demo;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.loopeer.android.demo.databinding.ActivityMainBinding;
import com.loopeer.android.popuputils.RecyclerAdapter;
import com.loopeer.android.popuputils.SimpleThreeLayersPopup;
import com.loopeer.android.popuputils.SimpleTwoLayersPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mPrimaryData;
    private HashMap<String, List<String>> mMapDatas;

    private List<Model> mPrimary;
    private List<Model> mSecondary;
    private List<Model> mTertiary;

    private SimpleTwoLayersPopup<String, String> mSimpleTwoLayersPopup;
    private SimpleThreeLayersPopup<Model, Model, Model> mThreeLayersPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(bind.anchor);

        mPrimaryData = new ArrayList<>();
        mMapDatas = new HashMap<>();

        mPrimary = new ArrayList<>();
        mSecondary = new ArrayList<>();
        mTertiary = new ArrayList<>();

//        buildTwoLayersPopup();

        buildThreeLayersPopup();

        bind.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mSimpleTwoLayersPopup.show(bind.anchor, 0, 0);
                mThreeLayersPopup.show(bind.anchor, 0, 0);
            }
        });
    }


    private void buildTwoLayersPopup() {

        for (int i = 0; i < 15; i++) {
            String primaryItem = "primaryItem: " + i;
            mPrimaryData.add(primaryItem);
            List<String> strings = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                strings.add(i + "-secondaryData: " + j);
            }

            mMapDatas.put(primaryItem, strings);
        }

        mSimpleTwoLayersPopup = new SimpleTwoLayersPopup<>(this);
        mSimpleTwoLayersPopup.setTwoLayersAdapter(new SimpleTwoLayersPopup.SimpleTwoLayersAdapter<String, String>() {
            @Override
            public List<String> setPrimaryData() {
                return mPrimaryData;
            }

            @Override
            public List<String> setSecondaryData(String s) {
                return mMapDatas.get(s);
            }
        });

        mSimpleTwoLayersPopup.setSecondaryItemClickListener(new RecyclerAdapter.OnItemClickListener<String>() {

            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                mSimpleTwoLayersPopup.dismiss();
            }

            @Override
            public void setItemStyle(RecyclerView.ViewHolder preItemViewHolder, RecyclerView.ViewHolder curItemViewHolder) {
                if (preItemViewHolder != null)
                    preItemViewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.bg_layers));
                if (curItemViewHolder != null)
                    curItemViewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
        });

        mSimpleTwoLayersPopup.setPrimaryItemClickListener(null);
    }

    private void buildThreeLayersPopup() {


        for (int i = 0; i < 5; i++) {
            Model model1 = new Model("primary--" + i);
            List<Model> data1 = new ArrayList<>();
            for (int j = 0; j < 40; j++) {
                Model model2 = new Model(i + "secondary--" + i);
                List<Model> data2 = new ArrayList<>();
                for (int k = 0; k < 50; k++) {
                    data2.add(new Model(j + "tertiary--" + k));
                }
                model2.setData(data2);
                data1.add(model2);
            }
            model1.setData(data1);
            mPrimary.add(model1);
        }

        mThreeLayersPopup = new SimpleThreeLayersPopup<>(this);
        mThreeLayersPopup.setThreeLayersAdapter(new SimpleThreeLayersPopup.SimpleThreeLayersAdapter<Model, Model, Model>() {
            @Override
            public List<Model> setTertiaryData(Model model) {
                return model.getData();
            }

            @Override
            public List<Model> setPrimaryData() {
                return mPrimary;
            }

            @Override
            public List<Model> setSecondaryData(Model model) {
                return model.getData();
            }
        });

        /*mThreeLayersPopup.setPrimaryItemClickListener(null);
        mThreeLayersPopup.setSecondaryItemClickListener(null);*/
    }
}
