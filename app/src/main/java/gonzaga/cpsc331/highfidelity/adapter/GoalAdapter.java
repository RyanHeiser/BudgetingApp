package gonzaga.cpsc331.highfidelity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;
import gonzaga.cpsc331.highfidelity.model.Goal;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {
    private final RecyclerView.RecycledViewPool sharedPool = new RecyclerView.RecycledViewPool();

    private List<Goal> goals;

    public GoalAdapter(List<Goal> goals) {
        this.goals = goals;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView goalAmount;
        RecyclerView rowsRecyclerView;
        Button addRowButton;
        ImageButton deleteButton;
        ProgressBar progressBar;
        TextView progressText;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvGoalName);
            goalAmount = itemView.findViewById(R.id.tvGoalAmount);
            deleteButton = itemView.findViewById(R.id.btnDeleteGoal);
            progressBar = itemView.findViewById(R.id.progressGoal);
            progressText = itemView.findViewById(R.id.tvGoalStatus);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goal, parent, false);
        return new GoalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goal goal = goals.get(position);

        holder.name.setText(goal.getName());
        holder.goalAmount.setText("$" + goal.getCurrentAmount() + "/" + goal.getGoalAmount());
        holder.progressBar.setMax(goal.getGoalAmount().intValue());
        holder.progressBar.setProgress(goal.getCurrentAmount().intValue());

        float ratio = goal.getCurrentAmount().floatValue() / goal.getGoalAmount().floatValue();
        holder.progressText.setText(Math.round(ratio * 100) + "%");

        holder.deleteButton.setOnClickListener(v -> {
            GoalAdapter.this.deleteGoal(holder.getAbsoluteAdapterPosition());
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

}
