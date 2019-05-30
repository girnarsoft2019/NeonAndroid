package com.gaadi.neon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gaadi.neon.activity.neutral.NeonNeutralActivity;
import com.gaadi.neon.dynamicgrid.BaseDynamicGridAdapter;
import com.gaadi.neon.util.FileInfo;
import com.gaadi.neon.util.NeonImagesHandler;
import com.scanlibrary.R;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author princebatra
 * @version 1.0
 * @since 2/2/17
 */
public class ImageShowAdapter extends BaseDynamicGridAdapter {

    private Context context;
    private boolean isProfileTagOnly;

    public ImageShowAdapter(Context context, boolean isProfileTagOnly) {
        super(context, NeonImagesHandler.getSingletonInstance().getImagesCollection(), 2);
        this.context = context;
        this.isProfileTagOnly = isProfileTagOnly;
    }

    @Override
    public int getCount() {
        if (NeonImagesHandler.getSingletonInstance().getImagesCollection() != null) {
            return NeonImagesHandler.getSingletonInstance().getImagesCollection().size();
        }
        return 0;
    }

   /* @Override
    public int getCount() {
        if(NeonImagesHandler.getSingleonInstance().getImagesCollection() != null) {
            return NeonImagesHandler.getSingleonInstance().getImagesCollection().size();
        }
        return 0;
    }*/

   /* @Override
    public Object getItem(int position) {
        return NeonImagesHandler.getSingleonInstance().getImagesCollection().get(position);
    }*/


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PhotosHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.display_images, null);
            holder = new PhotosHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.ivImageDisplay);
            holder.removeImage = (ImageView) convertView.findViewById(R.id.ivRemoveImage);
            holder.tvProfile = (TextView) convertView.findViewById(R.id.tvProfilePhoto);
            convertView.setTag(holder);
        } else {
            holder = (PhotosHolder) convertView.getTag();
        }
        List<FileInfo> fileInfoList = NeonImagesHandler.getSingleonInstance().getImagesCollection();
        if(fileInfoList != null && fileInfoList.size() > 0){
            if (isProfileTagOnly) {
                if (position > 0) {
                    holder.tvProfile.setVisibility(View.GONE);
                } else {
                    holder.tvProfile.setVisibility(View.VISIBLE);
                    String tagName = NeonImagesHandler.getSingletonInstance().getNeutralParam().getProfileTagName();
                    holder.tvProfile.setText(tagName);

                }
            } else {
                if (NeonImagesHandler.getSingletonInstance().getGenericParam() != null && !NeonImagesHandler.getSingletonInstance().getGenericParam().getTagEnabled()) {
                    holder.tvProfile.setVisibility(View.GONE);
                } else {
                    holder.tvProfile.setVisibility(View.VISIBLE);
                }

                if (fileInfoList.get(position).getFileTag() != null) {
                    holder.tvProfile.setText(fileInfoList.get(position).getFileTag().getTagName());
                } else {
                    holder.tvProfile.setText(R.string.select_tag);
                }
            }
            holder.removeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NeonImagesHandler.getSingleonInstance().removeFromCollection(position)) {
                        notifyDataSetChanged();
                        if (context instanceof NeonNeutralActivity) {
                            ((NeonNeutralActivity) context).onPostResume();
                        }
                      /*  if ((NeonImagesHandler.getSingleonInstance().getImagesCollection() == null ||
                                NeonImagesHandler.getSingleonInstance().getImagesCollection().size() <= 0) &&
                                context instanceof NeonNeutralActivity) {
                            ((NeonNeutralActivity) context).onPostResume();
                        }*/
                    } else {
                        Toast.makeText(context, "Failed to delete.Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

       /* convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPagerIntent = new Intent(context,ImageReviewActivity.class);
                viewPagerIntent.putExtra(Constants.IMAGE_REVIEW_POSITION,position);
                context.startActivity(viewPagerIntent);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context,"Long Pressed",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
*/
            if (NeonImagesHandler.getSingleonInstance().getImagesCollection().size() > 0) {
                RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.drawable.default_placeholder);
                Glide.with(context).load(NeonImagesHandler.getSingleonInstance().getImagesCollection().get(position).getFilePath())
                        .apply(options)
                        .transition(withCrossFade())
                        .into(holder.image);
                /*Glide.with(context).load(NeonImagesHandler.getSingleonInstance().getImagesCollection().get(position).getFilePath())
                        .crossFade()
                        .placeholder(R.drawable.default_placeholder)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.image);*/
            }
        }

        return convertView;
    }

    private class PhotosHolder {
        ImageView image;
        ImageView removeImage;
        TextView tvProfile;
    }
}
