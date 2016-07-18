package com.amisha.fattofabapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class DetailWidgetRemoteViewService extends RemoteViewsService
{
    private static final String[] EXERCISES_COLUMNS = {
            ExerciseProvider.DATABASE_NAME + "." + ExerciseProvider.NAME, ExerciseProvider.ID
    };

    static final int INDEX_EXERCISES_ID = 1;
    static final int INDEX_EXERCISE_NAME = 0;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor cursor=null;
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if(cursor!=null)
                {
                    cursor.close();
                }
                final long identityToken= Binder.clearCallingIdentity();
                cursor=getContentResolver().query(ExerciseProvider.CONTENT_URI,EXERCISES_COLUMNS,null,null,null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if(cursor!=null)
                {
                    cursor.close();
                    cursor=null;
                }
            }

            @Override
            public int getCount() {
                return cursor==null ? 0 : cursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if(position== AdapterView.INVALID_POSITION || cursor==null || !cursor.moveToPosition(position))
                {
                    return null;
                }
                RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.widget_layout);
                String name=cursor.getString(INDEX_EXERCISE_NAME);
                int id=cursor.getInt(INDEX_EXERCISES_ID);


                remoteViews.setTextViewText(R.id.widgetText, name);

                final Intent finalIntent=new Intent();
            //    finalIntent.setData(ExerciseProvider.DATABASE_NAME);
                remoteViews.setOnClickFillInIntent(R.id.widget_list,finalIntent);
                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(),R.layout.widget_layout);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if(cursor.moveToPosition(position))
                    return cursor.getInt(INDEX_EXERCISES_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
