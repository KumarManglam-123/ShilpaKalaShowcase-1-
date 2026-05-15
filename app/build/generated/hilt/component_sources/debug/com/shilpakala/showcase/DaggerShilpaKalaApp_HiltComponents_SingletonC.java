package com.shilpakala.showcase;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.shilpakala.showcase.core.network.ConnectivityObserver;
import com.shilpakala.showcase.core.utils.IdGenerator;
import com.shilpakala.showcase.core.utils.ImageCompressor;
import com.shilpakala.showcase.data.local.datastore.PreferencesManager;
import com.shilpakala.showcase.data.local.db.ShilpaKalaDatabase;
import com.shilpakala.showcase.data.local.db.dao.ArtworkDao;
import com.shilpakala.showcase.data.local.db.dao.HeritageDao;
import com.shilpakala.showcase.data.local.db.dao.ShilpiDao;
import com.shilpakala.showcase.data.local.db.dao.WipDao;
import com.shilpakala.showcase.data.repository.ArtworkRepositoryImpl;
import com.shilpakala.showcase.data.repository.HeritageRepositoryImpl;
import com.shilpakala.showcase.data.repository.SearchRepositoryImpl;
import com.shilpakala.showcase.data.repository.SettingsRepositoryImpl;
import com.shilpakala.showcase.data.repository.ShilpiRepositoryImpl;
import com.shilpakala.showcase.data.repository.WipRepositoryImpl;
import com.shilpakala.showcase.data.worker.CacheCleanupWorker;
import com.shilpakala.showcase.data.worker.CacheCleanupWorker_AssistedFactory;
import com.shilpakala.showcase.data.worker.SyncWorker;
import com.shilpakala.showcase.data.worker.SyncWorker_AssistedFactory;
import com.shilpakala.showcase.di.DatabaseModule_ProvideArtworkDaoFactory;
import com.shilpakala.showcase.di.DatabaseModule_ProvideDatabaseFactory;
import com.shilpakala.showcase.di.DatabaseModule_ProvideHeritageDaoFactory;
import com.shilpakala.showcase.di.DatabaseModule_ProvideShilpiDaoFactory;
import com.shilpakala.showcase.di.DatabaseModule_ProvideWipDaoFactory;
import com.shilpakala.showcase.domain.usecase.artwork.AddArtworkUseCase;
import com.shilpakala.showcase.domain.usecase.artwork.GetArtworksUseCase;
import com.shilpakala.showcase.domain.usecase.heritage.GetHeritageStoriesUseCase;
import com.shilpakala.showcase.domain.usecase.search.SearchArtworksUseCase;
import com.shilpakala.showcase.domain.usecase.shilpi.CreateShilpiUseCase;
import com.shilpakala.showcase.domain.usecase.shilpi.GetShilpiUseCase;
import com.shilpakala.showcase.domain.usecase.wip.AddWipStageUseCase;
import com.shilpakala.showcase.domain.usecase.wip.GetWipTimelineUseCase;
import com.shilpakala.showcase.feature.artwork.add.AddArtworkViewModel;
import com.shilpakala.showcase.feature.artwork.add.AddArtworkViewModel_HiltModules;
import com.shilpakala.showcase.feature.artwork.add.AddArtworkViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.artwork.add.AddArtworkViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.artwork.detail.ArtworkDetailViewModel;
import com.shilpakala.showcase.feature.artwork.detail.ArtworkDetailViewModel_HiltModules;
import com.shilpakala.showcase.feature.artwork.detail.ArtworkDetailViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.artwork.detail.ArtworkDetailViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.artwork.list.ArtworkListViewModel;
import com.shilpakala.showcase.feature.artwork.list.ArtworkListViewModel_HiltModules;
import com.shilpakala.showcase.feature.artwork.list.ArtworkListViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.artwork.list.ArtworkListViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.heritage.HeritageViewModel;
import com.shilpakala.showcase.feature.heritage.HeritageViewModel_HiltModules;
import com.shilpakala.showcase.feature.heritage.HeritageViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.heritage.HeritageViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.home.HomeViewModel;
import com.shilpakala.showcase.feature.home.HomeViewModel_HiltModules;
import com.shilpakala.showcase.feature.home.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.home.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.onboarding.OnboardingViewModel;
import com.shilpakala.showcase.feature.onboarding.OnboardingViewModel_HiltModules;
import com.shilpakala.showcase.feature.onboarding.OnboardingViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.onboarding.OnboardingViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.profile.ProfileViewModel;
import com.shilpakala.showcase.feature.profile.ProfileViewModel_HiltModules;
import com.shilpakala.showcase.feature.profile.ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.profile.ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.search.SearchViewModel;
import com.shilpakala.showcase.feature.search.SearchViewModel_HiltModules;
import com.shilpakala.showcase.feature.search.SearchViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.search.SearchViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.settings.SettingsViewModel;
import com.shilpakala.showcase.feature.settings.SettingsViewModel_HiltModules;
import com.shilpakala.showcase.feature.settings.SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.settings.SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.splash.SplashViewModel;
import com.shilpakala.showcase.feature.splash.SplashViewModel_HiltModules;
import com.shilpakala.showcase.feature.splash.SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.splash.SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.shilpakala.showcase.feature.wip.WipViewModel;
import com.shilpakala.showcase.feature.wip.WipViewModel_HiltModules;
import com.shilpakala.showcase.feature.wip.WipViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.shilpakala.showcase.feature.wip.WipViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DaggerShilpaKalaApp_HiltComponents_SingletonC {
  private DaggerShilpaKalaApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public ShilpaKalaApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements ShilpaKalaApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public ShilpaKalaApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements ShilpaKalaApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public ShilpaKalaApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements ShilpaKalaApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public ShilpaKalaApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements ShilpaKalaApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ShilpaKalaApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements ShilpaKalaApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ShilpaKalaApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements ShilpaKalaApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public ShilpaKalaApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements ShilpaKalaApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public ShilpaKalaApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends ShilpaKalaApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends ShilpaKalaApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends ShilpaKalaApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends ShilpaKalaApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(11).put(AddArtworkViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AddArtworkViewModel_HiltModules.KeyModule.provide()).put(ArtworkDetailViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ArtworkDetailViewModel_HiltModules.KeyModule.provide()).put(ArtworkListViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ArtworkListViewModel_HiltModules.KeyModule.provide()).put(HeritageViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HeritageViewModel_HiltModules.KeyModule.provide()).put(HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide()).put(OnboardingViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, OnboardingViewModel_HiltModules.KeyModule.provide()).put(ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ProfileViewModel_HiltModules.KeyModule.provide()).put(SearchViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SearchViewModel_HiltModules.KeyModule.provide()).put(SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SettingsViewModel_HiltModules.KeyModule.provide()).put(SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SplashViewModel_HiltModules.KeyModule.provide()).put(WipViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, WipViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectConnectivityObserver(instance, singletonCImpl.connectivityObserverProvider.get());
      MainActivity_MembersInjector.injectPreferencesManager(instance, singletonCImpl.preferencesManagerProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends ShilpaKalaApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddArtworkViewModel> addArtworkViewModelProvider;

    private Provider<ArtworkDetailViewModel> artworkDetailViewModelProvider;

    private Provider<ArtworkListViewModel> artworkListViewModelProvider;

    private Provider<HeritageViewModel> heritageViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private Provider<WipViewModel> wipViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private AddArtworkUseCase addArtworkUseCase() {
      return new AddArtworkUseCase(singletonCImpl.artworkRepositoryImplProvider.get(), singletonCImpl.idGeneratorProvider.get());
    }

    private GetArtworksUseCase getArtworksUseCase() {
      return new GetArtworksUseCase(singletonCImpl.artworkRepositoryImplProvider.get());
    }

    private GetHeritageStoriesUseCase getHeritageStoriesUseCase() {
      return new GetHeritageStoriesUseCase(singletonCImpl.heritageRepositoryImplProvider.get());
    }

    private GetShilpiUseCase getShilpiUseCase() {
      return new GetShilpiUseCase(singletonCImpl.shilpiRepositoryImplProvider.get());
    }

    private CreateShilpiUseCase createShilpiUseCase() {
      return new CreateShilpiUseCase(singletonCImpl.shilpiRepositoryImplProvider.get(), singletonCImpl.idGeneratorProvider.get());
    }

    private SearchArtworksUseCase searchArtworksUseCase() {
      return new SearchArtworksUseCase(singletonCImpl.searchRepositoryImplProvider.get());
    }

    private GetWipTimelineUseCase getWipTimelineUseCase() {
      return new GetWipTimelineUseCase(singletonCImpl.wipRepositoryImplProvider.get());
    }

    private AddWipStageUseCase addWipStageUseCase() {
      return new AddWipStageUseCase(singletonCImpl.wipRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addArtworkViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.artworkDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.artworkListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.heritageViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.wipViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(11).put(AddArtworkViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) addArtworkViewModelProvider)).put(ArtworkDetailViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) artworkDetailViewModelProvider)).put(ArtworkListViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) artworkListViewModelProvider)).put(HeritageViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) heritageViewModelProvider)).put(HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) homeViewModelProvider)).put(OnboardingViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) onboardingViewModelProvider)).put(ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) profileViewModelProvider)).put(SearchViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) searchViewModelProvider)).put(SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) settingsViewModelProvider)).put(SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) splashViewModelProvider)).put(WipViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) wipViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.shilpakala.showcase.feature.artwork.add.AddArtworkViewModel 
          return (T) new AddArtworkViewModel(viewModelCImpl.savedStateHandle, viewModelCImpl.addArtworkUseCase(), singletonCImpl.imageCompressorProvider.get());

          case 1: // com.shilpakala.showcase.feature.artwork.detail.ArtworkDetailViewModel 
          return (T) new ArtworkDetailViewModel(viewModelCImpl.savedStateHandle, viewModelCImpl.getArtworksUseCase());

          case 2: // com.shilpakala.showcase.feature.artwork.list.ArtworkListViewModel 
          return (T) new ArtworkListViewModel(viewModelCImpl.savedStateHandle, viewModelCImpl.getArtworksUseCase());

          case 3: // com.shilpakala.showcase.feature.heritage.HeritageViewModel 
          return (T) new HeritageViewModel(viewModelCImpl.getHeritageStoriesUseCase());

          case 4: // com.shilpakala.showcase.feature.home.HomeViewModel 
          return (T) new HomeViewModel(viewModelCImpl.getShilpiUseCase(), viewModelCImpl.getArtworksUseCase());

          case 5: // com.shilpakala.showcase.feature.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 6: // com.shilpakala.showcase.feature.profile.ProfileViewModel 
          return (T) new ProfileViewModel(viewModelCImpl.createShilpiUseCase(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.imageCompressorProvider.get());

          case 7: // com.shilpakala.showcase.feature.search.SearchViewModel 
          return (T) new SearchViewModel(viewModelCImpl.searchArtworksUseCase());

          case 8: // com.shilpakala.showcase.feature.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 9: // com.shilpakala.showcase.feature.splash.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.preferencesManagerProvider.get());

          case 10: // com.shilpakala.showcase.feature.wip.WipViewModel 
          return (T) new WipViewModel(viewModelCImpl.savedStateHandle, viewModelCImpl.getWipTimelineUseCase(), viewModelCImpl.addWipStageUseCase(), singletonCImpl.imageCompressorProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends ShilpaKalaApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends ShilpaKalaApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends ShilpaKalaApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<ShilpaKalaDatabase> provideDatabaseProvider;

    private Provider<HeritageRepositoryImpl> heritageRepositoryImplProvider;

    private Provider<CacheCleanupWorker_AssistedFactory> cacheCleanupWorker_AssistedFactoryProvider;

    private Provider<PreferencesManager> preferencesManagerProvider;

    private Provider<SyncWorker_AssistedFactory> syncWorker_AssistedFactoryProvider;

    private Provider<ConnectivityObserver> connectivityObserverProvider;

    private Provider<ArtworkRepositoryImpl> artworkRepositoryImplProvider;

    private Provider<IdGenerator> idGeneratorProvider;

    private Provider<ImageCompressor> imageCompressorProvider;

    private Provider<ShilpiRepositoryImpl> shilpiRepositoryImplProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<SearchRepositoryImpl> searchRepositoryImplProvider;

    private Provider<WipRepositoryImpl> wipRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private HeritageDao heritageDao() {
      return DatabaseModule_ProvideHeritageDaoFactory.provideHeritageDao(provideDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return MapBuilder.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>newMapBuilder(2).put("com.shilpakala.showcase.data.worker.CacheCleanupWorker", ((Provider) cacheCleanupWorker_AssistedFactoryProvider)).put("com.shilpakala.showcase.data.worker.SyncWorker", ((Provider) syncWorker_AssistedFactoryProvider)).build();
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private ArtworkDao artworkDao() {
      return DatabaseModule_ProvideArtworkDaoFactory.provideArtworkDao(provideDatabaseProvider.get());
    }

    private ShilpiDao shilpiDao() {
      return DatabaseModule_ProvideShilpiDaoFactory.provideShilpiDao(provideDatabaseProvider.get());
    }

    private WipDao wipDao() {
      return DatabaseModule_ProvideWipDaoFactory.provideWipDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<ShilpaKalaDatabase>(singletonCImpl, 2));
      this.heritageRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<HeritageRepositoryImpl>(singletonCImpl, 1));
      this.cacheCleanupWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<CacheCleanupWorker_AssistedFactory>(singletonCImpl, 0));
      this.preferencesManagerProvider = DoubleCheck.provider(new SwitchingProvider<PreferencesManager>(singletonCImpl, 4));
      this.syncWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<SyncWorker_AssistedFactory>(singletonCImpl, 3));
      this.connectivityObserverProvider = DoubleCheck.provider(new SwitchingProvider<ConnectivityObserver>(singletonCImpl, 5));
      this.artworkRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ArtworkRepositoryImpl>(singletonCImpl, 6));
      this.idGeneratorProvider = DoubleCheck.provider(new SwitchingProvider<IdGenerator>(singletonCImpl, 7));
      this.imageCompressorProvider = DoubleCheck.provider(new SwitchingProvider<ImageCompressor>(singletonCImpl, 8));
      this.shilpiRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ShilpiRepositoryImpl>(singletonCImpl, 9));
      this.settingsRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepositoryImpl>(singletonCImpl, 10));
      this.searchRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SearchRepositoryImpl>(singletonCImpl, 11));
      this.wipRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<WipRepositoryImpl>(singletonCImpl, 12));
    }

    @Override
    public void injectShilpaKalaApp(ShilpaKalaApp shilpaKalaApp) {
      injectShilpaKalaApp2(shilpaKalaApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private ShilpaKalaApp injectShilpaKalaApp2(ShilpaKalaApp instance) {
      ShilpaKalaApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.shilpakala.showcase.data.worker.CacheCleanupWorker_AssistedFactory 
          return (T) new CacheCleanupWorker_AssistedFactory() {
            @Override
            public CacheCleanupWorker create(Context context, WorkerParameters params) {
              return new CacheCleanupWorker(context, params, singletonCImpl.heritageRepositoryImplProvider.get());
            }
          };

          case 1: // com.shilpakala.showcase.data.repository.HeritageRepositoryImpl 
          return (T) new HeritageRepositoryImpl(singletonCImpl.heritageDao());

          case 2: // com.shilpakala.showcase.data.local.db.ShilpaKalaDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.shilpakala.showcase.data.worker.SyncWorker_AssistedFactory 
          return (T) new SyncWorker_AssistedFactory() {
            @Override
            public SyncWorker create(Context context2, WorkerParameters params2) {
              return new SyncWorker(context2, params2, singletonCImpl.heritageRepositoryImplProvider.get(), singletonCImpl.preferencesManagerProvider.get());
            }
          };

          case 4: // com.shilpakala.showcase.data.local.datastore.PreferencesManager 
          return (T) new PreferencesManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.shilpakala.showcase.core.network.ConnectivityObserver 
          return (T) new ConnectivityObserver(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 6: // com.shilpakala.showcase.data.repository.ArtworkRepositoryImpl 
          return (T) new ArtworkRepositoryImpl(singletonCImpl.artworkDao());

          case 7: // com.shilpakala.showcase.core.utils.IdGenerator 
          return (T) new IdGenerator();

          case 8: // com.shilpakala.showcase.core.utils.ImageCompressor 
          return (T) new ImageCompressor(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.shilpakala.showcase.data.repository.ShilpiRepositoryImpl 
          return (T) new ShilpiRepositoryImpl(singletonCImpl.shilpiDao());

          case 10: // com.shilpakala.showcase.data.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(singletonCImpl.preferencesManagerProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 11: // com.shilpakala.showcase.data.repository.SearchRepositoryImpl 
          return (T) new SearchRepositoryImpl(singletonCImpl.artworkDao(), singletonCImpl.shilpiDao());

          case 12: // com.shilpakala.showcase.data.repository.WipRepositoryImpl 
          return (T) new WipRepositoryImpl(singletonCImpl.wipDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
