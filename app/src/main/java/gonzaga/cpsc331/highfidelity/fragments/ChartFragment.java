package gonzaga.cpsc331.highfidelity.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gonzaga.cpsc331.highfidelity.R;
import gonzaga.cpsc331.highfidelity.data.BudgetRepository;
import gonzaga.cpsc331.highfidelity.model.BudgetCategory;
import gonzaga.cpsc331.highfidelity.model.BudgetRow;
import gonzaga.cpsc331.highfidelity.views.PieChartView;

public class ChartFragment extends Fragment {

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private TextView currentMonthView;
    private TextView incomeAmountView;
    private TextView remainingAmountView;
    private PieChartView pieChartView;
    private LinearLayout expenseContainer;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        currentMonthView = rootView.findViewById(R.id.tvCurrentMonth);
        incomeAmountView = rootView.findViewById(R.id.tvIncomeAmount);
        remainingAmountView = rootView.findViewById(R.id.tvRemainingAmount);
        pieChartView = rootView.findViewById(R.id.pieChartView);
        expenseContainer = rootView.findViewById(R.id.llExpenseContainer);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindChartData();
    }

    private void bindChartData() {
        if (getContext() == null || rootView == null) {
            return;
        }

        LocalDate now = LocalDate.now();
        String monthLabel = now.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        currentMonthView.setText(monthLabel);

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        List<ExpenseSummary> expenseSummaries = new ArrayList<>();

        for (BudgetCategory category : BudgetRepository.getCategories()) {
            BigDecimal categoryTotal = BigDecimal.ZERO;
            for (BudgetRow row : category.getRows()) {
                categoryTotal = categoryTotal.add(row.getAmount());
            }

            if ("Income".equalsIgnoreCase(category.getName())) {
                income = income.add(categoryTotal);
            } else {
                totalExpenses = totalExpenses.add(categoryTotal);
                expenseSummaries.add(new ExpenseSummary(category.getName(), categoryTotal));
            }
        }

        BigDecimal remaining = income.subtract(totalExpenses);

        incomeAmountView.setText(currencyFormat.format(income));
        remainingAmountView.setText(currencyFormat.format(remaining));
        bindPieChart(expenseSummaries, remaining);
        bindExpenseRows(expenseSummaries, totalExpenses);
    }

    private void bindPieChart(List<ExpenseSummary> summaries, BigDecimal remaining) {
        int[] palette = new int[] {
                requireContext().getColor(R.color.chart_green),
                requireContext().getColor(R.color.chart_green_light),
                requireContext().getColor(R.color.chart_lavender),
                requireContext().getColor(R.color.chart_green_dark)
        };

        int sliceCount = summaries.size() + (remaining.signum() > 0 ? 1 : 0);
        float[] values = new float[sliceCount];

        for (int i = 0; i < summaries.size(); i++) {
            values[i] = summaries.get(i).amount.floatValue();
        }

        if (remaining.signum() > 0) {
            values[sliceCount - 1] = remaining.floatValue();
        }

        pieChartView.setChartData(values, palette);
    }

    private void bindExpenseRows(List<ExpenseSummary> summaries, BigDecimal totalExpenses) {
        expenseContainer.removeAllViews();

        if (summaries.isEmpty()) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setText("No expenses added yet.");
            emptyView.setTextSize(16f);
            emptyView.setTextColor(requireContext().getColor(R.color.chart_text_muted));
            expenseContainer.addView(emptyView);
            return;
        }

        int[] palette = new int[] {
                requireContext().getColor(R.color.chart_green),
                requireContext().getColor(R.color.chart_green_light),
                requireContext().getColor(R.color.chart_lavender),
                requireContext().getColor(R.color.chart_green_dark)
        };

        for (int i = 0; i < summaries.size(); i++) {
            ExpenseSummary summary = summaries.get(i);
            expenseContainer.addView(createExpenseRow(summary, totalExpenses, palette[i % palette.length]));
        }
    }

    private View createExpenseRow(ExpenseSummary summary, BigDecimal totalExpenses, int color) {
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(16), dp(14), dp(16), dp(14));

        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        rowParams.bottomMargin = dp(12);
        row.setLayoutParams(rowParams);

        TextView label = new TextView(requireContext());
        label.setText(summary.name);
        label.setTextColor(color);
        label.setTextSize(20f);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );
        row.addView(label, labelParams);

        TextView amount = new TextView(requireContext());
        String percent = totalExpenses.signum() == 0
                ? "0%"
                : Math.round(summary.amount.floatValue() / totalExpenses.floatValue() * 100f) + "%";
        amount.setText(currencyFormat.format(summary.amount) + "  " + percent);
        amount.setTextColor(requireContext().getColor(R.color.black));
        amount.setTextSize(18f);
        row.addView(amount);

        return row;
    }

    private int dp(int value) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }

    private static class ExpenseSummary {
        private final String name;
        private final BigDecimal amount;

        private ExpenseSummary(String name, BigDecimal amount) {
            this.name = name;
            this.amount = amount;
        }
    }
}
