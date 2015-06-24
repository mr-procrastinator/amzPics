package org.deco.amazingpics.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.deco.amazingpics.R;
import org.deco.amazingpics.adapters.PicsListAdapter;
import org.deco.amazingpics.injector.AppModule;
import org.deco.amazingpics.injector.components.DaggerAppComponent;
import org.deco.amazingpics.model.FeedData;
import org.deco.amazingpics.model.FeedDataList;
import org.deco.amazingpics.model.Repository;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.activity_avengers_recycler)
    RecyclerView mAvengersRecycler;
    @InjectView(R.id.activity_avengers_toolbar)
    Toolbar mAvengersToolbar;
    @Inject
    Repository repository;
    public ArrayList<FeedData> feedDatas = new ArrayList<>();
    private PicsListAdapter picsListAdapter;
    private CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .build()
                .inject(this);
        ButterKnife.inject(this);
        initializeToolbar();
        initializeRecyclerView();
        loadRetrofitData();
    }

    private void initializeToolbar() {
        setSupportActionBar(mAvengersToolbar);
    }


    private void initializeRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAvengersRecycler.setLayoutManager(linearLayoutManager);
        picsListAdapter = new PicsListAdapter(feedDatas, this, position -> {
        });
        mAvengersRecycler.setAdapter(picsListAdapter);
    }

    private void loadRetrofitData(){
        Subscription subscription = repository.getFeedData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedDataList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("data", "error " + e.getMessage());
                    }

                    @Override
                    public void onNext(FeedDataList feedDatas) {
                        Log.e("data", "was loaded");
                        MainActivity.this.feedDatas.addAll(feedDatas.data);
                        picsListAdapter.updateList(MainActivity.this.feedDatas);
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}
