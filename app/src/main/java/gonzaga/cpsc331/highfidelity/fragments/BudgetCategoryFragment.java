package gonzaga.cpsc331.highfidelity.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gonzaga.cpsc331.highfidelity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";

    // TODO: Rename and change types of parameters
    private String name;

    public BudgetCategoryFragment(String name) {
        this.name = name;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BudgetCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetCategoryFragment newInstance(String param1) {
        BudgetCategoryFragment fragment = new BudgetCategoryFragment("New Category");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            Button add = (Button) container.findViewById(R.id.addButton);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.budget_category, container, false);
    }
}