package gonzaga.cpsc331.highfidelity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.dialogs.CreateCategoryDialog;
import gonzaga.cpsc331.highfidelity.dialogs.CreateRowDialog;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;

public class BudgetCategoryAdapter extends RecyclerView.Adapter<BudgetCategoryAdapter.ViewHolder> {

    private final RecyclerView.RecycledViewPool sharedPool = new RecyclerView.RecycledViewPool();

    private final List<BudgetCategory> categories;
    private final Fragment parent;

    public BudgetCategoryAdapter(List<BudgetCategory> categories, Fragment parent) {
        this.categories = categories;
        this.parent = parent;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView rowsRecyclerView;
        BudgetRowAdapter rowAdapter;
        Button addRowButton;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.categoryName);
            rowsRecyclerView = itemView.findViewById(R.id.rowsRecyclerView);

            rowsRecyclerView.setLayoutManager(
                    new LinearLayoutManager(itemView.getContext())
            );
            rowsRecyclerView.setNestedScrollingEnabled(false);
            addRowButton = itemView.findViewById(R.id.addRowButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BudgetCategory category = categories.get(position);

        holder.name.setText(category.getName());

        if (holder.rowAdapter == null) {
            holder.rowAdapter = new BudgetRowAdapter(category.getRows());
            holder.rowsRecyclerView.setAdapter(holder.rowAdapter);
        }

        holder.addRowButton.setOnClickListener(v -> {
            new CreateRowDialog(position)
                    .show(parent.getChildFragmentManager(), "createRow");
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(BudgetCategory category) {
        categories.add(category);
        notifyItemInserted(categories.size() - 1);
    }

    public void addRowToCategory(RecyclerView recyclerView, int categoryPosition, BudgetRow row) {
        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(categoryPosition);
        if (vh instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) vh;
            if (holder.rowAdapter != null) {
                holder.rowAdapter.addRow(row);
            }
        }
    }
}
