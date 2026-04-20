package gonzaga.cpsc331.highfidelity.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;

public final class BudgetRepository {

    private static final List<BudgetCategory> categories = new ArrayList<>();

    static {
        seedData();
    }

    private BudgetRepository() {
    }

    public static List<BudgetCategory> getCategories() {
        return categories;
    }

    private static void seedData() {
        if (!categories.isEmpty()) {
            return;
        }

        categories.add(new BudgetCategory("Income", new ArrayList<>(List.of(
                new BudgetRow("Paycheck 1", new BigDecimal("2755.00")),
                new BudgetRow("Paycheck 2", new BigDecimal("2980.00"))
        ))));

        categories.add(new BudgetCategory("Savings", new ArrayList<>(List.of(
                new BudgetRow("Savings Fund", new BigDecimal("500.00"))
        ))));

        categories.add(new BudgetCategory("Housing", new ArrayList<>(List.of(
                new BudgetRow("Rent", new BigDecimal("875.00")),
                new BudgetRow("Utilities", new BigDecimal("80.00"))
        ))));
    }
}
