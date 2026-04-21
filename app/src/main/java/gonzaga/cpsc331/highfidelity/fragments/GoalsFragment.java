package gonzaga.cpsc331.highfidelity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.adapter.BudgetCategoryAdapter;
import gonzaga.cpsc331.highfidelity.adapter.GoalAdapter;
import gonzaga.cpsc331.highfidelity.data.BudgetRepository;
import gonzaga.cpsc331.highfidelity.dialogs.CreateCategoryDialog;
import gonzaga.cpsc331.highfidelity.dialogs.CreateRowDialog;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;
import gonzaga.cpsc331.highfidelity.model.Goal;

public class GoalsFragment extends Fragment {
    private RecyclerView recyclerView;
    private GoalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        recyclerView = view.findViewById(R.id.);

        adapter = new GoalAdapter(, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.btnAddGoal);
        addButton.setOnClickListener(v -> {
            new CreateCategoryDialog()
                    .show(getChildFragmentManager(), "createCategory");
        });

        return view;
    }
    public void onCreateCategoryDialogPositiveClick(DialogFragment dialog, String name, BudgetRow row, BigDecimal amount) {
        adapter.addGoal(new Goal(name, row, amount);
    }

    @Override
    public void onCreateCategoryDialogNegativeClick(DialogFragment dialog) {
        // no-op
    }

    @Override
    public void onCreateGoalPositiveClick(DialogFragment dialog, String name, BudgetRow row, BigDecimal amount) {
        int position = ((CreateRowDialog)dialog).getCategoryPosition();
        adapter.addGoal(recyclerView, position, new Goal(name, row, amount));
    }

    @Override
    public void onCreateGoalDialogNegativeClick(DialogFragment dialog) {
        // no-op
    }
}