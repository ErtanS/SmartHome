package com.example.hal9000.smarthome.Views.OverView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hal9000.smarthome.Views.DeviceView.DynamicDeviceView;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;

import java.util.Collection;

@SuppressWarnings("ConstantConditions")
public class DynamicOverView extends AppCompatActivity {

    private OverViewDataManager initializeLists;

    /**
     * Activity Start
     * Inflaten der darzustellenden Zeilen in der View
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeLists = new OverViewDataManager(this);
        setContentView(R.layout.activity_dynamic_overview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout mContainerView = (LinearLayout) findViewById(R.id.containerView);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Bundle b = getIntent().getExtras();
        String category = b.getString(Config.STRING_INTENT_CATEGORY);
        setTitle(b.getString(Config.STRING_ACTIVITY_TITLE));
        Collection<String> list = initializeLists.getList(category);

        if (list != null && mContainerView != null) {
            for (String item : list) {
                mContainerView.addView(inflateRow(item, inflater, category), mContainerView.getChildCount());
            }
        }
    }

    /**
     * Inflaten einer Reihe
     *
     * @param label    Anzuzeigender name
     * @param inflater Inflater
     * @param category Kategorie
     * @return RowView
     */
    private View inflateRow(String label, LayoutInflater inflater, String category) {

        @SuppressLint("InflateParams") View rowView = inflater.inflate(R.layout.dynamic_row, null);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtDeviceRow);
        txtName.setText(initializeLists.uebersetzer(label));
        txtName.setOnClickListener(onClickListener(category, label));

        ImageView imNext = (ImageView) rowView.findViewById(R.id.imRowNext);
        imNext.setOnClickListener(onClickListener(category, label));

        return rowView;
    }

    /**
     * Wechsel der View beim Klick auf DynamicDeviceView
     * Setzt die Tags für die nächste Activity
     */
    private View.OnClickListener onClickListener(final String category, final String label) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(DynamicOverView.this, DynamicDeviceView.class);
                Bundle b = new Bundle();
                System.out.println(label);
                b.putString(category, label);
                b.putString(Config.STRING_ACTIVITY_TITLE, initializeLists.uebersetzer(label));
                nextScreen.putExtras(b);
                startActivity(nextScreen);
            }
        };
    }
}
