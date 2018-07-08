package akil.co.tz.notetaker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.transition.ChangeBounds;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import akil.co.tz.notetaker.Utils.NotificationUtil;
import akil.co.tz.notetaker.models.User;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import me.leolin.shortcutbadger.ShortcutBadger;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class BaseActivity extends AppCompatActivity {

    LinearLayout container;
    ConstraintSet constraintSetOld = new ConstraintSet();
    ConstraintSet constraintSetNew = new ConstraintSet();
    private boolean fullScreenSet = false;

    private User mUser;
    private Fragment mHostFragment;
    private BottomNavigationView navigation;

    private SharedPreferences prefs;
    private Boolean offline = false;

    Badge notifications_badge;
    private int mNotificationsIdx = 3;

    NavController navController;

    NotificationUtil notificationUtil;

    BottomNavigationMenuView bottomNavigationMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_base);

        container = findViewById(R.id.container);

//        constraintSetOld.clone(container);
//        constraintSetNew.clone(this, R.layout.activity_base_alt);

        navController = Navigation.findNavController(findViewById(R.id.main_nav_host_fragment));

        prefs = getDefaultSharedPreferences(getApplicationContext());

        notificationUtil = new NotificationUtil();

        navigation = findViewById(R.id.navigation);
        bottomNavigationMenuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        NavigationUI.setupWithNavController(navigation, navController);
//        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Log.d("WOURA", menuItem.getTitle().toString() + " selected");
//
//                NavigationUI.onNavDestinationSelected(menuItem, navController);
//
//                if(menuItem.getItemId() == R.id.navigation_notifications){
//
//                }
//
//                return true;
//            }
//        });

        ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
//        ShortcutBadger.with(getApplicationContext()).remove();  //for 1.1.3

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("notificationCountAdded"));

        String strJson = prefs.getString("saved_user",null);
        Log.d("WOURA", "LOGGED USER: " + strJson);

        if (strJson != null) {
            mUser = new Gson().fromJson(strJson, User.class);

            if(mUser != null && mUser.getRole() != null){
                if(mUser.getRole().equals("Admin")){
                    navigation.getMenu().getItem(2).setVisible(true);
                    mNotificationsIdx = 3;
                }
            }

            showNotificationCount(notificationUtil.getUnreadCount(getApplicationContext()));

            showNav();
        }else{
            logout();
        }
    }

    private void showNotificationCount(int count){
        View v = bottomNavigationMenuView.getChildAt(mNotificationsIdx);

        if(v != null){
            if(count > 0){
                notifications_badge = new QBadgeView(this).bindTarget(v)
                        .setBadgeNumber(count)
                        .setBadgeBackgroundColor(Color.parseColor("#ffa500"));
                notifications_badge.isExactMode();
            }
        }
        else
            Log.d("WOURA", "View out of bounds");
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager n = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            n.cancel("SMEMO", 0);

            if(navigation.getSelectedItemId() != R.id.navigation_notifications)
                showNotificationCount(notificationUtil.getUnreadCount(getApplicationContext()));
        }
    };

    public void logout(View view){
        logout();
    }

    public void removeNotificationsBadge(){
        if(notifications_badge != null)
            notifications_badge.hide(true);
    }

    public void login(){
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        showNav();
    }

    public void showNav(){
        TransitionSet transitionSet = new TransitionSet();
//        Slide transition = new Slide(Gravity.BOTTOM);
//        transition.addTarget(navigation);
//        transitionSet.addTransition(transition);
//        Animation slide = new TranslateAnimation(0, 0, 0, 64);
//        slide.setInterpolator(new LinearInterpolator());

//        ChangeBounds transition2 = new ChangeBounds();
//        transition2.setDuration(200L);
//        transition2.addTarget(findViewById(R.id.main_nav_host_fragment));
//        transitionSet.addTransition(transition2);
//        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);

//        TransitionManager.beginDelayedTransition(container, transitionSet);

//        findViewById(R.id.main_nav_host_fragment).getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//        navigation.setVisibility(View.VISIBLE);

//        TransitionManager.beginDelayedTransition(container);
//        constraintSetNew.applyTo(container);
//        navigation.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

        navigation.setVisibility(View.VISIBLE);
        navigation.setAlpha(0.0f);

        navigation.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(null);
    }

    public void hideNav(){
//        TransitionManager.beginDelayedTransition(container);
//        navigation.getLayoutParams().height = 0;
//        constraintSetOld.applyTo(container);

//        TransitionManager.beginDelayedTransition(container, new Slide());
//        navigation.setVisibility(View.GONE);

//        TransitionSet transitionSet = new TransitionSet();
//        Slide transition = new Slide(Gravity.BOTTOM);
//        transition.addTarget(navigation);
//        transitionSet.addTransition(transition);
//
//        ChangeBounds transition2 = new ChangeBounds();
//        transition2.addTarget(findViewById(R.id.main_nav_host_fragment));
//        transitionSet.addTransition(transition2);
//        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
//
//        TransitionManager.beginDelayedTransition(container, transitionSet);
//        findViewById(R.id.main_nav_host_fragment).getLayoutParams().height = 0;
//        navigation.setVisibility(View.GONE);

        navigation.animate()
                .translationY(navigation.getHeight())
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        navigation.setVisibility(View.GONE);
                    }
                });
    }

    private void logout(){
        if(mUser != null){
            if(mUser.getDepartment() != null)
                FirebaseMessaging.getInstance().unsubscribeFromTopic(mUser.getDepartment().replaceAll("\\s+",""));

            if(mUser.getRole() != null)
                FirebaseMessaging.getInstance().unsubscribeFromTopic(mUser.getRole().replaceAll("\\s+",""));
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("saved_user", null);
        editor.putString("subscribed_to_department", null);
        editor.putString("subscribed_to_role", null);
        editor.commit();

        NotificationUtil notificationUtil = new NotificationUtil();
        notificationUtil.emptyNotifications(getApplicationContext());

        navController.navigate(R.id.loginFragment);

        hideNav();
    }

}
