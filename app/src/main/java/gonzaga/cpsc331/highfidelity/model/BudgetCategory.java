package gonzaga.cpsc331.highfidelity.model;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import gonzaga.cpsc331.highfidelity.adapter.BudgetCategoryAdapter;
import gonzaga.cpsc331.highfidelity.adapter.BudgetRowAdapter;

public class BudgetCategory {
    private String name;
    private List<BudgetRow> rows;

    public BudgetCategory(String name, List<BudgetRow> rows) {
        this.name = name;
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public List<BudgetRow> getRows() {
        return rows;
    }

    public void addRow(BudgetRow row) {
        rows.add(row);
    }
}