package com.example.listviewjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cars[] ob1 = new cars[]{
                new cars("Alto 800", "Maruti", 2012, R.drawable.alto800v1),
        new cars("Alto 800 LXI", "Maruti Suzuki", 2020, R.drawable.alto800v2)
        };
        ListView listView = findViewById(R.id.lisView);
        listView.setAdapter(new SetTicket(ob1));
        }
    class SetTicket extends BaseAdapter{
        cars[] ob2;
        SetTicket(cars[] ob1 ){
            this.ob2 = ob1;
        }

        @Override
        public int getCount() {
            return ob2.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = getLayoutInflater().inflate(R.layout.cars_ticket,null);
            ImageView img = myView.findViewById(R.id.carImg);
            img.setImageResource(ob2[position].getImage());
            TextView txt = myView.findViewById(R.id.carTitle);
            txt.setText(ob2[position].getCompany());
            txt = myView.findViewById(R.id.carCompany);
            txt.setText(ob2[position].getName()+" : Year - "+ob2[position].getYear());
            return myView;
        }
    }
}