package neocode.ch.iamrich;

import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class IamRichActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private boolean showBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new FrontFragment())
                    .commit();
        } else {
            showBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        ((FrameLayout)findViewById(R.id.container)).setOnClickListener(clickListener);
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v ) {
            flipCard();
        }
    };

    private void flipCard() {
        if (showBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.
        showBack = true;

        getFragmentManager()
                .beginTransaction()

                // replace default animation
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                // replace fragment
                .replace(R.id.container, new BackFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        showBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }
}
