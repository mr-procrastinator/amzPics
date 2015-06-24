/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.deco.amazingpics.model.rest;

import org.deco.amazingpics.model.FeedDataList;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface PicsApi {

    String END_POINT       = "https://api.instagram.com/";

    @GET("/v1/users/{userId}/media/recent/")
    Observable<FeedDataList> getFeedData(@Path("userId") String userId, @Query("client_id") String clientId);

}
