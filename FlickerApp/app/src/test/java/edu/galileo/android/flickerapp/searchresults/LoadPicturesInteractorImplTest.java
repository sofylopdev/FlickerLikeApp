package edu.galileo.android.flickerapp.searchresults;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;

import static org.mockito.Mockito.verify;

public class LoadPicturesInteractorImplTest extends BaseTest{

    @Mock
    private SearchResultsRepository repository;

    private LoadPicturesInteractorImpl loadPicturesInteractor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        loadPicturesInteractor = new LoadPicturesInteractorImpl(repository);
    }

    @Test
    public void testExecuteLoading_ShouldCallRepositoryLoadPictures(){
        String tags = "blue";
        loadPicturesInteractor.executeLoading(tags);
        verify(repository).loadPictures(tags);
    }
}
