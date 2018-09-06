package edu.galileo.android.flickerapp;

import android.widget.Toast;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

public abstract class BaseTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        if (RuntimeEnvironment.application != null) {
            ShadowApplication shadowApp = Shadows.shadowOf(RuntimeEnvironment.application);
            shadowApp.grantPermissions("android.permission.INTERNET");
        }
    }

    protected List<Toast> getShadowToasts(){
        ShadowApplication shadowApp = Shadows.shadowOf(RuntimeEnvironment.application);
        return shadowApp.getShownToasts();
    }
}
