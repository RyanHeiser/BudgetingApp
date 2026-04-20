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
import gonzaga.cpsc331.highfidelity.data.BudgetRepository;
import gonzaga.cpsc331.highfidelity.dialogs.CreateCategoryDialog;
import gonzaga.cpsc331.highfidelity.dialogs.CreateRowDialog;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;

public class BudgetFragment extends Fragment
        implements CreateCategoryDialog.CreateCategoryDialogListener, CreateRowDialog.CreateRowDialogListener {

    private BudgetCategoryAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        recyclerView = view.findViewById(R.id.rvCategories);

        adapter = new BudgetCategoryAdapter(BudgetRepository.getCategories(), this);
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
    public void onCreateCategoryDialogPositiveClick(DialogFragment dialog, String name) {
        adapter.addCategory(new BudgetCategory(name, new ArrayList<>()));
    }

    @Override
    public void onCreateCategoryDialogNegativeClick(DialogFragment dialog) {
        // no-op
    }

    @Override
    public void onCreateRowDialogPositiveClick(DialogFragment dialog, String name) {
        int position = ((CreateRowDialog)dialog).getCategoryPosition();
        adapter.addRowToCategory(recyclerView, position, new BudgetRow(name));
    }

    @Override
    public void onCreateRowDialogNegativeClick(DialogFragment dialog) {
        // no-op
    }
}
