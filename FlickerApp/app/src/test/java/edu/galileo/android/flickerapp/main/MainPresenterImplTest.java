package edu.galileo.android.flickerapp.main;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.main.events.MainActivityEvent;
import edu.galileo.android.flickerapp.main.ui.MainView;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterImplTest extends BaseTest {

    @Mock
    private MyEventBus eventBus;
    @Mock
    private MainView view;
    @Mock
    private MainInteractor interactor;

    private MainPresenterImpl presenter;

    @Mock
    MainActivityEvent event;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new MainPresenterImpl(eventBus, view, interactor);
    }


    @Test
    public void testOnCreate_subscribedToEventBus() {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_unsubscribedToEventBus() {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getView());
    }

    @Test
    public void testSearch_interactorShouldCallExecute() {
        String tags = "";
        presenter.search(tags);
        verify(interactor).execute(tags);
    }

    @Test
    public void testOnEventEmptyString_ViewShouldCallOnEmptySearchString() {
        when(event.getType()).thenReturn(MainActivityEvent.EMPTY_STRING);

        presenter.onEventMainThread(event);
        assertNotNull(presenter.getView());
        verify(view).onEmptySearchString();
    }

    @Test
    public void testOnEventNotEmptyString_ViewShouldCallOnNotEmptySearchString() {
        String tags = "cats";
        when(event.getType()).thenReturn(MainActivityEvent.NOT_EMPTY_STRING);
        when(event.getTags()).thenReturn(tags);

        presenter.onEventMainThread(event);
        assertNotNull(presenter.getView());
        verify(view).onNotEmptySearchString(event.getTags());
    }
}
