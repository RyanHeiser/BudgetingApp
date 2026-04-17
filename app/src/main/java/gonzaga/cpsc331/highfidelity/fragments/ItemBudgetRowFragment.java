package gonzaga.cpsc331.highfidelity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import gonzaga.cpsc331.highfidelity.R;

public class ItemBudgetRowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";

    // TODO: Rename and change types of parameters
    private String name;

    public ItemBudgetRowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BudgetCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemBudgetRowFragment newInstance(String param1) {
        ItemBudgetRowFragment fragment = new ItemBudgetRowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget_row, container, false);

        TextView label = view.findViewById(R.id.rowLabelText);
        label.setText(name);

        return view;
    }
}