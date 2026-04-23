package gonzaga.cpsc331.highfidelity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.math.BigDecimal;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;

public class BudgetRowAdapter extends RecyclerView.Adapter<BudgetRowAdapter.RowViewHolder> {

    private final List<BudgetRow> rows;
    private OnRowClickListener listener;

    public interface OnRowClickListener {
        void onRowClick(BudgetRow row);
    }

    public BudgetRowAdapter(List<BudgetRow> rows) {
        this.rows = rows;
    }
//needed for click handeling
    public BudgetRowAdapter(List<BudgetRow> rows, OnRowClickListener listener) {
        this.rows = rows;
        this.listener = listener;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        EditText amount;
        ImageButton deleteButton;
        TextWatcher amountWatcher;

        public RowViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rowName);
            amount = itemView.findViewById(R.id.etRowAmount);
            deleteButton = itemView.findViewById(R.id.btnDeleteRow);
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
        BudgetRow row = rows.get(position);

        holder.title.setText(row.getName());

        if (holder.amountWatcher != null) {
            holder.amount.removeTextChangedListener(holder.amountWatcher);
        }

        holder.amount.setText(row.getAmount().toString());
        holder.amountWatcher = new SimpleAmountWatcher(row);
        holder.amount.addTextChangedListener(holder.amountWatcher);

        holder.deleteButton.setOnClickListener(v -> {
            BudgetRowAdapter.this.deleteRow(holder.getAbsoluteAdapterPosition());
        });
    }


    @Override
    public int getItemCount() {
        return rows.size();
    }

    public void addRow(BudgetRow row) {
        //rows.add(row);
        notifyItemInserted(rows.size() - 1);
    }

    public void deleteRow(int position) {
        rows.remove(position);
        notifyItemRemoved(position);
    }

    private static class SimpleAmountWatcher implements TextWatcher {
        private final BudgetRow row;

        SimpleAmountWatcher(BudgetRow row) {
            this.row = row;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // no-op
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // no-op
        }

        @Override
        public void afterTextChanged(Editable s) {
            String value = s.toString().trim();
            if (value.isEmpty() || value.equals(".")) {
                row.setAmount(BigDecimal.ZERO);
                return;
            }

            try {
                row.setAmount(new BigDecimal(value));
            } catch (NumberFormatException ignored) {
                row.setAmount(BigDecimal.ZERO);
            }
        }


    }
}
