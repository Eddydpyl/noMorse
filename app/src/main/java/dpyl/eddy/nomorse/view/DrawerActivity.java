package dpyl.eddy.nomorse.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import dpyl.eddy.nomorse.R;

import static dpyl.eddy.nomorse.Constants.STATE_ABOUT;
import static dpyl.eddy.nomorse.Constants.STATE_KEY;
import static dpyl.eddy.nomorse.Constants.STATE_SETTINGS;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Integer state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
        roundedBitmapDrawable.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()));
        ImageView navHeader = navigationView.getHeaderView(0).findViewById(R.id.imageView_navHeader);
        navHeader.setImageDrawable(roundedBitmapDrawable);

        state = savedInstanceState != null ? savedInstanceState.getInt(STATE_KEY) : STATE_SETTINGS;
        navigationView.getMenu().getItem(state).setChecked(true);

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (state) {
            case STATE_SETTINGS:
                fragment = new SettingsFragment();
                setTitle(R.string.menu_settings);
                break;
            case STATE_ABOUT:
                fragment = new AboutFragment();
                setTitle(R.string.menu_about);
                break;
            default:
                fragment = new SettingsFragment();
                setTitle(R.string.menu_settings);
        } fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_KEY, state);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_help);
        if (menuItem != null) {
            if (state == STATE_SETTINGS) menuItem.setVisible(true);
            else menuItem.setVisible(false);
        } return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            // TODO
            return true;
        } return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_manage) {
            state = STATE_SETTINGS;
            fragment = new SettingsFragment();
            setTitle(R.string.menu_settings);
        } else if (id == R.id.nav_about) {
            state = STATE_ABOUT;
            fragment = new AboutFragment();
            setTitle(R.string.menu_about);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();
        return true;
    }
}
