package gr.aueb.softeng.project1805.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gr.aueb.softeng.project1805.R;

public class HistoryPagePurchases extends android.support.v4.app.Fragment {

    private View view;
    LayoutInflater inflater;
    ArrayList<View> packages=new ArrayList<>();
    LinearLayout list;

    public HistoryPagePurchases() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.history_page_purchases, container, false);
        this.inflater=inflater;

        return view;
    }

    public void addItem(String title, String amount, String date) {
        list=view.findViewById(R.id.history_purchases_layout);

        final View item=inflater.inflate(R.layout.history_item, null);
        list.addView(item, 0);
        packages.add(item);

        final TextView txtTitle=item.findViewById(R.id.title);
        txtTitle.setText(title);

        final TextView txtAmount=item.findViewById(R.id.amount);
        txtAmount.setText(amount);

        final TextView txtDate=item.findViewById(R.id.date);
        txtDate.setText(date);

        final ImageView imageView=item.findViewById(R.id.image);
        imageView.setImageResource(R.drawable.ic_history_package);
    }

    public void clearView() {
        if (packages.size()!=0) {
            for (View v : packages) {
                list.removeView(v);
            }
            packages.clear();
        }
    }
}
