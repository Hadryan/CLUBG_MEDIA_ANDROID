package com.gtechnologies.clubg.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gtechnologies.clubg.Adapter.ListAdapter;
import com.gtechnologies.clubg.Http.BaseApiClient;
import com.gtechnologies.clubg.Http.BaseApiInterface;
import com.gtechnologies.clubg.Library.EndlessScrollListener;
import com.gtechnologies.clubg.Library.KeyWord;
import com.gtechnologies.clubg.Library.Message;
import com.gtechnologies.clubg.Library.Utility;
import com.gtechnologies.clubg.Model.Content;
import com.gtechnologies.clubg.Model.ContentResponse;
import com.gtechnologies.clubg.R;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 9/11/2017.
 */

public class Audio extends Fragment {

    Context context;
    Utility utility;
    BaseApiInterface apiInterface = BaseApiClient.getWebClient().create(BaseApiInterface.class);
    ContentResponse contentResponse;
    List<Content> contents;
    ListAdapter adapter;
    ListView listView;
    TextView noData;
    GifImageView loadingImage;

    public Audio() {
    }

    @SuppressLint("ValidFragment")
    public Audio(Context context) {
        this.context = context;
        utility = new Utility(this.context);
        contents = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, null);
        listView = (ListView)view.findViewById(R.id.listview);
        noData = (TextView)view.findViewById(R.id.noData);
        loadingImage = (GifImageView)view.findViewById(R.id.loadingImage);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                utility.logger("Page: "+(page-1)+", Total Count: "+totalItemsCount);
                if(totalItemsCount%getResources().getInteger(R.integer.data_size)==0) {
                    getAudio(page - 1);
                }
                return true;
            }
        });
        getAudio(0);
        return view;
    }

    private void getAudio(int pageNumber){
        if(utility.isNetworkAvailable()) {
            Call<ContentResponse> call = apiInterface.getAudio(context.getString(R.string.authorization_key), pageNumber, getResources().getInteger(R.integer.data_size));
            call.enqueue(new Callback<ContentResponse>() {
                @Override
                public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                    try {
                        if (response.isSuccessful() && response.code() == 200) {
                            contentResponse = response.body();
                            contents.addAll(contentResponse.getContents());
                            if (contents.size() > 0) {
                                utility.hideAndShowView(new View[]{listView, noData, loadingImage}, listView);
                                adapter = new ListAdapter(context, contents, KeyWord.SONG);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                listView.setSelection(contents.size() > 20 ? contents.size() - contentResponse.getContents().size() - 6 : 0);
                            } else {
                                utility.hideAndShowView(new View[]{listView, noData, loadingImage}, noData);
                            }
                        } else {
                            utility.logger(String.valueOf(response.code()));
                            //utility.showToast(String.valueOf(response.code()));
                            utility.hideAndShowView(new View[]{listView, noData, loadingImage}, noData);
                        }
                    } catch (Exception ex) {
                        utility.logger(ex.toString());
                        //utility.showToast(ex.toString());
                        utility.hideAndShowView(new View[]{listView, noData, loadingImage}, noData);
                    }
                }

                @Override
                public void onFailure(Call<ContentResponse> call, Throwable t) {
                    utility.logger(t.toString());
                    utility.showToast(Message.HTTP_ERROR);
                }
            });
        }
        else {
            utility.showToast(Message.NO_NET);
        }
    }
}
