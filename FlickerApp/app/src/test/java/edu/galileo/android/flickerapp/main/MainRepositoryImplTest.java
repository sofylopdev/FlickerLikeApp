package edu.galileo.android.flickerapp.main;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.main.events.MainActivityEvent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

public class MainRepositoryImplTest extends BaseTest {

    @Mock
    private MyEventBus eventBus;

    private MainRepositoryImpl repository;
    private ArgumentCaptor<MainActivityEvent> argumentCaptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        repository = new MainRepositoryImpl(eventBus);
        argumentCaptor = ArgumentCaptor.forClass(MainActivityEvent.class);
    }

    @Test
    public void testOnSearchClickedWithEmptyString_ShouldPostEmptyStringEvent() {
        String tags = "";

        repository.onSearchClicked(tags);
        verify(eventBus).post(argumentCaptor.capture());

        MainActivityEvent event = argumentCaptor.getValue();
        assertEquals(MainActivityEvent.EMPTY_STRING, event.getType());
        assertNull(event.getTags());
    }

    @Test
    public void testOnSearchClickedWithNotEmptyString_ShouldPostNotEmptyStringEvent() {
        String tags = "dogs";
        repository.onSearchClicked(tags);
        verify(eventBus).post(argumentCaptor.capture());

        MainActivityEvent event = argumentCaptor.getValue();
        assertEquals(MainActivityEvent.NOT_EMPTY_STRING, event.getType());
        assertEquals(tags, event.getTags());
    }
}
