package gonzaga.cpsc331.highfidelity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import gonzaga.cpsc331.highfidelity.fragments.AnalysisFragment;
import gonzaga.cpsc331.highfidelity.fragments.BudgetFragment;
import gonzaga.cpsc331.highfidelity.fragments.ChartFragment;
import gonzaga.cpsc331.highfidelity.fragments.GoalsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Fragment budgetFragment = new BudgetFragment();
        Fragment chartFragment = new ChartFragment();
        Fragment goalsFragment = new GoalsFragment();
        Fragment analysisFragment = new AnalysisFragment();

        setCurrentFragment(budgetFragment);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.budget)
                setCurrentFragment(budgetFragment);
            else if (id == R.id.chart)
                setCurrentFragment(chartFragment);
            else if (id == R.id.goals)
                setCurrentFragment(goalsFragment);
            else if (id == R.id.analysis)
                setCurrentFragment(analysisFragment);

            return true;
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }
}