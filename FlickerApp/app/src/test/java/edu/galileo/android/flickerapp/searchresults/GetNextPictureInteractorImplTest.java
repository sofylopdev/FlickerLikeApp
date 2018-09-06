package edu.galileo.android.flickerapp.searchresults;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;

import static org.mockito.Mockito.verify;

public class GetNextPictureInteractorImplTest extends BaseTest {

    @Mock
    private SearchResultsRepository repository;

    private GetNextPictureInteractorImpl getNextPictureInteractor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getNextPictureInteractor = new GetNextPictureInteractorImpl(repository);
    }

    @Test
    public void testExecuteGetNext_ShouldCallRepositoryGetNextPicture(){
        getNextPictureInteractor.executeGetNext();
        verify(repository).getNextPicture();
    }
}
