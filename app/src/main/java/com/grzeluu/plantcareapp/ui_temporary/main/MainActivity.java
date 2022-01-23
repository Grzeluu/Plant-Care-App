package com.grzeluu.plantcareapp.ui_temporary.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.databinding.ActivityMainBinding;
import com.grzeluu.plantcareapp.ui_temporary.base.App;
import com.grzeluu.plantcareapp.ui_temporary.base.BaseActivity;
import com.grzeluu.plantcareapp.ui_temporary.discover.DiscoverFragment;
import com.grzeluu.plantcareapp.view.LoginActivity;
import com.grzeluu.plantcareapp.ui_temporary.myPlants.MyPlantsFragment;
import com.grzeluu.plantcareapp.ui_temporary.suggest.SuggestFragment;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseActivity implements MainMvpView, NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private MainMvpPresenter presenter;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this, ((App) getApplication()).getUserStorage());

        presenter.checkUser();
        presenter.onNavMenuCreated();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void openDiscover() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DiscoverFragment())
                .commit();
    }

    @Override
    public void openMyPlants() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MyPlantsFragment())
                .commit();
    }

    @Override
    public void openSuggest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SuggestFragment())
                .commit();
    }

    @Override
    public void updateUsername(String username) {
        View headerView = binding.navView.getHeaderView(0);
        TextView drawerNameTextView = headerView.findViewById(R.id.tv_username);
        drawerNameTextView.setText(username);
    }

    @Override
    public void updateEmail(String email) {
        View headerView = binding.navView.getHeaderView(0);
        TextView drawerEmailTextView = headerView.findViewById(R.id.tv_email);
        drawerEmailTextView.setText(email);
    }

}