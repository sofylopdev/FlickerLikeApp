package edu.galileo.android.flickerapp.likedphotos;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;

import static org.mockito.Mockito.verify;

public class LikedPhotosInteractorImplTest extends BaseTest{

    @Mock
    private LikedPhotosRepository repository;

    private LikedPhotosInteractorImpl interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new LikedPhotosInteractorImpl(repository);
    }

    @Test
    public void testExecuteGetPictures_RepositoryCallGetPicturesFromDb() {
        interactor.executeGetPictures();
        verify(repository).getPicturesFromDb();
    }
}
