/*
 * SchedulesTransportType_ListViewItem_ArrayAdapter.java
 * Last modified on 05-05-2014 16:51-0400 by brianhmayo
 *
 * Copyright (c) 2014 SEPTA.  All rights reserved.
 */

package org.septa.android.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import org.septa.android.app.R;
import org.septa.android.app.activities.CommentsFormActionBarActivity;
import org.septa.android.app.models.FavoriteModel;
import org.septa.android.app.models.RecentlyViewedModel;
import org.septa.android.app.models.RouteModel;
import org.septa.android.app.models.SchedulesRouteModel;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class SchedulesRouteSelectionListViewItemArrayAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer  {
    public static final String TAG = SchedulesRouteSelectionListViewItemArrayAdapter.class.getName();
    private final Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<FavoriteModel> favorites = new ArrayList<FavoriteModel>();
    private ArrayList<RecentlyViewedModel> recentlyViewed = new ArrayList<RecentlyViewedModel>();
    private ArrayList<SchedulesRouteModel> routes = new ArrayList<SchedulesRouteModel>();

    String[] resourceEndNames;
    String leftImageStartName;
    String rightImageBackgroundName;

    String routeType = null;

    private int[] mSectionIndices = {0,1,2};
    private String[] sectionTitles = new String[]{ "Favorites", "Recently Viewed", "Routes"};

    public SchedulesRouteSelectionListViewItemArrayAdapter(Context context, String routeType) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        this.routeType = routeType;

        resourceEndNames = context.getResources().getStringArray(R.array.schedulesfragment_listview_bothimage_endnames);
        leftImageStartName = context.getString(R.string.schedulesfragment_listview_leftimage_startname);
        rightImageBackgroundName = context.getString(R.string.schedulesfragment_listview_rightimage_startname);

        String[] routeTitles = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q"};
        for (String routeTitle : routeTitles) {
            SchedulesRouteModel rm = new SchedulesRouteModel();
            rm.setRouteTitle(routeTitle);
            routes.add(rm);
        }

        FavoriteModel fm = null;
        RecentlyViewedModel rm = null;

        fm = new FavoriteModel();
        fm.setRouteTitle("a fav 1");
        favorites.add(fm);
        fm = new FavoriteModel();
        fm.setRouteTitle("a fav 2");
        favorites.add(fm);
        fm = new FavoriteModel();
        fm.setRouteTitle("a fav 3");
        favorites.add(fm);

        rm = new RecentlyViewedModel();
        rm.setRouteTitle("a recent 1");
        recentlyViewed.add(rm);
        rm = new RecentlyViewedModel();
        rm.setRouteTitle("a recent 2");
        recentlyViewed.add(rm);
        rm = new RecentlyViewedModel();
        rm.setRouteTitle("a recent 3");
        recentlyViewed.add(rm);
    }

    protected Object[] getItems() {
        ArrayList<Object> items = new ArrayList<Object>(getCount());
        items.addAll(favorites); items.addAll(recentlyViewed); items.addAll(routes);

        return items.toArray();
    }

    protected boolean isFavorite(int position) {

        return getItems()[position] instanceof FavoriteModel;
    }

    protected boolean isRecentlyViewed(int position) {

        return getItems()[position] instanceof RecentlyViewedModel;
    }

    @Override
    public int getCount() {
        return favorites.size()+recentlyViewed.size()+routes.size();
    }

    @Override
    public Object getItem(int position) {

        return getItems()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        if (isFavorite(position)) {
            FavoriteModel fm = (FavoriteModel)getItem(position);
            rowView = mInflater.inflate(R.layout.schedules_routeselection_favoriteandrecentlyviewed_listview_item, parent, false);
            TextView routeIdTextView = (TextView)rowView.findViewById(R.id.schedulesrouteselection_favoriterecentlyviewed_routeid_textview);
            TextView startRouteTextView = (TextView)rowView.findViewById(R.id.schedulesrouteselection_favoriterecentlyviewed_start_textview);
            TextView endRouteTextView = (TextView)rowView.findViewById(R.id.schedulesrouteselection_favoriterecentlyviewed_end_textview);

            routeIdTextView.setText(""+position);
            startRouteTextView.setText("Start: "+position+" startroute");
            endRouteTextView.setText("End: "+position+" end route");

            View transparentView = (View)rowView.findViewById(R.id.schedules_routeselection_favoriteandrecentlyviewed_transparentview);
            if (position == (favorites.size()-1)) {
                transparentView.setVisibility(View.VISIBLE);
            } else {
                transparentView.setVisibility(View.GONE);
            }
        } else {
            if (isRecentlyViewed(position)) {
                RecentlyViewedModel rm = (RecentlyViewedModel)getItem(position);
                rowView = mInflater.inflate(R.layout.schedules_routeselection_favoriteandrecentlyviewed_listview_item, parent, false);
                TextView routeIdTextView = (TextView)rowView.findViewById(R.id.schedulesrouteselection_favoriterecentlyviewed_routeid_textview);
                TextView startRouteTextView = (TextView)rowView.findViewById(R.id.schedulesrouteselection_favoriterecentlyviewed_start_textview);
                TextView endRouteTextView = (TextView)rowView.findViewById(R.id.schedulesrouteselection_favoriterecentlyviewed_end_textview);

                routeIdTextView.setText(""+position);
                startRouteTextView.setText("Start: "+position+" startroute");
                endRouteTextView.setText("End: "+position+" end route");

                View transparentView = (View)rowView.findViewById(R.id.schedules_routeselection_favoriteandrecentlyviewed_transparentview);
                if (position == (favorites.size()-1)) {
                    transparentView.setVisibility(View.VISIBLE);
                } else {
                    transparentView.setVisibility(View.GONE);
                }
            } else{
                SchedulesRouteModel rtm = (SchedulesRouteModel)getItem(position);
                rowView = mInflater.inflate(R.layout.schedules_routeselection_routes_listview_item, parent, false);
                ImageView leftIconImageView = (ImageView)rowView.findViewById(R.id.schedules_routeselect_item_leftImageView);
                ImageView rightBackgroundImageView = (ImageView)rowView.findViewById(R.id.schedules_routeselection_item_rightImageBackgroundview);
                TextView rightTextView = (TextView)rowView.findViewById(R.id.schedules_routeselection_item_rightTextView);

                int id = mContext.getResources().getIdentifier(leftImageStartName + routeType + "_small", "drawable", mContext.getPackageName());
                leftIconImageView.setImageResource(id);

                id = mContext.getResources().getIdentifier(rightImageBackgroundName + routeType, "drawable", mContext.getPackageName());
                rightBackgroundImageView.setImageResource(id);

                // TODO: given the travel type, set the left icon image and the right background color
                if (rightTextView != null) {
                    rightTextView.setText("NN | : " + position + " name here");
                } else {
                    int routePosition = position - favorites.size() - recentlyViewed.size();
                    Log.d(TAG, "not sure why rightTextView is null... blah, but I have route title of "+routes.get(routePosition).getRouteTitle());
                }
            }
        }

        return rowView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.schedules_routeselection_headerview, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.schedules_routeselection_sectionheader_textview);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        if (position < favorites.size()) { // in the favorites part of the list
            holder.text.setText(sectionTitles[0]);
            holder.text.setBackgroundColor(Color.parseColor("#990DA44A"));
        } else {
            if (favorites.size() == position) {
                holder.text.setText(sectionTitles[1]);
                holder.text.setBackgroundColor(Color.parseColor("#990DA44A"));
            } else {
                holder.text.setText(sectionTitles[2]);
                holder.text.setBackgroundColor(Color.parseColor("#99553344"));
            }
        }

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if (isFavorite(position)) {
            return 0;
        } else {
            if (isRecentlyViewed(position)) {
                return 1;
            }
        }

        return 2;
    }

    @Override
    public int getPositionForSection(int section) {
        switch (section) {
            case 0: {

                return 0;
            }
            case 1: {

                return favorites.size();
            }
            case 2: {

                return favorites.size()+recentlyViewed.size();
            }
            default: {
                return 0;
            }
        }
    }

    @Override
    public int getSectionForPosition(int position) {
        if (isFavorite(position)) {
            return 0;
        } else {
            if (isRecentlyViewed(position)) {
                return 1;
            }
        }

        return 2;
    }

    @Override
    public Object[] getSections() {

//        return sectionTitles;
        return new Object[]{"noo", "yee", "ooo"};
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }

}