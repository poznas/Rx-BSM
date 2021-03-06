package com.bsm.mobile.domain.home;

import com.bsm.mobile.backend.notifications.INotificationService;
import com.bsm.mobile.backend.report.IPendingReportsService;
import com.bsm.mobile.backend.score.IScoreRepository;
import com.bsm.mobile.backend.user.IUserAuthService;
import com.bsm.mobile.backend.user.IUserPrivilegeRepository;

import dagger.Module;
import dagger.Provides;

import static com.bsm.mobile.domain.home.HomeActivityMVP.Model;
import static com.bsm.mobile.domain.home.HomeActivityMVP.Presenter;

@Module
public class HomeActivityModule {

    @Provides
    public Presenter provideHomePresenter(Model model){
        return new HomePresenter(model);
    }

    @Provides
    public Model provideHomeModel(IUserAuthService authService,
                                  INotificationService notificationService,
                                  IUserPrivilegeRepository privilegeRepository,
                                  IScoreRepository scoreRepository,
                                  IPendingReportsService pendingReportsService){

        return HomeModel.builder()
                .userAuthService(authService)
                .notificationService(notificationService)
                .privilegeRepository(privilegeRepository)
                .scoreRepository(scoreRepository)
                .pendingReportsService(pendingReportsService).build();

    }
}
