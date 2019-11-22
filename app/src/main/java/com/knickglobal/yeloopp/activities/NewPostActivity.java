package com.knickglobal.yeloopp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.BackgroundsAdapter;
import com.knickglobal.yeloopp.adapters.SelectedImagesAdapter;
import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.models.FriendsListPojo;
import com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi.GetFriendsPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi.GetFriendsPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi.GetFriendsView;
import com.knickglobal.yeloopp.mvpArchitecture.postApi.PostPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.postApi.PostPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.postApi.PostView;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.CustomProgressDialog;
import com.knickglobal.yeloopp.utils.StopWords;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener, GetFriendsView, PostView {

//    private static final String TAG = "Beant NewPostActivity";

    public static final int REQUEST_ADDRESS = 10001;

    private Activity activity;

    private List<String> friendsList = new ArrayList<>();
    private List<String> imageList;
    private String videoPath = "";

    private EditText captionTextPost;
    private AutoCompleteTextView hashTagET, tagPeopleET;
    private EditText locationPlaceET, privacySettings;
    private RelativeLayout viewScreen;
    private ImageView imageBG;
    private TextView tagPerson;

    private File imageWithBgFile = null;

    private int[] arrImages = {R.drawable.background_white_and_food, R.drawable.background_blocks};
    boolean success = true;

    private MultipartBody.Part[] post_images = new MultipartBody.Part[2];
    private MultipartBody.Part[] post_imagesPart = new MultipartBody.Part[2];

    private String post_location = "", post_tag_people = "", privacyType = "public", post_type = "",
            allowCommentS = "yes", allowShareS = "yes", allowSaveS = "yes", imageBgPath = "";
    private int height = 1;

    private CustomProgressDialog dialog;
    private GetFriendsPresenter presenter;

    private PostPresenter postPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        inits();

    }

    private void inits() {

        activity = NewPostActivity.this;

        dialog = new CustomProgressDialog(activity);
        presenter = new GetFriendsPresenterImpl(activity, this);

        postPresenter = new PostPresenterImpl(activity, this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPostDiscard();
            }
        });

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("New Post");

        privacySettings = findViewById(R.id.privacySettings);
        privacySettings.setOnClickListener(this);

        locationPlaceET = findViewById(R.id.locationPlaceET);
        locationPlaceET.setOnClickListener(this);

        hashTagET = findViewById(R.id.hashTagET);
        captionTextPost = findViewById(R.id.captionTextPost);

        RecyclerView selectedNewImagesRC = findViewById(R.id.selectedNewImagesRC);
        selectedNewImagesRC.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        captionTextPost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                prepareHashTagList();
            }
        });

        hashTagET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (captionTextPost.getText().toString().isEmpty() ||
                        captionTextPost.getText().toString().length() < 5) {
                    Common.showToast(activity, "Please write caption first");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tagPerson = findViewById(R.id.tagPerson);
        tagPeopleET = findViewById(R.id.tagPeopleET);

        viewScreen = findViewById(R.id.viewScreen);
        imageBG = findViewById(R.id.imageBG);

        RecyclerView imagesBgRC = findViewById(R.id.imagesBgRC);
        imagesBgRC.setLayoutManager(new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false));
        imagesBgRC.setAdapter(new BackgroundsAdapter(activity, arrImages,
                new BackgroundsAdapter.SelectBgListener() {
                    @Override
                    public void onPositionSelected(int position) {
                        imageBG.setImageResource(arrImages[position]);
                    }
                }));

        ImageView textCenter = findViewById(R.id.textCenter);
        textCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captionTextPost.setGravity(Gravity.CENTER);
            }
        });

        ImageView textFromRight = findViewById(R.id.textFromRight);
        textFromRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captionTextPost.setGravity(Gravity.TOP | Gravity.END);
            }
        });

        ImageView textFromLeft = findViewById(R.id.textFromLeft);
        textFromLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captionTextPost.setGravity(Gravity.TOP | Gravity.START);
            }
        });

        Button postBtn = findViewById(R.id.postBtn);
        postBtn.setOnClickListener(this);

        Switch allowCommentSwitch = findViewById(R.id.allowCommentSwitch);
        Switch allowShareSwitch = findViewById(R.id.allowShareSwitch);
        Switch allowSaveSwitch = findViewById(R.id.allowSaveSwitch);

        allowCommentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    allowCommentS = "yes";
                } else {
                    allowCommentS = "no";
                }
            }
        });
        allowShareSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    allowShareS = "yes";
                } else {
                    allowShareS = "no";
                }
            }
        });
        allowSaveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    allowSaveS = "yes";
                } else {
                    allowSaveS = "no";
                }
            }
        });

        ImageView selectVideo = findViewById(R.id.selectVideo);

        // @@@@@@@@@@@@ get Intent @@@@@@@@@@@
        Intent intent = getIntent();
        videoPath = intent.getStringExtra("videoPath");
        imageList = intent.getStringArrayListExtra("imagesList");
        String textWithBg = intent.getStringExtra("textWithBg");

        if (videoPath != null) {
            post_type = "videos";
            selectVideo.setVisibility(View.VISIBLE);
            Glide.with(activity).load("file://" + videoPath)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(selectVideo);
        }

        if (imageList != null) {
            post_type = "images";
            selectedNewImagesRC.setVisibility(View.VISIBLE);
            selectedNewImagesRC.setAdapter(new SelectedImagesAdapter(activity, imageList,
                    new SelectedImagesAdapter.SelectImageToShowListener() {
                        @Override
                        public void selectImage(String position) {
                            startActivity(new Intent(activity, ShowSelectedImagesActivity.class)
                                    .putExtra("imagesList", (ArrayList<String>) imageList)
                                    .putExtra("position", position));
                        }
                    }));
        }

        if (textWithBg != null) {
            imagesBgRC.setVisibility(View.VISIBLE);
            post_type = "text_with_bg";
        }


        // @@@@@@@@@@@@@@@@@@ Get Friends List Api @@@@@@@@@@@@@@@@@@
        String id = App.getAppPreference().getString(AppConstants.USER_ID);
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", id);

        presenter.goGetFriends(map);


    }

    private void prepareHashTagList() {
        String caption = captionTextPost.getText().toString();
        caption = caption.replaceAll("[^a-zA-Z0-9\\s+]", "");

        String[] arr = caption.split("[\\s']");
        HashSet<String> typedWords = new HashSet<>(Arrays.asList(arr));

        typedWords.removeAll(StopWords.getStopWords());
        typedWords.remove("");

        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, R.layout.item_drop_down, new ArrayList<>(typedWords));
        //Getting the instance of AutoCompleteTextView
        hashTagET.setThreshold(1);//will start working from first character
        hashTagET.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.privacySettings:
                dialogPostPrivacy();
                break;
            case R.id.locationPlaceET:
                startActivityForResult(new Intent(activity, MapActivity.class),
                        REQUEST_ADDRESS);
                break;
            case R.id.postBtn:
                postApi();
                break;
        }
    }

    @Override
    public void showGetFriendsProgress() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void hideGetFriendsProgress() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onGetFriendsSuccess(FriendsListPojo body) {
        Common.showToast(activity, body.getMessage());

        if (body.getFriendsList() != null) {
            for (int i = 0; i < body.getFriendsList().size(); i++)
                friendsList.add(body.getFriendsList().get(i).getUserDetails().getName());

            //Creating the instance of ArrayAdapter containing list of fruit names
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (activity, R.layout.item_drop_down, new ArrayList<>(friendsList));
            //Getting the instance of AutoCompleteTextView
            tagPeopleET.setThreshold(1);//will start working from first character
            tagPeopleET.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

            tagPeopleET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                        long id) {
                    tagPerson.setVisibility(View.VISIBLE);

                    String at;
                    if (post_tag_people.isEmpty())
                        at = "@";
                    else
                        at = " @";

                    post_tag_people = post_tag_people + at + parent.getItemAtPosition(pos).toString();
                    tagPerson.setText(post_tag_people);

                    tagPeopleET.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tagPeopleET.dismissDropDown();
                        }
                    }, 500);
                    tagPeopleET.setText("");
                }
            });
        }
    }

    @Override
    public void onGetFriendsError(String error) {
        Common.showToast(activity, error);
    }

    @Override
    public void noNetGetFriends(String error) {
        Common.showToast(activity, error);
    }

    private void postApi() {

        String user_id = App.getAppPreference().getString(AppConstants.USER_ID);
        String post_title = hashTagET.getText().toString();
        String post_caption = captionTextPost.getText().toString();

        if (post_caption.isEmpty() && !post_type.equalsIgnoreCase("text_with_bg")) {
            Common.showToast(activity, "Please add post caption");
        } else if (post_title.isEmpty()) {
            Common.showToast(activity, "Please add post title");
        } else if (post_location.isEmpty()) {
            Common.showToast(activity, "Please add post location");
        } else {

            RequestBody user_idR = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody post_titleR = RequestBody.create(MediaType.parse("text/plain"), post_title);
            RequestBody post_captionR = RequestBody.create(MediaType.parse("text/plain"), post_caption);
            RequestBody post_locationR = RequestBody.create(MediaType.parse("text/plain"), post_location);
            RequestBody post_tag_peopleR = RequestBody.create(MediaType.parse("text/plain"), post_tag_people);
            RequestBody post_privacyR = RequestBody.create(MediaType.parse("text/plain"), privacyType);
            RequestBody allow_commentsR = RequestBody.create(MediaType.parse("text/plain"), allowCommentS);
            RequestBody allow_shareR = RequestBody.create(MediaType.parse("text/plain"), allowShareS);
            RequestBody allow_saveR = RequestBody.create(MediaType.parse("text/plain"), allowSaveS);
            RequestBody post_typeR = RequestBody.create(MediaType.parse("text/plain"), post_type);

            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("user_id", user_idR);
            map.put("post_caption", post_captionR);
            map.put("post_title", post_titleR);
            map.put("post_location", post_locationR);
            map.put("post_tag_people", post_tag_peopleR);
            map.put("post_privacy", post_privacyR);
            map.put("allow_comments", allow_commentsR);
            map.put("allow_share", allow_shareR);
            map.put("allow_save", allow_saveR);
            map.put("post_type", post_typeR);

            if (post_type.equalsIgnoreCase("videos")) {

                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("post_video", "", attachmentEmpty);

                RequestBody attachment = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part file1 = MultipartBody.Part.createFormData("post_video", "", attachment);

                RequestBody attachment2 = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part file2 = MultipartBody.Part.createFormData("post_images", "", attachment2);

                post_imagesPart[0] = file1;
                post_imagesPart[1] = file2;

                File file = new File(videoPath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part post_video = MultipartBody.Part.createFormData("post_video", file.getName(), requestFile);

                postPresenter.goPost(map, fileToUpload, post_imagesPart, post_video);
            } else if (post_type.equalsIgnoreCase("images")) {

                for (int i = 0; i < imageList.size(); i++) {
                    File file = new File(imageList.get(i));
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    post_images[i] = MultipartBody.Part.createFormData("post_images[]", file.getName(), requestFile);
                }

                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("post_video", "", attachmentEmpty);

                RequestBody attachment = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part file = MultipartBody.Part.createFormData("post_video", "", attachment);

                postPresenter.goPost(map, file, post_images, fileToUpload);

            } else if (post_type.equalsIgnoreCase("text_with_bg")) {
                height = viewScreen.getWidth();

                Bitmap bitmap = loadBitmapFromView(viewScreen,
                        viewScreen.getWidth(), viewScreen.getHeight());
                createDirectoryAndSaveFile(bitmap, "image_yeloopp" + System.currentTimeMillis());

                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("post_video", "", attachmentEmpty);

                RequestBody attachment = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part file1 = MultipartBody.Part.createFormData("post_video", "", attachment);

                RequestBody attachment2 = RequestBody.create(MediaType.parse("text/plain"), "");
                MultipartBody.Part file2 = MultipartBody.Part.createFormData("post_images", "", attachment2);

                post_imagesPart[0] = file1;
                post_imagesPart[1] = file2;

                File file = new File(imageBgPath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part text_bg_image = MultipartBody.Part.createFormData("text_bg_image", file.getName(), requestFile);

                postPresenter.goPost(map, text_bg_image, post_imagesPart, fileToUpload);
            }
        }
    }

    @Override
    public void showPostProgress() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void hidePostProgress() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onPostSuccess(CommonPojo body) {
        Common.showToast(activity, body.getMessage());
    }

    @Override
    public void onPostError(String error) {
        Common.showToast(activity, error);
    }

    @Override
    public void noNetPost(String error) {
        Common.showToast(activity, error);
    }

    private void dialogPostPrivacy() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_post_privacy_settings);

        TextView publicBtn = dialog.findViewById(R.id.publicBtn);
        TextView privateBtn = dialog.findViewById(R.id.privateBtn);
        TextView friendsOnlyBtn = dialog.findViewById(R.id.friendsOnlyBtn);
        TextView onlyMeBtn = dialog.findViewById(R.id.onlyMeBtn);

        publicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacyType = "public";
                privacySettings.setText(privacyType);
                dialog.dismiss();
            }
        });

        privateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacyType = "private";
                privacySettings.setText(privacyType);
                dialog.dismiss();
            }
        });

        friendsOnlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacyType = "friends_only";
                privacySettings.setText(privacyType);
                dialog.dismiss();
            }
        });

        onlyMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacyType = "only_me";
                privacySettings.setText(privacyType);
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        dialogPostDiscard();
    }

    private void dialogPostDiscard() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_remove_follower);

        TextView textRemoveFollower = dialog.findViewById(R.id.textRemoveFollower);
        String discardPost = "Discard Post?";
        textRemoveFollower.setText(discardPost);

        TextView textRemoveFollowerName = dialog.findViewById(R.id.textRemoveFollowerName);
        String lostPost = "If you go back, you will lose your post.";
        textRemoveFollowerName.setText(lostPost);

        Button discardPostBtn = dialog.findViewById(R.id.keepFollowerBtn);
        String discard = "Discard";
        discardPostBtn.setText(discard);

        Button keepPostBtn = dialog.findViewById(R.id.removeFollowerBtn);
        String keep = "Keep";
        keepPostBtn.setText(keep);

        discardPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        keepPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADDRESS && resultCode == RESULT_OK && data != null) {
            post_location = data.getStringExtra("address");
            locationPlaceET.setText(post_location);
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File folder = new File(getFilesDir() + "Yeloopp");

        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            imageWithBgFile = new File(getFilesDir() +
                    "Yeloopp", fileName);

            try {
                FileOutputStream out = new FileOutputStream(imageWithBgFile);
                imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Beant ", "createDirectoryAndSaveFile: Unable to Create folder");
        }

        if (imageWithBgFile.exists()) {
            Log.d("Beant", "createDirectoryAndSaveFile: " + imageWithBgFile.getAbsolutePath());
            Bitmap myBitmap = BitmapFactory.decodeFile(imageWithBgFile.getAbsolutePath());

            imageBG.setImageBitmap(myBitmap);

            Uri uri = getImageUri(activity, myBitmap);
            imageBgPath = getRealPathFromUri(uri);
        }

        ViewGroup.LayoutParams layoutParams = viewScreen.getLayoutParams();
        layoutParams.height = height;
        viewScreen.setLayoutParams(layoutParams);
    }

    private Uri getImageUri(Activity activity, Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromUri(Uri tempUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = activity.getContentResolver().query(tempUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}