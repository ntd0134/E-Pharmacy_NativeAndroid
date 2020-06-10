package prm.example.project.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import prm.example.project.R;
import prm.example.project.models.request_data.CategoryData;

public class RecyclerViewCategoryAdapter extends RecyclerView.Adapter<RecyclerViewCategoryAdapter.ViewHolder> {
    private static final String TAG = "CategoryAdapter";

    private Context mContext;
    private ArrayList<CategoryData.CategoryItem> mListCategory;

    public RecyclerViewCategoryAdapter(Context mContext, ArrayList<CategoryData.CategoryItem> mListCategory) {
        this.mContext = mContext;
        this.mListCategory = mListCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_catergory_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mListCategory.get(position).getName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked on: "+mListCategory.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_item_category_name);
            parentLayout = itemView.findViewById(R.id.category_parent_layout);
        }
    }
}
