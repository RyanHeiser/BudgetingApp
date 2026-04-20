package gonzaga.cpsc331.highfidelity.model;

import java.math.BigDecimal;

public class BudgetRow {
    private String name;
    BigDecimal amount;

    public BudgetRow(String name) {
        this(name, new BigDecimal("0.00"));
    }

    public BudgetRow(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

