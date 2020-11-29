package com.gtechnologies.clubg.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gtechnologies.clubg.Activity.MainActivity;
import com.gtechnologies.clubg.Adapter.PictureRadapter;
import com.gtechnologies.clubg.Http.BaseApiClient;
import com.gtechnologies.clubg.Http.BaseApiInterface;
import com.gtechnologies.clubg.Interface.MenuInterface;
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

public class Home extends Fragment {

    Context context;
    Utility utility;
    RecyclerView pictureRV, songRV, videoRV, newsRV;
    GifImageView picture, song, video, news;
    TextView pictureText, songText, videoText, newsText;
    BaseApiInterface apiInterface = BaseApiClient.getWebClient().create(BaseApiInterface.class);
    ContentResponse pictureResponse, audioResponse, videoResponse, newsResponse;
    List<Content> pictureContents = null;
    List<Content> audioContents = null;
    List<Content> videoContents = null;
    List<Content> newsContents = null;

    LinearLayoutManager pictureManager, songManager, videoManager, newsManager;
    PictureRadapter pictureRadapter, songAdapter, videoAdapter, newsAdapter;
    MenuInterface menuInterface;

    public Home() {
    }

    @SuppressLint("ValidFragment")
    public Home(Context context, MenuInterface menuInterface) {
        this.context = context;
        utility = new Utility(this.context);
        pictureContents = new ArrayList<>();
        audioContents = new ArrayList<>();
        videoContents = new ArrayList<>();
        newsContents = new ArrayList<>();
        this.menuInterface = menuInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, null);
        pictureRV = (RecyclerView)view.findViewById(R.id.picture_rv);
        songRV = (RecyclerView)view.findViewById(R.id.song_rv);
        videoRV = (RecyclerView)view.findViewById(R.id.video_rv);
        newsRV = (RecyclerView)view.findViewById(R.id.news_rv);
        picture = (GifImageView)view.findViewById(R.id.picture_loader);
        song = (GifImageView)view.findViewById(R.id.song_loader);
        video = (GifImageView)view.findViewById(R.id.video_loader);
        news = (GifImageView)view.findViewById(R.id.news_loader);
        pictureText = (TextView) view.findViewById(R.id.picture_text);
        songText = (TextView) view.findViewById(R.id.song_text);
        videoText = (TextView) view.findViewById(R.id.video_text);
        newsText = (TextView) view.findViewById(R.id.news_text);
        pictureManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        songManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        videoManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        newsManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        pictureRV.setLayoutManager(pictureManager);
        songRV.setLayoutManager(songManager);
        videoRV.setLayoutManager(videoManager);
        newsRV.setLayoutManager(newsManager);
        getPicture(0);
        getSong(0);
        getVideo(0);
        getNews(0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        menuInterface.setMenuChecked(KeyWord.HOME);
    }

