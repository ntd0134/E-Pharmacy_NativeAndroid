package prm.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import prm.example.project.R;
import prm.example.project.models.request_data.DrugData;

public class RecyclerViewDrugAdapter extends RecyclerView.Adapter<RecyclerViewDrugAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewDrugAdapter";

    private Context mContext;
    private ArrayList<DrugData.drugItem> mListDrugs;

    public RecyclerViewDrugAdapter(Context mContext, ArrayList<DrugData.drugItem> mListDrugs) {
        this.mContext = mContext;
        this.mListDrugs = mListDrugs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_drug_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mListDrugs.get(position).getName());
        holder.price.setText(mListDrugs.get(position).getCost()+"");
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return null!=mListDrugs?mListDrugs.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_item_drug_name);
            price = itemView.findViewById(R.id.txt_item_drug_price);
            parentLayout = itemView.findViewById(R.id.drug_parent_layout);
        }
    }
}
