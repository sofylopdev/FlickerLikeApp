package edu.galileo.android.flickerapp.searchresults;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.entities.Picture;

import static org.mockito.Mockito.verify;

public class SavePictureInteractorImplTest extends BaseTest {

    @Mock
    private SearchResultsRepository repository;

    @Mock
    private Picture picture;

    private SavePictureInteractorImpl savePictureInteractor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        savePictureInteractor = new SavePictureInteractorImpl(repository);
    }

    @Test
    public void testExecuteSave_ShouldCallRepositorySavePicture(){
        savePictureInteractor.executeSave(picture);
        verify(repository).savePicture(picture);
    }
}
