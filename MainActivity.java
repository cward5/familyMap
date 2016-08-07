package edu.byu.cs.familymap.main;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.map.MyMapFragment;

public class MainActivity extends FragmentActivity {
    private LoginFragment loginFragment;
    private MyMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager=this.getSupportFragmentManager();
        loginFragment=(LoginFragment)manager.findFragmentById(R.id.loginFrameLayout);
        if(loginFragment==null)
        {
            loginFragment= LoginFragment.newInstance();
            loginFragment.setMainActivity(this);
            manager.beginTransaction().add(R.id.loginFrameLayout, loginFragment).commit();
        }
    }

    /**
     * called by login fragment after it finishes its jobs.
     */
    public void loggedIn()
    {
        FragmentManager manager=this.getSupportFragmentManager();
        //remove login activity
        manager.beginTransaction().remove(loginFragment).commit();
        //add map fragment
        mapFragment= MyMapFragment.newInstance();
        manager.beginTransaction().add(R.id.mapFragmentLayout, mapFragment).commit();
    }
}