    private void getPicture(int pageNumber){
        if(utility.isNetworkAvailable()) {
            Call<ContentResponse> call = apiInterface.getImage(context.getString(R.string.authorization_key), pageNumber, getResources().getInteger(R.integer.data_size));
            call.enqueue(new Callback<ContentResponse>() {
                @Override
                public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                    try {
                        if (response.isSuccessful() && response.code() == 200) {
                            pictureResponse = response.body();
                            pictureContents.addAll(pictureResponse.getContents());
                            if (pictureContents.size() > 0) {
                                utility.hideAndShowView(new View[]{pictureRV, picture, pictureText}, pictureRV);
                                pictureRadapter = new PictureRadapter(context, pictureContents, KeyWord.PICTURE, menuInterface);
                                pictureRV.setAdapter(pictureRadapter);
                            } else {
                                utility.hideAndShowView(new View[]{pictureRV, picture, pictureText}, pictureText);
                            }
                        } else {
                            utility.logger(String.valueOf(response.code()));
                            //utility.showToast(String.valueOf(response.code()));
                            utility.hideAndShowView(new View[]{pictureRV, picture, pictureText}, pictureText);
                        }
                    } catch (Exception ex) {
                        utility.logger(ex.toString());
                        //utility.showToast(ex.toString());
                        utility.hideAndShowView(new View[]{pictureRV, picture, pictureText}, pictureText);
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

    private void getSong(int pageNumber){
        if(utility.isNetworkAvailable()) {
            Call<ContentResponse> call = apiInterface.getAudio(context.getString(R.string.authorization_key), pageNumber, getResources().getInteger(R.integer.data_size));
            call.enqueue(new Callback<ContentResponse>() {
                @Override
                public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                    try {
                        if (response.isSuccessful() && response.code() == 200) {
                            audioResponse = response.body();
                            audioContents.addAll(audioResponse.getContents());
                            if (audioContents.size() > 0) {
                                utility.hideAndShowView(new View[]{songRV, song, songText}, songRV);
                                songAdapter = new PictureRadapter(context, audioContents, KeyWord.SONG, menuInterface);
                                songRV.setAdapter(songAdapter);
                            } else {
                                utility.hideAndShowView(new View[]{songRV, song, songText}, songText);
                            }
                        } else {
                            utility.logger(String.valueOf(response.code()));
                            //utility.showToast(String.valueOf(response.code()));
                            utility.hideAndShowView(new View[]{songRV, song, songText}, songText);
                        }
                    } catch (Exception ex) {
                        utility.logger(ex.toString());
                        //utility.showToast(ex.toString());
                        utility.hideAndShowView(new View[]{songRV, song, songText}, songText);
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

    private void getVideo(int pageNumber){
        if(utility.isNetworkAvailable()) {
            Call<ContentResponse> call = apiInterface.getVideo(context.getString(R.string.authorization_key), pageNumber, getResources().getInteger(R.integer.data_size));
            call.enqueue(new Callback<ContentResponse>() {
                @Override
                public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                    try {
                        if (response.isSuccessful() && response.code() == 200) {
                            videoResponse = response.body();
                            videoContents.addAll(videoResponse.getContents());
                            if (videoContents.size() > 0) {
                                utility.hideAndShowView(new View[]{videoRV, video, videoText}, videoRV);
                                videoAdapter = new PictureRadapter(context, videoContents, KeyWord.VIDEO, menuInterface);
                                videoRV.setAdapter(videoAdapter);
                            } else {
                                utility.hideAndShowView(new View[]{videoRV, video, videoText}, videoText);
                            }
                        } else {
                            utility.logger(String.valueOf(response.code()));
                            //utility.showToast(String.valueOf(response.code()));
                            utility.hideAndShowView(new View[]{videoRV, video, videoText}, videoText);
                        }
                    } catch (Exception ex) {
                        utility.logger(ex.toString());
                        //utility.showToast(ex.toString());
                        utility.hideAndShowView(new View[]{videoRV, video, videoText}, videoText);
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

    private void getNews(int pageNumber){
        if(utility.isNetworkAvailable()) {
            Call<ContentResponse> call = apiInterface.getNews(context.getString(R.string.authorization_key), pageNumber, getResources().getInteger(R.integer.data_size));
            call.enqueue(new Callback<ContentResponse>() {
                @Override
                public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                    try {
                        if (response.isSuccessful() && response.code() == 200) {
                            newsResponse = response.body();
                            newsContents.addAll(newsResponse.getContents());
                            if (newsContents.size() > 0) {
                                utility.hideAndShowView(new View[]{newsRV, news, newsText}, newsRV);
                                newsAdapter = new PictureRadapter(context, newsContents, KeyWord.NEWS, menuInterface);
                                newsRV.setAdapter(newsAdapter);
                            } else {
                                utility.hideAndShowView(new View[]{newsRV, news, newsText}, newsText);
                            }
                        } else {
                            utility.logger(String.valueOf(response.code()));
                            //utility.showToast(String.valueOf(response.code()));
                            utility.hideAndShowView(new View[]{newsRV, news, newsText}, newsText);
                        }
                    } catch (Exception ex) {
                        utility.logger(ex.toString());
                        //utility.showToast(ex.toString());
                        utility.hideAndShowView(new View[]{newsRV, news, newsText}, newsText);
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
