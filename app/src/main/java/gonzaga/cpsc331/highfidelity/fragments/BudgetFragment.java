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

import java.util.ArrayList;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.adapter.BudgetCategoryAdapter;
import gonzaga.cpsc331.highfidelity.dialogs.CreateCategoryDialog;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;

public class BudgetFragment extends Fragment
        implements CreateCategoryDialog.CreateCategoryDialogListener {

    private BudgetCategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvCategories);

        adapter = new BudgetCategoryAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.btnAddCategory);
        addButton.setOnClickListener(v -> {
            new CreateCategoryDialog()
                    .show(getChildFragmentManager(), "createCategory");
        });

        return view;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name) {
        adapter.addCategory(new BudgetCategory(name));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // no-op
    }
}