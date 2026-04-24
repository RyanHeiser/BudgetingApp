package gonzaga.cpsc331.highfidelity.dialogs;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.adapter.BudgetRowAdapter;
import gonzaga.cpsc331.highfidelity.data.BudgetRepository;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;

public class CreateGoalDialog extends DialogFragment {

    public interface CreateGoalDialogListener {
        public void onCreateGoalDialogPositiveClick(DialogFragment dialog, String name, BigDecimal amount);
        public void onCreateGoalDialogNegativeClick(CreateGoalDialog dialog, int position);
    }
    CreateGoalDialog.CreateGoalDialogListener listener;

    private BudgetRow selectedRow;
    EditText editText;

    RecyclerView GoalView;

    // Override the Fragment.onAttach() method to instantiate the
    // NoticeDialogListener.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parent = getParentFragment();

        if (parent == null) {
            throw new IllegalStateException("Dialog must be shown from a Fragment");
        }

        // Verify that the host fragment implements the callback interface.
        if (parent instanceof CreateGoalDialog.CreateGoalDialogListener) {
            listener = (CreateGoalDialog.CreateGoalDialogListener) parent;
        } else {
            throw new ClassCastException("Parent fragment must implement CreateGoalDialogListener");
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater.
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog.
        // Pass null as the parent view because it's going in the dialog layout.
        View view = inflater.inflate(R.layout.goal_dialog, null);

        EditText nameInput = view.findViewById(R.id.goalName);
        EditText amountInput = view.findViewById(R.id.goalAmount);



        List<BudgetRow> Rows = new ArrayList<>();
        for (BudgetCategory category : BudgetRepository.getCategories()) {
            Rows.addAll(category.getRows());
        }
        BudgetRowAdapter rowAdapter = new BudgetRowAdapter(Rows, row -> { selectedRow = row; } );

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = nameInput.getText().toString().trim();
                        String amountText = amountInput.getText().toString().trim();

                        if (name.isEmpty() || amountText.isEmpty()) {
                            return; // basic validation
                        }

                        BigDecimal amount;
                        try {
                            amount = new BigDecimal(amountText);
                        } catch (Exception e) {
                            amount = BigDecimal.ZERO;
                        }

                        listener.onCreateGoalDialogPositiveClick(
                                CreateGoalDialog.this,
                                name,
                                amount
                        );
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        listener.onCreateGoalDialogNegativeClick(CreateGoalDialog.this, id);
                    }
                });


        return  builder.create();
    }
}
