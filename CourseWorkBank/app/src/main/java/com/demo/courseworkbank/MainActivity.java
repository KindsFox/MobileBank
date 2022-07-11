package com.demo.courseworkbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.courseworkbank.utils.Navigator;
import com.demo.courseworkbank.view.auth.LoginFragment;
import com.demo.courseworkbank.view.auth.RegistrationFragment;
import com.demo.courseworkbank.view.card.CardInfoFragment;
import com.demo.courseworkbank.view.operation.OperationsFragment;
import com.demo.courseworkbank.view.report.ReportFragment;

public class MainActivity extends AppCompatActivity implements Navigator {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigateTo(LoginFragment.newInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            App.getInstance().getFirebaseAuth().logOut();
            navigateTo(LoginFragment.newInstance());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateTo(Fragment fragment) {
        final int MAIN_CONTAINER_ID = R.id.main_container;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment instanceof RegistrationFragment
                || fragment instanceof CardInfoFragment
                || fragment instanceof OperationsFragment
                || fragment instanceof ReportFragment) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        } else {
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStack();
            }
        }

        transaction.replace(MAIN_CONTAINER_ID, fragment);
        transaction.commit();
    }
}