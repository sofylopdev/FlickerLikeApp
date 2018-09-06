package edu.galileo.android.flickerapp.main;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.main.events.MainActivityEvent;
import edu.galileo.android.flickerapp.main.ui.MainView;

public class MainPresenterImpl implements MainPresenter {

    private MyEventBus eventBus;
    private MainView view;
    private MainInteractor interactor;

    public MainPresenterImpl(MyEventBus eventBus, MainView view, MainInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainActivityEvent event) {
        if (view != null) {
            switch (event.getType()) {
                case MainActivityEvent.EMPTY_STRING:
                    view.onEmptySearchString();
                    break;
                case MainActivityEvent.NOT_EMPTY_STRING:
                    view.onNotEmptySearchString(event.getTags());
                    break;
            }
        }
    }

    @Override
    public void search(String search) {
        interactor.execute(search);
    }

    public MainView getView() {
        return view;
    }
}
