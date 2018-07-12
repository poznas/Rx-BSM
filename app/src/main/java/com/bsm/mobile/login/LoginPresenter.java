package com.bsm.mobile.login;


import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.util.LinkedList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import static com.bsm.mobile.login.LoginActivityMVP.*;

@Slf4j
public class LoginPresenter implements Presenter {

    private static final String AUTH_STATE_SIGNED_IN = "auth state - signed in";
    private static final String AUTH_STATE_SIGNED_OUT = "auth state - signed out";
    private static final String SIGN_IN_WITH_FAILURE = "sign in with Google to BSM failed";
    private View view;
    private Model model;

    private LinkedList<Disposable> subscriptions;

    public LoginPresenter(Model model) {
        this.model = model;
    }

    @Override
    public void attachView(View view) {
        this.view = view;
        subscriptions = new LinkedList<>();
    }

    @Override
    public void subscribeForAuth() {
        Disposable authSubscription = model.getSignInStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSignedIn -> {
                    if(isSignedIn && view != null){
                        log.info(AUTH_STATE_SIGNED_IN);
                        Log.i(getTag(), AUTH_STATE_SIGNED_IN);
                        view.goHomeActivity();
                    }else {
                        log.info(AUTH_STATE_SIGNED_OUT);
                        Log.i(getTag(), AUTH_STATE_SIGNED_OUT);
                    }
                });
        subscriptions.add(authSubscription);
    }

    @Override
    public void unsubscribe() {
        clearSubscriptions(subscriptions);
    }

    @Override
    public void handleGoogleSignInResult(GoogleSignInResult result) {
        view.hideProgress();
        if(result.isSuccess()){
            // Google sign in was successful, now auth with firebase
            view.showProgress();
            GoogleSignInAccount account = result.getSignInAccount();

            Disposable subscription = model.authWithGoogle(account)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate(view::hideProgress)
                    .subscribe(
                            signInSuccess -> {
                                if (signInSuccess){
                                    subscribeForAuth();
                                }else {
                                    view.showMessage(SIGN_IN_WITH_FAILURE);
                                }
                            },
                            error ->
                                    view.showMessage(error.getLocalizedMessage())
                    );
            subscriptions.add(subscription);
        }else {
            view.showMessage(result.getStatus().getStatusMessage());
        }
    }
}
