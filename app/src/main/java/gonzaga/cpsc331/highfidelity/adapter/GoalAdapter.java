package gonzaga.cpsc331.highfidelity.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;
import gonzaga.cpsc331.highfidelity.model.Goal;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder>{
    private final List<Goal> goals;
    public GoalAdapter(List<Goal> goals) {
        this.goals = goals;
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        EditText amount;
        ImageButton deleteButton;

        ProgressBar progressBar;
        TextWatcher amountWatcher;

        public GoalViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.goalTitle);
            amount = itemView.findViewById(R.id.);
            deleteButton = itemView.findViewById(R.id.btnDeleteGoal);
            progressBar = itemView.findViewById(R.id.progressGoal);
        }
    }

    @NonNull
    @Override
    public GoalAdapter.GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goal, parent, false);
        return new GoalAdapter.GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetRowAdapter.RowViewHolder holder, int position) {
        Goal goal = goals.get(position);

        holder.title.setText(goal.getName());

        if (holder.amountWatcher != null) {
            holder.amount.removeTextChangedListener(holder.amountWatcher);
        }

        holder.amount.setText(goal.getAmount().toString());
        holder.amountWatcher = new GoalAdapter.SimpleAmountWatcher(goal);
        holder.amount.addTextChangedListener(holder.amountWatcher);
        BigDecimal current = goal.getBudgetRow().getAmount();
        BigDecimal target = goal.getAmount();

        // Calculate percentage
        int progress = current.divide(target, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

        // Prevent crash if target is 0
        if (target.compareTo(BigDecimal.ZERO) == 0){
            progress = 0;
        }

        holder.progressBar.setProgress(progress);
        holder.deleteButton.setOnClickListener(v -> {
            goalAdapter.this.deleteRow(holder.getAbsoluteAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
        notifyItemInserted(goals.size() - 1);
    }

    public void deleteGoal(int position) {
        goals.remove(position);
        notifyItemRemoved(position);
    }

    private static class SimpleAmountWatcher implements TextWatcher {
        private final Goal goal;

        SimpleAmountWatcher(Goal goal) {
            this.goal = goal;
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
                goal.setAmount(BigDecimal.ZERO);
                return;
            }

            try {
                goal.setAmount(new BigDecimal(value));
            } catch (NumberFormatException ignored) {
                goal.setAmount(BigDecimal.ZERO);
            }
        }
    }

}
