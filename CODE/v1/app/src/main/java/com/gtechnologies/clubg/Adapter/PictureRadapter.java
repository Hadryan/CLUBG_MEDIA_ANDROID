package com.gtechnologies.clubg.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtechnologies.clubg.Fragment.Audio;
import com.gtechnologies.clubg.Fragment.News;
import com.gtechnologies.clubg.Fragment.Picture;
import com.gtechnologies.clubg.Fragment.Video;
import com.gtechnologies.clubg.Interface.MenuInterface;
import com.gtechnologies.clubg.Library.KeyWord;
import com.gtechnologies.clubg.Library.Utility;
import com.gtechnologies.clubg.Model.Content;
import com.gtechnologies.clubg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hp on 12/28/2017.
 */

public class PictureRadapter extends RecyclerView.Adapter<PictureRadapter.ViewHolder> {

    List<Content> contents;
    Context context;
    View view;
    Utility utility;
    String keyword;
    MenuInterface menuInterface;

    public PictureRadapter(Context context, List<Content> contents, String keyword, MenuInterface menuInterface){
        this.contents = contents;
        this.context = context;
        utility = new Utility(this.context);
        this.keyword = keyword;
        this.menuInterface = menuInterface;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        ImageView image;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_album_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(Html.fromHtml(contents.get(position).getTitle()));
        Picasso.with(holder.itemView.getContext())
                .load(context.getString(R.string.image_url)+"image/"+contents.get(position).getFilename()+"_thumb.jpeg")
                .placeholder(context.getResources().getDrawable(R.drawable.ic_photo))
                .error(context.getResources().getDrawable(R.drawable.broken_image))
                .into(holder.image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    switch (keyword){
                        case "Picture":
                            menuInterface.setMenuChecked(keyword);
                            Picture picture = new Picture(context);
                            fragmentTransaction.replace(R.id.containerView, picture);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case "Song":
                            menuInterface.setMenuChecked(keyword);
                            Audio audio = new Audio(context);
                            fragmentTransaction.replace(R.id.containerView, audio);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case "Video":
                            menuInterface.setMenuChecked(keyword);
                            Video video = new Video(context);
                            fragmentTransaction.replace(R.id.containerView, video);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case "News":
                            menuInterface.setMenuChecked(keyword);
                            News news = new News(context);
                            fragmentTransaction.replace(R.id.containerView, news);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        default:
                            menuInterface.setMenuChecked("Home");
                            utility.logger("Error");
                    }
                }
                catch (Exception ex){
                    utility.logger(ex.toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

}
