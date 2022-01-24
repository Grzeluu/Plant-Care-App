package com.grzeluu.plantcareapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.BaseActivity;
import com.grzeluu.plantcareapp.core.main.MainContract;
import com.grzeluu.plantcareapp.core.main.MainPresenter;
import com.grzeluu.plantcareapp.databinding.ActivityMainBinding;
import com.grzeluu.plantcareapp.ui_temporary.myPlants.MyPlantsFragment;
import com.grzeluu.plantcareapp.ui_temporary.suggest.SuggestFragment;

public class MainActivity extends BaseActivity
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private MainPresenter presenter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your Plants");
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);
        presenter.checkIfUserIsLoggedIn();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawer.setDrawerListener(toggle);

        binding.navView.setNavigationItemSelectedListener(this);

        openMyPlants();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_my_plants:
                openMyPlants();
                break;
            case R.id.nav_discover:
                openDiscover();
                break;
            case R.id.nav_suggest_new_plant:
                openSuggest();
                break;
            case R.id.nav_logout:
                presenter.onDrawerOptionLogoutClick();
                return true;
            default:
                return false;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void openDiscover() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DiscoverFragment())
                .commit();
    }

    private void openMyPlants() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MyPlantsFragment())
                .commit();
    }

    private void openSuggest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SuggestFragment())
                .commit();
    }

    @Override
    public void requireLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setUsername(String username) {
        View headerView = binding.navView.getHeaderView(0).findViewById(R.id.tv_username);
        TextView drawerNameTextView = headerView.findViewById(R.id.tv_username);
        drawerNameTextView.setText(username);
    }

    @Override
    public void setEmail(String email) {
        View headerView = binding.navView.getHeaderView(0);
        TextView drawerEmailTextView = headerView.findViewById(R.id.tv_email);
        drawerEmailTextView.setText(email);
    }
}