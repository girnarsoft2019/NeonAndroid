package com.gaadi.neon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gaadi.neon.util.Constants;
import com.gaadi.neon.util.NeonUtils;
import com.scanlibrary.R;

public class SingleImageReviewActivity extends NeonBaseActivity {
    private ImageView ivReview;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_review);
        toolbar = findViewById(R.id.tool_bar);
        ivReview = findViewById(R.id.iv_review);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.image_review);
        Intent intent = getIntent();
        path = intent.getStringExtra(Constants.SINGLE_IMAGE_PATH);
        String title = intent.getStringExtra(Constants.REVIEW_TITLE);
        if (title != null && !TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        if (path == null || TextUtils.isEmpty(path)) {
            finish();
        }
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.default_placeholder);
        Glide.with(this).load(path)
                .apply(options)
                .into(ivReview);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
        menu.findItem(R.id.menu_done).setVisible(false);
        menu.findItem(R.id.menu_retry).setVisible(true);
        menu.findItem(R.id.menu_apply).setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        NeonUtils.deleteFile(SingleImageReviewActivity.this, path);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        if (item.getItemId() == R.id.menu_apply) {
            setResult(RESULT_OK, new Intent().putExtra(Constants.SINGLE_IMAGE_PATH, path));
            finish();
            return true;
        }
        if (item.getItemId() == R.id.menu_retry) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
