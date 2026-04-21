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

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import gonzaga.cpsc331.highfidelity.R;

public class CreateGoalDialog {

    public interface CreateGoalDialogListener {
        public void onCreateGoalDialogPositiveClick(DialogFragment dialog, String name);
        public void onCreateGoalDialogNegativeClick(DialogFragment dialog);
    }
    CreateGoalDialog.CreateGoalDialogListener listener;
    EditText editText;

    RecyclerView categoryView;

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
        if (parent instanceof CreateRowDialog.CreateRowDialogListener) {
            listener = (CreateGoalDialog.CreateGoalDialogListener) parent;
        } else {
            throw new ClassCastException("Parent fragment must implement CreateRowDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog.
        // Pass null as the parent view because it's going in the dialog layout.
        View view = inflater.inflate(R.layout.dialog_get_name, null);
        editText = view.findViewById(R.id.newCategoryNameField);
        editText.setHint("Amount");
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCreateGoalDialogPositiveClick(CreateGoalDialog.this, editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateGoalDialog.this.getDialog().cancel();
                        listener.onCreateGoalDialogNegativeClick(CreateGoalDialog.this);
                    }
                });


        return  builder.create();
    }
}
