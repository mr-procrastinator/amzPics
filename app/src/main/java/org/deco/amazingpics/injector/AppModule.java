/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.deco.amazingpics.injector;

import android.content.Context;

import org.deco.amazingpics.model.Repository;
import org.deco.amazingpics.model.rest.RestRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context mContext;

    public AppModule(Context application) {

        this.mContext = application;
    }

    @Provides @Singleton
    Context provideApplicationContext () { return mContext; }

    @Provides @Singleton
    Repository provideDataRepository (RestRepository repository) { return repository; }
}
