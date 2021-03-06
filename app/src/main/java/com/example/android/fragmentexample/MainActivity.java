package com.example.android.fragmentexample;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.android.fragmentexample.R.id.open_button;


public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {


    private Button mButton;
    private boolean isFragmentDisplayed = false;
    private int mRadioButtonChoice = 2;
    private static final String TAG = "MainActivity";

    // Saved instance state key.
    static final String STATE_FRAGMENT = "state_of_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the button for opening and closing the fragment.
        mButton = findViewById(open_button);

        // fragment state and set the button text.
        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close);
            }
        }
        // Set Button onClick
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    Log.d(TAG, "onClick: Open");
                    displayFragment();
                } else {
                    closeFragment();
                    Log.d(TAG, "onClick: Close");
                }
            }
        });
    }

    //Open fragment onClick
    public void displayFragment() {
        // Initiate the fragment.
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();
        // Update the Button text.
        mButton.setText(R.string.close);
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;
    }

   //Close fragment onClick
    public void closeFragment() {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Update the Button text.
        mButton.setText(R.string.open);
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
        Toast.makeText(this, "Choice is " + Integer.toString(choice), Toast.LENGTH_SHORT).show();

    }
}
