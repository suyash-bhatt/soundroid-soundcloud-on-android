/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.soundroid.widgets.wiktionary;



import org.soundroid.R;
import org.soundroid.activities.main.SoundroidActivity;
import org.soundroid.activities.you.AboutMeActivity;
import org.soundroid.models.User;
import org.soundroid.oauth.Response;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

/**
 * Define a simple widget that shows the Wiktionary "Word of the day." To build
 * an update we spawn a background {@link Service} to perform the API queries.
 */
public class WordWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // To prevent any ANR timeouts, we perform the update in a service
        context.startService(new Intent(context, UpdateService.class));
    }
    
    public static class UpdateService extends Service {
        @Override
        public void onStart(Intent intent, int startId) {
            // Build the widget update for today
            RemoteViews updateViews = buildUpdate(this);
            
            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(this, WordWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, updateViews);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        /**
         * Build a widget update to show the current Wiktionary
         * "Word of the day." Will block until the online API returns.
         */
        public RemoteViews buildUpdate(Context context) {            
            RemoteViews views = null;
            
            Response<User> response = SoundroidActivity.client.getInfoAboutMe();
    		User user = response.getData();
       
      
                // Build an update that holds the updated widget contents
                views = new RemoteViews(context.getPackageName(), R.layout.widget_word);
                

                views.setTextViewText(R.id.word_title, user.getFullName());
                views.setTextViewText(R.id.word_type, user.getCity());
                views.setTextViewText(R.id.definition, user.getTrack_count());
                
            
                Intent defineIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AboutMeActivity.ACTION_ABOUT));
                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        0 /* no requestCode */, defineIntent, 0 /* no flags */);
                views.setOnClickPendingIntent(R.id.widget, pendingIntent);
                
  
            return views;
        }
    }
}
