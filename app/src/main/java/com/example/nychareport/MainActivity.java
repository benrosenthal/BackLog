package com.example.nychareport;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Classes.Constants;
import com.example.Classes.Problem;
import com.example.Classes.User;
import com.example.Fragments.LeaderBoardFragment;
import com.example.Fragments.MyCommunityFragment;
import com.example.Fragments.ProfileFragment;
import com.example.Fragments._311Fragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements android.app.ActionBar.TabListener,
        NavigationView.OnNavigationItemSelectedListener, LeaderBoardFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener, MyCommunityFragment.OnFragmentInteractionListener,
        _311Fragment.OnFragmentInteractionListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String PROBLEM = "PROBLEM";
    private Problem[] mProblems;
    SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Firebase mUserRef;
    private ValueEventListener mUserRefListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        /**
         * Create Firebase references
         */
        mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);

        /**
         * Link layout elements from XML and setup the toolbar
         */
        initializeScreen();

        /**
         * Set up Navigation View
         */

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * Add ValueEventListeners to Firebase references
         * to control get data and control behavior and visibility of elements
         */
        mUserRefListener = mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                /**
                 * Set the activity title to current user name if user is not null
                 */
                if (user != null) {
                    /* Assumes that the first word in the user's name is the user's first name. */
                    String firstName = user.getName().split("\\s+")[0];
                    String title = firstName + "'s Lists";
                    setTitle(title);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });

    }

    //UI methods
    public void initializeScreen() {
        mSectionPagerAdapter =
                new SectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Profile"));
        mTabLayout.addTab(mTabLayout.newTab().setText("My Commnity"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Investigate"));
        mTabLayout.addTab(mTabLayout.newTab().setText("311"));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }


    /**
     * Override onOptionsItemSelected to use main_menu instead of BaseActivity menu
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    /**
     * Override onOptionsItemSelected to add action_settings only to the MainActivity
     *
     * @param item
     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        /**
//         * Open SettingsActivity with sort options when Sort icon was clicked
//         */
//        if (id == R.id.action_sort) {
//            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.drawable.shodan_logo) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserRef.removeEventListener(mUserRefListener);
   }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTabSelected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }
//
//    /**
//     * Link layout elements from XML and setup the toolbar
//     */
//    public void initializeScreen() {
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);
//        /**
//         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(2)
//         **/
//        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
//        viewPager.setOffscreenPageLimit(2);
//        viewPager.setAdapter(adapter);
//        /**
//         * Setup the mTabLayout with view pager
//         */
//        tabLayout.setupWithViewPager(viewPager);
//    }

    /**
     * Create an instance of the AddList dialog fragment and show it
     */
//    public void showAddListDialog(View view) {
//        /* Create an instance of the dialog fragment and show it */
//        DialogFragment dialog = AddListDialogFragment.newInstance(mEncodedEmail);
//        dialog.show(MainActivity.this.getFragmentManager(), "AddListDialogFragment");
//    }

    /**
     * Create an instance of the AddMeal dialog fragment and show it
     */
//    public void showAddMealDialog(View view) {
//        /* Create an instance of the dialog fragment and show it */
//        DialogFragment dialog = AddMealDialogFragment.newInstance();
//        dialog.show(MainActivity.this.getFragmentManager(), "AddMealDialogFragment");
//    }

    /**
     * SectionPagerAdapter class that extends FragmentStatePagerAdapter to save fragments state
     */
    public class SectionPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> mFragments;

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<>();
            //mFragments.add(new LeaderBoardFragment());
            mFragments.add(new ProfileFragment());
            mFragments.add(new MyCommunityFragment());
            //mFragments.add(new ShodanFragment());
            mFragments.add(new _311Fragment());
        }

        @Override
        public Fragment getItem(int i) {
            //check if i exists if null then create instance fragment

            Fragment fragment = mFragments.get(i);
//            Fragment fragment = new LeaderBoardFragment();
//            Fragment fragment1 = new ProfileFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(SectionObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    //    public class SectionPagerAdapter extends FragmentStatePagerAdapter {
//
//        public SectionPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        /**
//         * Use positions (0 and 1) to find and instantiate fragments with newInstance()
//         *
//         * @param position
//         */
//        @Override
//        public Fragment getItem(int position) {
//
//            Fragment fragment = null;
//
//            /**
//             * Set fragment to different fragments depending on position in ViewPager
//             */
//            switch (position) {
//                case 0:
//                    fragment = ShoppingListsFragment.newInstance(mEncodedEmail);
//                    break;
//                case 1:
//                    fragment = MealsFragment.newInstance();
//                    break;
//                default:
//                    fragment = ShoppingListsFragment.newInstance(mEncodedEmail);
//                    break;
//            }
//
//            return fragment;
//        }
//
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        /**
//         * Set string resources as titles for each fragment by it's position
//         *
//         * @param position
//         */
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return getString(R.string.pager_title_shopping_lists);
//                case 1:
//                default:
//                    return getString(R.string.pager_title_meals);
//            }
//        }
//    }
//}
    public static class SectionObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }

    }
}
