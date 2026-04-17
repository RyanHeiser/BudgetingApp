package gonzaga.cpsc331.highfidelity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;

public class BudgetRowAdapter extends RecyclerView.Adapter<BudgetRowAdapter.RowViewHolder> {

    private final List<BudgetRow> rows;

    public BudgetRowAdapter(List<BudgetRow> rows) {
        this.rows = rows;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        EditText amount;

        public RowViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rowName);
            amount = itemView.findViewById(R.id.etRowAmount);
        }
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_row, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        holder.title.setText(rows.get(position).getName());
        holder.amount.setText(rows.get(position).getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public void addRow(BudgetRow row) {
        //rows.add(row);
        notifyItemInserted(rows.size() - 1);
    }
}