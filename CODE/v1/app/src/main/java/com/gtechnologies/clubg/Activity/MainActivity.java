package com.gtechnologies.clubg.Activity;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtechnologies.clubg.Fragment.Audio;
import com.gtechnologies.clubg.Fragment.Hits;
import com.gtechnologies.clubg.Fragment.Home;
import com.gtechnologies.clubg.Fragment.News;
import com.gtechnologies.clubg.Fragment.Picture;
import com.gtechnologies.clubg.Fragment.Video;
import com.gtechnologies.clubg.Interface.MenuInterface;
import com.gtechnologies.clubg.Library.KeyWord;
import com.gtechnologies.clubg.Library.Utility;
import com.gtechnologies.clubg.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MenuInterface {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    TextView pageTitle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Utility utility = new Utility(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pageTitle = (TextView) findViewById(R.id.page_title);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        navigationView.setNavigationItemSelectedListener(this);
        //utility.setFont(pageTitle);
        String topTitle = getResources().getString(R.string.titleEn);
        if (utility.getLangauge().equals("bn")) {
            topTitle = getResources().getString(R.string.titleBn);
        }
        pageTitle.setText(topTitle);
        callFragment(R.id.nav_home, new Home(MainActivity.this, MainActivity.this), KeyWord.HOME, topTitle);
        changeNavigationText();
    }

    @Override
    public void onBackPressed() {
        try {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    if (isTaskRoot()) {
                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        HashMap<String, Integer> screen = utility.getScreenRes();
                        int width = screen.get(KeyWord.SCREEN_WIDTH);
                        int height = screen.get(KeyWord.SCREEN_HEIGHT);
                        int mywidth = (width / 10) * 7;
                        final Dialog dialog = new Dialog(this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.setContentView(R.layout.dialog_layout);
                        TextView tv = (TextView) dialog.findViewById(R.id.permission_message);
                        Button yes = (Button) dialog.findViewById(R.id.dialog_yes);
                        Button no = (Button) dialog.findViewById(R.id.dialog_no);
                        tv.setText(getString(R.string.exit_message_bn));
                        LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.dialog_layout_size);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
                        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        params.width = mywidth;
                        ll.setLayoutParams(params);
                        yes.setText(getString(R.string.yes));
                        no.setText(getString(R.string.no));
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
//                            Fragment live = (Fragment) getSupportFragmentManager().findFragmentByTag(KeyWord.LIVE);
//                            if (live != null && live.isVisible()) {
//                                live.onDestroy();
//                            }
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.show();
                    } else {
                        super.onBackPressed();
                    }
                } else {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
        } catch (Exception ex) {
            //utility.call_error(ex);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            String topTitle = getResources().getString(R.string.titleBn);
            callFragment(R.id.nav_home, new Home(MainActivity.this, MainActivity.this), KeyWord.HOME, topTitle);
        } else if (id == R.id.nav_picture) {
            String topTitle = getResources().getString(R.string.picture_bn);
            callFragment(R.id.nav_picture, new Picture(MainActivity.this), KeyWord.PICTURE, topTitle);
        } else if (id == R.id.nav_song) {
            String topTitle = getResources().getString(R.string.song_bn);
            callFragment(R.id.nav_song, new Audio(MainActivity.this), KeyWord.SONG, topTitle);
        } else if (id == R.id.nav_video) {
            String topTitle = getResources().getString(R.string.video_bn);
            callFragment(R.id.nav_video, new Video(MainActivity.this), KeyWord.VIDEO, topTitle);
        } else if (id == R.id.nav_news) {
            String topTitle = getResources().getString(R.string.news_bn);
            callFragment(R.id.nav_news, new News(MainActivity.this), KeyWord.NEWS, topTitle);
        } /*else if (id == R.id.nav_newalbum) {
            String topTitle = getResources().getString(R.string.new_album);
            callFragment(R.id.nav_newalbum, new Hits(MainActivity.this, topTitle), KeyWord.NEW_ALBUM, topTitle);
        } else if (id == R.id.nav_category) {
            String topTitle = getResources().getString(R.string.catagory);
            callFragment(R.id.nav_category, new Hits(MainActivity.this, topTitle), KeyWord.CATEGORY, topTitle);
        } else if (id == R.id.nav_offline) {
            String topTitle = getResources().getString(R.string.offline);
            callFragment(R.id.nav_offline, new Hits(MainActivity.this, topTitle), KeyWord.OFFLINE, topTitle);
        } else if (id == R.id.nav_language) {
            callFragment(R.id.nav_language, new Hits(MainActivity.this, "Language"), KeyWord.LANGUAGE, "Language");
        }
        else if (id == R.id.nav_subscription) {
            callFragment(R.id.nav_language, new Hits(MainActivity.this, "Subscription"), KeyWord.LANGUAGE, "Subscription");
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callFragment(int itemId, android.support.v4.app.Fragment fragment, String tag, String title) {
        pageTitle.setText(title);
        //utility.setFont(pageTitle);
        try {
            while (fragmentManager.getBackStackEntryCount() != 0) {
                fragmentManager.popBackStackImmediate();
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.containerView, fragment, tag).commit();
            navigationView.getMenu().findItem(itemId).setChecked(true);
        } catch (Exception ex) {
            //utility.call_error(ex);
        }
    }

    private void changeNavigationText() {
        String language = utility.getLangauge();
        Menu menu = navigationView.getMenu();
        if (language.equals("bn")) {
            menu.findItem(R.id.nav_home).setTitle(R.string.home_bn);
            menu.findItem(R.id.nav_picture).setTitle(R.string.picture_bn);
            menu.findItem(R.id.nav_song).setTitle(R.string.song_bn);
            menu.findItem(R.id.nav_video).setTitle(R.string.video_bn);
            menu.findItem(R.id.nav_news).setTitle(R.string.news_bn);
            /*menu.findItem(R.id.nav_newalbum).setTitle(R.string.new_album_bn);
            menu.findItem(R.id.nav_category).setTitle(R.string.catagory_bn);
            menu.findItem(R.id.nav_offline).setTitle(R.string.offline_bn);
            menu.findItem(R.id.nav_subscription).setTitle(R.string.subscription_bn);*/
        } else {
            /*menu.findItem(R.id.nav_home).setTitle(R.string.home);
            menu.findItem(R.id.nav_hits).setTitle(R.string.hits);
            menu.findItem(R.id.nav_selected).setTitle(R.string.selected);
            menu.findItem(R.id.nav_mylist).setTitle(R.string.mylist);
            menu.findItem(R.id.nav_exclusive).setTitle(R.string.exclusive);
            menu.findItem(R.id.nav_newalbum).setTitle(R.string.new_album);
            menu.findItem(R.id.nav_category).setTitle(R.string.catagory);
            menu.findItem(R.id.nav_offline).setTitle(R.string.offline);
            menu.findItem(R.id.nav_subscription).setTitle(R.string.subscription);*/
        }
    }

    @Override
    public void setMenuChecked(String keyword) {
        switch (keyword){
            case "Picture":
                navigationView.getMenu().findItem(R.id.nav_picture).setChecked(true);
                break;
            case "Song":
                navigationView.getMenu().findItem(R.id.nav_song).setChecked(true);
                break;
            case "Video":
                navigationView.getMenu().findItem(R.id.nav_video).setChecked(true);
                break;
            case "News":
                navigationView.getMenu().findItem(R.id.nav_news).setChecked(true);
                break;
            default:
                navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                break;
        }
    }
}
