package com.gaadi.neon.activity.finance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gaadi.neon.PhotosLibrary;
import com.gaadi.neon.adapter.FlashModeAdapter;
import com.gaadi.neon.enumerations.CameraFacing;
import com.gaadi.neon.enumerations.CameraOrientation;
import com.gaadi.neon.enumerations.CameraType;
import com.gaadi.neon.enumerations.GalleryType;
import com.gaadi.neon.enumerations.LibraryMode;
import com.gaadi.neon.enumerations.ResponseCode;
import com.gaadi.neon.fragment.CameraFragment1;
import com.gaadi.neon.interfaces.ICameraParam;
import com.gaadi.neon.interfaces.IGalleryParam;
import com.gaadi.neon.interfaces.OnImageCollectionListener;
import com.gaadi.neon.model.ImageTagModel;
import com.gaadi.neon.model.NeonResponse;
import com.gaadi.neon.model.PhotosMode;
import com.gaadi.neon.util.Constants;
import com.gaadi.neon.util.CustomParameters;
import com.gaadi.neon.util.FileInfo;
import com.gaadi.neon.util.GetFilePath;
import com.gaadi.neon.util.NeonImagesHandler;
import com.gaadi.neon.util.NeonUtils;
import com.gaadi.neon.util.OneStepImageHandler;
import com.intsig.csopen.sdk.CSOpenAPI;
import com.intsig.csopen.sdk.CSOpenApiFactory;
import com.intsig.csopen.sdk.CSOpenApiHandler;
import com.scanlibrary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class OneStepActivity extends AppCompatActivity implements CameraFragment1.SetOnPictureTaken, OnImageCollectionListener {
    private final int REQ_CODE_CALL_CAMSCANNER = 168;
    //private CameraFragment1 camFragment;
    private ImageView ivGallery, ivClickPicture, ivFlash, ivPdf;
    private RecyclerView rvFlash;
    private List<String> listOfFlashModes = new ArrayList<>();
    private HashSet<String> mSelectedImages = new HashSet<>();
    private int count;
    private TextView tvImageCount, tvNext;
    private ICameraParam cameraParam;
    private IGalleryParam galleryParam;
    private int PICKFILE_RESULT_CODE = 119;
    //File descriptor of the PDF.
    private ParcelFileDescriptor mFileDescriptor;
    //to render the PDF.
    private PdfRenderer mPdfRenderer;
    //Page that is currently converted.
    private PdfRenderer.Page mCurrentPage;
    private String path;
    private String docCat;
    private String docSubCat;
    private CSOpenAPI camScannerApi;
    private String camScannerApiKey;
    private String mOutputImagePath;
    private String mInputImagePath;

    public OneStepActivity() {
    }

    private CameraFragment1 getCameraFragmentInstance() {
        return (CameraFragment1) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_one_step_upload);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        ivPdf = (ImageView) findViewById(R.id.ivPdf);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            ActivityCompat.requestPermissions(OneStepActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ivPdf.setVisibility(View.INVISIBLE);
        }

        extractData();

        setUpCamera();

        createFlashModes();
        if (camScannerApiKey != null && !camScannerApiKey.equals("")) {
            camScannerApi = CSOpenApiFactory.createCSOpenApi(this, camScannerApiKey, null);
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                CameraFragment1 fragment = CameraFragment1.getInstance(false);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            }
        });
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        rvFlash = (RecyclerView) findViewById(R.id.rvFlash);
        tvImageCount = (TextView) findViewById(R.id.tvImageCount);
        tvNext = (TextView) findViewById(R.id.tvNext);
        tvNext.setVisibility(View.GONE);
        ivPdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });


        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResponse();
            }
        });

        TextView tvDocName = (TextView) findViewById(R.id.tvDocType);
        tvDocName.setText(docCat);
        TextView tvSubDocName = (TextView) findViewById(R.id.tvSubDocType);
        tvSubDocName.setText(docSubCat);
        ImageView ivBackBtn = (ImageView) findViewById(R.id.ivBackBtn);
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();
            }
        });
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(OneStepActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvFlash.setLayoutManager(layoutManager);
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        ivClickPicture = (ImageView) findViewById(R.id.ivClick);
        ivClickPicture.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                tvNext.setClickable(false);
                ivClickPicture.setClickable(false);
                getCameraFragmentInstance().clickPicture();

            }
        });

        ivFlash = (ImageView) findViewById(R.id.ivFlash);
        ivFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvFlash.getVisibility() == View.GONE) {
                    FlashModeAdapter flashModeAdapter = new FlashModeAdapter(OneStepActivity.this, listOfFlashModes);
                    rvFlash.setAdapter(flashModeAdapter);
                    rvFlash.setVisibility(View.VISIBLE);
                    flashModeAdapter.setOnItemClickListener(new FlashModeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            rvFlash.setVisibility(View.GONE);
                            switch (position) {
                                case 0:
                                    ivFlash.setBackgroundResource(R.drawable.flash_off_circle);
                                    getCameraFragmentInstance().setFlash("off");
                                    break;
                                case 1:
                                    ivFlash.setBackgroundResource(R.drawable.flash_circle);
                                    getCameraFragmentInstance().setFlash("on");
                                    break;
                                case 2:
                                    ivFlash.setBackgroundResource(R.drawable.flash_auto_circle);
                                    getCameraFragmentInstance().setFlash("auto");
                                    break;
                            }
                        }
                    });
                } else {
                    rvFlash.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull
                                                   String permissions[],
                                           @NonNull
                                                   int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLatestPictureInGallery();
                } else {
                    //issue in react projects
                    /*Toast.makeText(OneStepActivity.this, "Permission Error", Toast.LENGTH_SHORT)
                            .show();
                    finish();*/
                }
            }
        }
    }

    private void setupLatestPictureInGallery() {
        String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
        String CAMERA_IMAGE_BUCKET_ID = String.valueOf(CAMERA_IMAGE_BUCKET_NAME.toLowerCase().hashCode());

        final String[] projection = {MediaStore.Images.Media.DATA};
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};
        final Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs,
                MediaStore.Images.Media.DATE_TAKEN + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String data = cursor.getString(dataColumn);
            cursor.close();

            RequestOptions options = new RequestOptions()
                    .centerCrop();
            Glide.with(this).asBitmap()
                    .load("file://" + data)
                    .apply(options)
                    .into(new BitmapImageViewTarget(ivGallery) {

                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ivGallery.setImageDrawable(circularBitmapDrawable);
                        }
                    });
            /*Glide.with(OneStepActivity.this)
                    .load("file://" + data)
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(ivGallery) {

                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ivGallery.setImageDrawable(circularBitmapDrawable);
                        }
                    });*/
        }
    }

    private void createFlashModes() {
        listOfFlashModes.add("off");
        listOfFlashModes.add("on");
        listOfFlashModes.add("auto");
    }

    private void extractData() {
        docCat = getIntent().getStringExtra(Constants.CATEGORY);
        docSubCat = getIntent().getStringExtra(Constants.SUB_CATEGORY);
        camScannerApiKey = getIntent().getStringExtra(Constants.CAM_SCANNER_API_KEY);
        Log.d("Rajeev", "extractData: " + camScannerApiKey);
    }

    private void openGallery() {
        try {
            NeonImagesHandler.getSingletonInstance().scheduleSingletonClearance();
            galleryParam = NeonImagesHandler.getSingletonInstance().getGalleryParam();
            if (galleryParam == null) {
                galleryParam = new IGalleryParam() {
                    @Override
                    public boolean selectVideos() {
                        return false;
                    }

                    @Override
                    public CustomParameters getCustomParameters() {
                        CustomParameters.CustomParametersBuilder builder = new CustomParameters.CustomParametersBuilder();
                        builder.setLocationRestrictive(false);
                        return builder.build();
                    }

                    @Override
                    public GalleryType getGalleryViewType() {
                        return GalleryType.Grid_Structure;
                    }

                    @Override
                    public boolean enableFolderStructure() {
                        return false;
                    }

                    @Override
                    public boolean galleryToCameraSwitchEnabled() {
                        return false;
                    }

                    @Override
                    public boolean isRestrictedExtensionJpgPngEnabled() {
                        return true;
                    }

                    @Override
                    public int getNumberOfPhotos() {
                        return 0;
                    }

                    @Override
                    public boolean getTagEnabled() {
                        return false;
                    }

                    @Override
                    public List<ImageTagModel> getImageTagsModel() {
                        return null;
                    }

                    @Override
                    public List<FileInfo> getAlreadyAddedImages() {
                        List<FileInfo> selectedFileList = new ArrayList<>();
                        if (mSelectedImages != null) {
                            if (mSelectedImages.size() > 0) {
                                for (String s : mSelectedImages) {
                                    FileInfo fileInfo = new FileInfo();
                                    fileInfo.setFilePath(s);
                                    selectedFileList.add(fileInfo);
                                }
                                return selectedFileList;
                            }
                        }
                        return null;
                    }

                    @Override
                    public boolean enableImageEditing() {
                        return false;
                    }
                };
            }

            PhotosLibrary.collectPhotos(0, this, LibraryMode.Relax, PhotosMode.setGalleryMode().setParams(galleryParam), new OnImageCollectionListener() {
                @Override
                public void imageCollection(NeonResponse neonResponse) {
                    List<FileInfo> imageCollection = neonResponse.getImageCollection();
                    if (imageCollection != null && imageCollection.size() > 0 && neonResponse.getResponseCode() == ResponseCode.Success) {
                        mSelectedImages.clear();
                        for (FileInfo file : imageCollection) {
                            mSelectedImages.add(file.getFilePath());
                        }
                        count = mSelectedImages.size();
                        if (count > 0) {
                            tvNext.setVisibility(View.VISIBLE);
                            tvImageCount.setVisibility(View.VISIBLE);
                            tvImageCount.setText(String.valueOf(count));

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/
        if (NeonImagesHandler.getSingletonInstance().getCameraParam() == null) {
            NeonImagesHandler.getSingletonInstance().setCameraParam(cameraParam);
        }

        if (NeonImagesHandler.getSingletonInstance().getGalleryParam() == null) {
            NeonImagesHandler.getSingletonInstance().setGalleryParam(galleryParam);
        }

    }

    private void setUpCamera() {
        try {
            cameraParam = new ICameraParam() {
                @Override
                public CameraFacing getCameraFacing() {
                    return CameraFacing.back;
                }

                @Override
                public CustomParameters getCustomParameters() {
                    CustomParameters.CustomParametersBuilder builder = new CustomParameters.CustomParametersBuilder();
                    builder.setLocationRestrictive(false);
                    return builder.build();
                }

                @Override
                public CameraOrientation getCameraOrientation() {
                    return CameraOrientation.portrait;
                }

                @Override
                public boolean getFlashEnabled() {
                    return false;
                }

                @Override
                public boolean getCameraSwitchingEnabled() {
                    return false;
                }

                @Override
                public boolean getVideoCaptureEnabled() {
                    return false;
                }

                @Override
                public CameraType getCameraViewType() {
                    return CameraType.normal_camera;
                }

                @Override
                public boolean cameraToGallerySwitchEnabled() {
                    return false;
                }

                @Override
                public int getNumberOfPhotos() {
                    return 0;
                }

                @Override
                public boolean getTagEnabled() {
                    return false;
                }

                @Override
                public List<ImageTagModel> getImageTagsModel() {
                    return null;
                }

                @Override
                public List<FileInfo> getAlreadyAddedImages() {
                    return null;
                }

                @Override
                public boolean enableImageEditing() {
                    return false;
                }
            };
            NeonImagesHandler.getSingletonInstance().setCameraParam(cameraParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPictureTaken(String filePath) {
        ivClickPicture.setClickable(true);
        mInputImagePath = filePath;
        if (camScannerApi != null && camScannerApi.isCamScannerInstalled()) {
            String appName = getResources().getString(R.string.app_name).replace(" ", "");
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + appName;
            checkDir(path);
            mOutputImagePath = path + File.separator + "IMG_" + System.currentTimeMillis() + "_scanned.jpg";
            boolean req = PhotosLibrary.go2CamScanner(this, filePath, mOutputImagePath, REQ_CODE_CALL_CAMSCANNER, camScannerApi);
            Log.d("Rajeev", "onPictureTaken: " + req);
            if (!req)
                afterPictureTaken(filePath);
        } else {
            afterPictureTaken(filePath);
        }
    }

    private void afterPictureTaken(String filePath) {
        count++;
        tvImageCount.setVisibility(View.VISIBLE);
        tvImageCount.setText(String.valueOf(count));
        tvNext.setVisibility(View.VISIBLE);
        tvNext.setClickable(true);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilePath(filePath);
        fileInfo.setFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
        fileInfo.setSource(FileInfo.SOURCE.PHONE_CAMERA);
        if (cameraParam == null) {
            return;
        }
        NeonImagesHandler.getSingletonInstance().setCameraParam(cameraParam);
        NeonImagesHandler.getSingletonInstance().putInImageCollection(fileInfo, this);
        RequestOptions options = new RequestOptions().centerCrop();
        Glide.with(OneStepActivity.this).asBitmap().load("file://" + fileInfo.getFilePath())
                .apply(options)
                .into(new BitmapImageViewTarget(ivGallery) {

                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivGallery.setImageDrawable(circularBitmapDrawable);
                    }
                });
        /*Glide.with(OneStepActivity.this)
                .load("file://" + fileInfo.getFilePath())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(ivGallery) {

                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivGallery.setImageDrawable(circularBitmapDrawable);
                    }
                });*/

        mSelectedImages.add(filePath);
    }

    private boolean checkDir(String path) {
        boolean result = true;
        File f = new File(path);
        if (!f.exists()) {
            result = f.mkdirs();
        } else if (f.isFile()) {
            f.delete();
            result = f.mkdirs();
        }
        return result;
    }

    @Override
    public void onBackPressed() {

        if (mSelectedImages.size() > 0) {
            new AlertDialog.Builder(OneStepActivity.this).setTitle(R.string.please_confirm)
                    .setMessage(R.string.would_you_like_to_save_captured_images)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendResponse();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.gcloud.gaadi.service.FinanceImagesUploadService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICKFILE_RESULT_CODE) {
            if (data == null && data.getData() == null) {
                Toast.makeText(this, "Error!!!",
                        Toast.LENGTH_SHORT).show();
            } else {
                String myData = "";
                Uri pdfFile = data.getData();
                path = GetFilePath.getPath(getApplicationContext(), pdfFile);
                try {
                    openRenderer(this);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error!!!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == REQ_CODE_CALL_CAMSCANNER) {
            camScannerApi.handleResult(requestCode, resultCode, data, new CSOpenApiHandler() {
                @Override
                public void onSuccess() {
                    Log.d("Rajeev", "onSuccess: " + mOutputImagePath);
                    //File file = new File(mOutputImagePath);
                    //Log.d("Rajeev", "onSuccess: " + Uri.fromFile(file));
                    //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                    NeonUtils.scanFile(OneStepActivity.this, mInputImagePath);
                    afterPictureTaken(mOutputImagePath);
                    NeonUtils.deleteFile(OneStepActivity.this, mInputImagePath);
                }

                @Override
                public void onError(int i) {
                    Log.d("Rajeev", "onError: " + i);
                }

                @Override
                public void onCancel() {
                    Log.d("Rajeev", "onCancel: ");
                }
            });
        }
    }


    @Override
    public void imageCollection(NeonResponse neonResponse) {

    }

    //pdf conversion to images section starts here


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showFileChooser() {
        if (mSelectedImages.size() != 0) {
            showImageLostAlertDialog();
        } else {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("application/pdf");
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(chooseFile, "Choose a PDF file"), PICKFILE_RESULT_CODE);
        }
    }

    private void showImageLostAlertDialog() {
        new AlertDialog.Builder(OneStepActivity.this).setTitle(R.string.please_confirm)
                .setCancelable(true)
                .setMessage("Captured images will be replace. \n Would you like to proceed?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectedImages.clear();
                        count = 0;
                        tvImageCount.setVisibility(View.INVISIBLE);
                        tvNext.setVisibility(View.INVISIBLE);
                        showFileChooser();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        }).create().show();
    }


    /**
     * Sets up a {@link PdfRenderer} and related resources.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void openRenderer(Context context) throws IOException {
        if(path == null){
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfRenderer we use to render the PDF.
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
            for (int i = 0; i < mPdfRenderer.getPageCount(); i++) {
                convertIntoImage(i);
            }
            if (mSelectedImages.size() != 0) {
                showPdfUploadDialog();
            } else {
                Toast.makeText(this, "Can't read the pdf file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Closes the {@link PdfRenderer} and related resources.
     *
     * @throws IOException When the PDF file cannot be closed.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void closeRenderer() throws IOException {
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        if (mPdfRenderer != null)
            mPdfRenderer.close();
        if (mFileDescriptor != null)
            mFileDescriptor.close();
    }

    //Convert the specified page of PDF to image.
    @SuppressLint("InlinedApi")
    private void convertIntoImage(int index) throws IOException {
        if (mPdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != mCurrentPage) {
            try {
                mCurrentPage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // Use `openPage` to open a specific page in PDF.
        mCurrentPage = mPdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        saveImage(bitmap, index);
    }

    /**
     * Gets the number of pages in the PDF. This method is marked as public for testing.
     *
     * @return The number of pages.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int getPageCount() {
        return mPdfRenderer.getPageCount();
    }

    private void saveImage(Bitmap bitmap, int index) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/downloads");
        myDir.mkdirs();
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imageFile = new File(myDir, "IMG_" + timeStamp + "_" + index + ".png");
        mSelectedImages.add(imageFile.getPath());
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPdfUploadDialog() {
        new AlertDialog.Builder(OneStepActivity.this).setTitle(R.string.please_confirm)
                .setCancelable(false)
                .setMessage(R.string.would_you_like_to_save_captured_pdf)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResponse();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        }).create().show();
    }

    @Override
    protected void onDestroy() {
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void sendResponse() {
        List<String> images = new ArrayList<>(mSelectedImages);
        List<FileInfo> imageCollection = new ArrayList<>();
        for (String path : images) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFilePath(path);
            imageCollection.add(fileInfo);
        }
        NeonResponse response = new NeonResponse();
        response.setImageCollection(imageCollection);
        OneStepImageHandler.getInstance().getOneStepImagesActionListener().imageCollection(response);
        finish();
    }
}
