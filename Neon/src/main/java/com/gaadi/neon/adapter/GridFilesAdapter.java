package com.gaadi.neon.adapter;

import android.content.Context;
import android.support.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gaadi.neon.activity.gallery.GridFilesActivity;
import com.gaadi.neon.model.ImageInfo;
import com.gaadi.neon.util.Constants;
import com.gaadi.neon.util.ExifInterfaceHandling;
import com.gaadi.neon.util.FileInfo;
import com.gaadi.neon.util.NeonImagesHandler;
import com.google.gson.Gson;
import com.scanlibrary.R;

import java.io.File;
import java.util.ArrayList;


/**
 * @author princebatra
 * @version 1.0
 * @since 2/2/17
 */
public class GridFilesAdapter extends BaseAdapter {

    private AppCompatActivity context;
    private ArrayList<FileInfo> fileInfos;

    public GridFilesAdapter(AppCompatActivity _context, ArrayList<FileInfo> _fileInfos) {
        context = _context;
        fileInfos = _fileInfos;
    }

    @Override
    public int getCount() {
        return fileInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FilesHolder filesHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_files, null);

            filesHolder = new FilesHolder();
            filesHolder.selectedImage = (ImageView) convertView.findViewById(R.id.imageSelected);
            filesHolder.selection_view = (ImageView) convertView.findViewById(R.id.selection_view);
            filesHolder.transparentLayer = (ImageView) convertView.findViewById(R.id.vTransparentLayer);
            convertView.setTag(filesHolder);
        } else {
            Log.e("tag", "came");
        }
        filesHolder = (FilesHolder) convertView.getTag();

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop();
        Glide.with(context).load(fileInfos.get(position).getFilePath())
                .apply(options)
                .into(filesHolder.selectedImage);
        /*Glide.with(context).load(fileInfos.get(position).getFilePath())
                .placeholder(R.drawable.default_placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(filesHolder.selectedImage);*/

        if (NeonImagesHandler.getSingleonInstance().checkImageAvailableForPath(fileInfos.get(position))) {
            filesHolder.selection_view.setVisibility(View.VISIBLE);
            filesHolder.transparentLayer.setVisibility(View.VISIBLE);
        } else {
            filesHolder.selection_view.setVisibility(View.GONE);
            filesHolder.transparentLayer.setVisibility(View.GONE);
        }
        final FilesHolder finalFilesHolder = filesHolder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NeonImagesHandler.getSingletonInstance().checkImageAvailableForPath(fileInfos.get(position))) {
                    if (NeonImagesHandler.getSingletonInstance().removeFromCollection(fileInfos.get(position))) {
                        finalFilesHolder.selection_view.setVisibility(View.GONE);
                        finalFilesHolder.transparentLayer.setVisibility(View.GONE);
                        ((GridFilesActivity) context).removeImageFromRecentCollection(fileInfos.get(position));
                    }
                } else {
                    if (NeonImagesHandler.getSingletonInstance().getGenericParam() != null && NeonImagesHandler.getSingletonInstance().getGenericParam().getCustomParameters() != null && NeonImagesHandler.getSingletonInstance().getGenericParam().getCustomParameters().getVccIdAvailable() != null) {
                        String appName = Constants.getAppName(context);
                        File file = new File(fileInfos.get(position).getFilePath());
                        if (file.exists()) {
                            try {
                                ExifInterfaceHandling exifInterfaceHandling = new ExifInterfaceHandling(file);
                                if (exifInterfaceHandling.getAttribute(ExifInterface.TAG_USER_COMMENT) != null) {
                                    Gson gson = new Gson();
                                    ImageInfo imageInfo = gson.fromJson(exifInterfaceHandling.getAttribute(ExifInterface.TAG_USER_COMMENT), ImageInfo.class);
                                    String artist = exifInterfaceHandling.getAttribute(ExifInterfaceHandling.TAG_ARTIST);
                                    if (artist != null && (String.valueOf(appName)).equals(artist) && imageInfo.getVccId().equals(NeonImagesHandler.getSingletonInstance().getGenericParam().getCustomParameters().getVccIdAvailable())) {
                                        if (NeonImagesHandler.getSingletonInstance().putInImageCollection(fileInfos.get(position), context)) {
                                            finalFilesHolder.selection_view.setVisibility(View.VISIBLE);
                                            finalFilesHolder.transparentLayer.setVisibility(View.VISIBLE);
                                            ((GridFilesActivity) context).addImageToRecentelySelected(fileInfos.get(position));
                                        }
                                    } else {
                                        Toast.makeText(context, "Not allowed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "Not allowed", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (NeonImagesHandler.getSingletonInstance().getGenericParam() != null && NeonImagesHandler.getSingletonInstance().getGenericParam().getCustomParameters() != null && NeonImagesHandler.getSingletonInstance().getGenericParam().getCustomParameters().getFolderRestrictive()) {
                        String appName = Constants.getAppName(context);
                        File file = new File(fileInfos.get(position).getFilePath());
                        if (file.exists()) {
                            try {
                                ExifInterfaceHandling exifInterfaceHandling = new ExifInterfaceHandling(file);
                                String artist = exifInterfaceHandling.getAttribute(ExifInterfaceHandling.TAG_ARTIST);
                                if (artist != null && (String.valueOf(appName)).equals(artist)) {
                                    if (NeonImagesHandler.getSingletonInstance().putInImageCollection(fileInfos.get(position), context)) {
                                        finalFilesHolder.selection_view.setVisibility(View.VISIBLE);
                                        finalFilesHolder.transparentLayer.setVisibility(View.VISIBLE);
                                        ((GridFilesActivity) context).addImageToRecentelySelected(fileInfos.get(position));
                                    }
                                } else {
                                    Toast.makeText(context, "Not allowed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        if (NeonImagesHandler.getSingletonInstance().putInImageCollection(fileInfos.get(position), context)) {
                            finalFilesHolder.selection_view.setVisibility(View.VISIBLE);
                            finalFilesHolder.transparentLayer.setVisibility(View.VISIBLE);
                            ((GridFilesActivity) context).addImageToRecentelySelected(fileInfos.get(position));
                        }
                    }
                }
            }
        });
        return convertView;
    }

    private class FilesHolder {
        ImageView selectedImage;
        ImageView transparentLayer;
        ImageView selection_view;
    }
}
