/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.deco.amazingpics.injector.components;

import android.content.Context;

import org.deco.amazingpics.activities.MainActivity;
import org.deco.amazingpics.injector.AppModule;
import org.deco.amazingpics.model.Repository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {

    void inject (MainActivity detailActivity);

    Context app();
    Repository dataRepository();
}
