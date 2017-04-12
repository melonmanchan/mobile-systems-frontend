package fi.tutee.tutee.splash;

/**
 * Created by emmilinkola on 12/04/17.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private final SplashContract.View view;

    public SplashPresenter(SplashContract.View view
    ) {
        this.view = view;

        view.setPresenter(this);
    }
    @Override
    public void start() {

    }
}
