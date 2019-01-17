package com.gaadi.neon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.gaadi.neon.activity.ImageReviewActivity;
import com.gaadi.neon.dynamicgrid.DynamicGridView;
import com.gaadi.neon.enumerations.ResponseCode;
import com.gaadi.neon.adapter.ImageShowAdapter;
import com.gaadi.neon.interfaces.INeutralParam;
import com.gaadi.neon.model.ImageTagModel;
import com.gaadi.neon.util.Constants;
import com.gaadi.neon.util.FileInfo;
import com.gaadi.neon.util.NeonImagesHandler;
import com.scanlibrary.R;

import java.util.List;

/**
 * @author princebatra
 * @version 1.0
 * @since 2/2/17
 */
public class ImageShowFragment extends Fragment {

    ImageShowAdapter adapter;
    private boolean isProfileTagOnly;
    private DynamicGridView imageShowGrid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        INeutralParam iNeutralParam = NeonImagesHandler.getSingletonInstance().getNeutralParam();
        if (iNeutralParam != null) {
            isProfileTagOnly = iNeutralParam.hasOnlyProfileTag();
        }
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.image_show_layout, container, false);
        rootView.findViewById(R.id.btnDone).setOnClickListener(doneListener);
        imageShowGrid = rootView.findViewById(R.id.image_show_grid);
        imageShowGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (NeonImagesHandler.getSingletonInstance() != null &&
                        NeonImagesHandler.getSingletonInstance().getGenericParam() != null &&
                        NeonImagesHandler.getSingletonInstance().getGenericParam().getImageTagsModel() != null &&
                        NeonImagesHandler.getSingletonInstance().getGenericParam().getImageTagsModel().size() > 0)
                {
                    Intent viewPagerIntent = new Intent(getActivity(), ImageReviewActivity.class);
                    viewPagerIntent.putExtra(Constants.IMAGE_REVIEW_POSITION, position);
                    getActivity().startActivity(viewPagerIntent);
                }

            }
        });
        imageShowGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                imageShowGrid.startEditMode(position);
                return true;
            }
        });


        imageShowGrid.setOnDropListener(new DynamicGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                imageShowGrid.stopEditMode();
            }
        });
        imageShowGrid.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                if (NeonImagesHandler.getSingletonInstance().getImagesCollection() == null ||
                        NeonImagesHandler.getSingletonInstance().getImagesCollection().size() <= 0) {
                    return;
                }
                NeonImagesHandler.getSingletonInstance().getImagesCollection().add(newPosition,
                        NeonImagesHandler.getSingletonInstance().getImagesCollection().remove(oldPosition));

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NeonImagesHandler.getSingletonInstance().getImagesCollection() == null ||
                NeonImagesHandler.getSingletonInstance().getImagesCollection().size() < 0) {
            return;
        }
        adapter = new ImageShowAdapter(getActivity(), isProfileTagOnly);
        imageShowGrid.setAdapter(adapter);
    }

    View.OnClickListener doneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (NeonImagesHandler.getSingletonInstance().validateNeonExit(getActivity())) {
                NeonImagesHandler.getSingletonInstance().sendImageCollectionAndFinish(getActivity(), ResponseCode.Success);
            }
        }
    };
}
