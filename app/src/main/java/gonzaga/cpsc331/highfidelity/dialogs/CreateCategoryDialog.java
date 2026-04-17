package gonzaga.cpsc331.highfidelity.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import gonzaga.cpsc331.highfidelity.R;

public class CreateCategoryDialog extends DialogFragment {

    public interface CreateCategoryDialogListener {
        public void onCreateCategoryDialogPositiveClick(DialogFragment dialog, String name);
        public void onCreateCategoryDialogNegativeClick(DialogFragment dialog);
    }

    CreateCategoryDialogListener listener;
    EditText editText;

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
        if (parent instanceof CreateCategoryDialogListener) {
            listener = (CreateCategoryDialogListener) parent;
        } else {
            throw new ClassCastException("Parent fragment must implement CreateCategoryDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog.
        // Pass null as the parent view because it's going in the dialog layout.
        View view = inflater.inflate(R.layout.dialog_get_name, null);
        editText = view.findViewById(R.id.newCategoryNameField);
        editText.setHint("Category Name");
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCreateCategoryDialogPositiveClick(CreateCategoryDialog.this, editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateCategoryDialog.this.getDialog().cancel();
                        listener.onCreateCategoryDialogNegativeClick(CreateCategoryDialog.this);
                    }
                });

        return builder.create();
    }
}