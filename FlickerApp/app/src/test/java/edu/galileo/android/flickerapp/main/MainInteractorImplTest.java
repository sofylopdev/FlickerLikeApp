package edu.galileo.android.flickerapp.main;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;

import static org.mockito.Mockito.verify;

public class MainInteractorImplTest extends BaseTest{
    @Mock
    private MainRepository repository;

    private MainInteractorImpl mainInteractor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainInteractor = new MainInteractorImpl(repository);
    }

    @Test
    public void testOnExecute_repositoryShouldCallOnSearchClicked(){
        String tags = "cats";
        mainInteractor.execute(tags);
        verify(repository).onSearchClicked(tags);
    }
}
