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
import gr.aueb.softeng.project1805.domain.TransferType;

public class HistoryPageTransfers extends android.support.v4.app.Fragment {

    private View view;
    LayoutInflater inflater;
    ArrayList<View> transfers=new ArrayList<>();
    LinearLayout list;

    public HistoryPageTransfers() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.history_page_transfers, container, false);
        this.inflater=inflater;

        return view;
    }

    public void addItem(String title, String amount, String date, TransferType type) {
        list=view.findViewById(R.id.history_transfers_layout);

        final View item=inflater.inflate(R.layout.history_item, null);
        list.addView(item, 0);
        transfers.add(item);

        final TextView txtTitle=item.findViewById(R.id.title);
        txtTitle.setText(title);

        final TextView txtAmount=item.findViewById(R.id.amount);
        txtAmount.setText(amount);

        final TextView txtDate=item.findViewById(R.id.date);
        txtDate.setText(date);

        final ImageView imageView=item.findViewById(R.id.image);
        if (type==TransferType.Incoming) imageView.setImageResource(R.drawable.ic_incoming);
        else imageView.setImageResource(R.drawable.ic_outgoing);
    }

    public void clearView() {
        if (transfers.size()!=0) {
            for (View v : transfers) {
                list.removeView(v);
            }
            transfers.clear();
        }
    }
}
