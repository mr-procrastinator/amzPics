/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.deco.amazingpics.model.rest;

import android.util.Log;

import com.google.gson.Gson;

import org.deco.amazingpics.model.FeedDataList;
import org.deco.amazingpics.model.Repository;
import org.deco.amazingpics.model.rest.exceptions.NetworkErrorException;
import org.deco.amazingpics.model.rest.exceptions.NetworkTimeOutException;
import org.deco.amazingpics.model.rest.exceptions.NetworkUknownHostException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class RestRepository implements Repository {

    private final PicsApi mPicsApi;
    public final static int MAX_ATTEMPS = 3;

    String clientId    = "f17ac5220f3f4087a164121a58b4cc39";

    @Inject
    public RestRepository() {

//        Gson gson = new GsonBuilder()
//            .registerTypeAdapterFactory(new CharacterItemAdapterFactory())
//            .create();

        RestAdapter picsApiAdapter = new RestAdapter.Builder()
            .setEndpoint(PicsApi.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .setConverter(new GsonConverter(new Gson()))
            .setErrorHandler(new RetrofitErrorHandler())
            .build();

        mPicsApi = picsApiAdapter.create(PicsApi.class);
    }

    @Override
    public Observable<FeedDataList> getFeedData() {
        return mPicsApi.getFeedData("290482318",clientId);
    }


    public class RetrofitErrorHandler implements retrofit.ErrorHandler {

        @Override
        public Throwable handleError(RetrofitError cause) {

            if (cause.getKind() == RetrofitError.Kind.NETWORK) {

                if (cause.getCause() instanceof SocketTimeoutException)
                    return new NetworkTimeOutException();

                else if (cause.getCause() instanceof UnknownHostException)
                    return new NetworkUknownHostException();

                else if (cause.getCause() instanceof ConnectException)
                    return cause.getCause();

            } else {
                Log.e("error", cause.getMessage());
                return new NetworkErrorException();
            }

            return new Exception();
        };
    }

//    @Override
//    public Observable<saulmm.avengers.model.Character> getCharacter(int characterId) {
//        return mPicsApi.getCharacter(characterId);
//    }
//
//    @Override
//    public Observable<List<Comic>> getCharacterComics(int characterId) {
//
//        final String comicsFormat   = "comic";
//        final String comicsType     = "comic";
//
//        return mPicsApi.getCharacterComics(characterId, comicsFormat, comicsType)
//            .retry((attemps, error) -> error instanceof SocketTimeoutException && attemps < MAX_ATTEMPS);
//    }
//
//    public Observable<RetrofitError> emitRetrofitError (RetrofitError retrofitError) {
//
//        return Observable.just(retrofitError);
//    }
}
