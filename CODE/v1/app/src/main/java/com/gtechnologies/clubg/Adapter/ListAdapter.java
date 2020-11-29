package com.gtechnologies.clubg.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gtechnologies.clubg.Library.KeyWord;
import com.gtechnologies.clubg.Library.Utility;
import com.gtechnologies.clubg.Model.Content;
import com.gtechnologies.clubg.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Hp on 4/10/2018.
 */

public class ListAdapter extends BaseAdapter {

    Context context;
    List<Content> contentList;
    Utility utility;
    String keyword;
    long imageDownloadReference;
    long audioDownloadReference;
    long videoDownloadReference;

    public ListAdapter(Context context, List<Content> contents, String keyword){
        this.context = context;
        this.contentList = contents;
        utility = new Utility(this.context);
        this.keyword = keyword;
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public Object getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout, null);
        }
        LinearLayout suggesationLayout = (LinearLayout)convertView.findViewById(R.id.suggestion_layout);
        ImageView image = (ImageView)convertView.findViewById(R.id.image);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView description = (TextView)convertView.findViewById(R.id.description);
        Picasso.with(context)
                .load(context.getString(R.string.image_url)+"image/"+ contentList.get(position).getFilename()+"_thumb.jpeg")
                .placeholder(context.getResources().getDrawable(R.drawable.ic_photo))
                .error(context.getResources().getDrawable(R.drawable.broken_image))
                .into(image);
        title.setText(Html.fromHtml(contentList.get(position).getTitle()));
        description.setText(Html.fromHtml(contentList.get(position).getBrief()));
        suggesationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //suggesationInterface.refreshPlayer(position);
                if(keyword.equals(KeyWord.PICTURE)){
                    downloadFile(
                            Html.fromHtml(contentList.get(position).getTitle()).toString(),
                            Html.fromHtml(contentList.get(position).getBrief()).toString(),
                            contentList.get(position).getFilename(),
                            context.getString(R.string.image_url)+"image/"+ contentList.get(position).getFilename()+"_regular.jpeg",
                            KeyWord.PICTURE
                            );
                }
                else if(keyword.equals(KeyWord.SONG)){
                    downloadFile(
                            Html.fromHtml(contentList.get(position).getTitle()).toString(),
                            Html.fromHtml(contentList.get(position).getBrief()).toString(),
                            contentList.get(position).getFilename(),
                            context.getString(R.string.image_url)+"audio/"+ contentList.get(position).getFilename()+".mp3",
                            KeyWord.SONG
                    );
                }
                else if(keyword.equals(KeyWord.VIDEO)){
                    downloadFile(
                            Html.fromHtml(contentList.get(position).getTitle()).toString(),
                            Html.fromHtml(contentList.get(position).getBrief()).toString(),
                            contentList.get(position).getFilename(),
                            context.getString(R.string.image_url)+"video/"+ contentList.get(position).getFilename()+".mp4",
                            KeyWord.VIDEO
                    );
                }
                else if(keyword.equals(KeyWord.NEWS)){
                    showDialog(contentList.get(position));
                }
                else {
                    utility.showToast("Clicked " + Html.fromHtml(contentList.get(position).getTitle()));
                }
            }
        });
        return convertView;
    }

    private void showDialog(Content content){
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        HashMap<String, Integer> screen = utility.getScreenRes();
        int width = screen.get(KeyWord.SCREEN_WIDTH);
        int height = screen.get(KeyWord.SCREEN_HEIGHT);
        int mywidth = (width / 10) * 8;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.news_layout);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView brief = (TextView) dialog.findViewById(R.id.brief);
        ImageView imageFrame = (ImageView) dialog.findViewById(R.id.imageFrame);
        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        title.setText(Html.fromHtml(content.getTitle()));
        brief.setText(Html.fromHtml(content.getBrief()));
        Picasso.with(context)
                .load(context.getString(R.string.image_url)+"image/"+ content.getFilename()+"_news.jpeg")
                .placeholder(context.getResources().getDrawable(R.drawable.ic_photo))
                .error(context.getResources().getDrawable(R.drawable.broken_image))
                .into(imageFrame);
        RelativeLayout ll = (RelativeLayout) dialog.findViewById(R.id.news_page);
        ViewGroup.LayoutParams params = ll.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = mywidth;
        ll.setLayoutParams(params);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void downloadFile(String title, String brief, String filename, String url, String keyword){
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);

        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle(title);

        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription(brief);

        //Set the local destination for the downloaded file to a path within the application's external files directory
        switch (keyword){
            case "Picture":
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename+".jpeg");
                imageDownloadReference = downloadManager.enqueue(request);
                break;
            case "Song":
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename+".mp3");
                audioDownloadReference = downloadManager.enqueue(request);
                break;
            case "Video":
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename+".mp4");
                videoDownloadReference = downloadManager.enqueue(request);
                break;
        }
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadReceiver, filter);
    }


    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Toast toast;
            Intent i = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "10101")
                    .setSmallIcon(R.drawable.ic_photo)
                    .setContentText("Download Completed")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            if(referenceId==imageDownloadReference) {
                toast = Toast.makeText(context,
                        "Image Download Complete", Toast.LENGTH_LONG);
                mBuilder.setContentTitle("Image");
            }
            else if(referenceId == audioDownloadReference){
                toast = Toast.makeText(context,
                        "Audio Download Complete", Toast.LENGTH_LONG);
                mBuilder.setContentTitle("Audio");
            }
            else if(referenceId == videoDownloadReference){
                toast = Toast.makeText(context,
                        "Video Download Complete", Toast.LENGTH_LONG);
                mBuilder.setContentTitle("Video");
            }
            else {
                toast = Toast.makeText(context,
                        "Download Complete", Toast.LENGTH_LONG);
                mBuilder.setContentTitle("File");
            }
            toast.show();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(10101, mBuilder.build());
        }
    };
}
