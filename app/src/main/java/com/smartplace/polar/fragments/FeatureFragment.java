package com.smartplace.polar.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.smartplace.polar.R;
import com.smartplace.polar.adapters.RequirementsAdapter;
import com.smartplace.polar.helpers.Constants;
import com.smartplace.polar.helpers.MemoryServices;
import com.smartplace.polar.helpers.Utilities;
import com.smartplace.polar.helpers.WebServices;
import com.smartplace.polar.models.Comment;
import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Link;
import com.smartplace.polar.models.Requirement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeatureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeatureFragment extends Fragment {

    private static final int SELECT_PICTURE = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int MOVE_ABOVE = 0;
    private static final int MOVE_BELOW = 1;

    private Bitmap mBitmap;

    private Feature mFeature;
    private RequirementsAdapter mRequirementsAdapter;
    private TextView mTvFeature;
    private ListView mListRequirements;
    private EditText mEtRequirement;
    private Button mBtnSend;
    private ImageView mIvBtnType;
    private int mType = Constants.TYPE_REQUIREMENT;
    private LinearLayout mLlNewRequirement;
    private com.smartplace.polar.listeners.OnFeatureListener mListener;

    public static FeatureFragment newInstance() {
        FeatureFragment fragment = new FeatureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FeatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feature, container, false);

        mTvFeature = (TextView)v.findViewById(R.id.tv_feature);
        mListRequirements = (ListView)v.findViewById(R.id.list_requirements);
        mLlNewRequirement = (LinearLayout)v.findViewById(R.id.ll_new_requirement);
        mEtRequirement = (EditText) v.findViewById(R.id.et_requirement);
        mBtnSend = (Button)v.findViewById(R.id.btn_send);
        mIvBtnType = (ImageView)v.findViewById(R.id.iv_btn_type);

        mFeature = new Feature();
        mFeature.setId("");
        mFeature.setName("");
        mFeature.setItems(new ArrayList<Requirement>());

        mRequirementsAdapter = new RequirementsAdapter(getActivity(),mFeature.getItems());
        mListRequirements.setAdapter(mRequirementsAdapter);

        mListRequirements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (mListener != null) {

                    mListener.onRequirementSelected(mFeature.getItems().get(i));
                    mRequirementsAdapter.setSelectedItem(i);
                }
            }
        });
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mEtRequirement.getText().toString().equals("")) {

                    Requirement requirement = new Requirement();
                    requirement.setValue(mEtRequirement.getText().toString());
                    requirement.setType(mType);
                    requirement.setInLinks(new ArrayList<Link>());
                    requirement.setOutLinks(new ArrayList<Link>());
                    requirement.setOrder(mFeature.getItems().size());
                    requirement.setComments(new ArrayList<Comment>());

                    mFeature.getItems().add(mRequirementsAdapter.getSelectedItem() + 1, requirement);
                    mEtRequirement.setText("");
                    mRequirementsAdapter.notifyDataSetChanged();
                    if(mRequirementsAdapter.getSelectedItem() == mRequirementsAdapter.getCount()-2) {
                        mListRequirements.smoothScrollToPosition(mRequirementsAdapter.getCount() - 1);
                    }

                }
            }
        });
        mIvBtnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), mIvBtnType);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_types, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().toString().equals("Imagen")) {

                            selectImage();
                        } else {
                            mEtRequirement.setHint(item.getTitle());
                            mType = Utilities.getRequirementTypeByName(item.getTitle().toString());
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        return v;
    }
    public void setOnFeatureListener(com.smartplace.polar.listeners.OnFeatureListener onFeatureListener){

        mListener = onFeatureListener;

    }

    public void setFeature(Feature feature){

        if(isAdded()) {
            //change view
            mTvFeature.setText(feature.getName());
            mFeature.setId(feature.getId());
            mFeature.setName(feature.getName());
            mFeature.getItems().clear();
            mRequirementsAdapter.notifyDataSetChanged();

            getSpecificationFeature();


        }

    }
    public void changeRequirementOrder(String movedRequirementID,String referenceRequirementID,int orderType){

        Requirement movedRequirement = mFeature.getItemByID(movedRequirementID);
        Requirement referenceRequirement = mFeature.getItemByID(referenceRequirementID);

        if(orderType == MOVE_ABOVE){



        }else{

        }

    }
    public void removeSelectedRequirement(){

        int selected = mRequirementsAdapter.getSelectedItem();
        mRequirementsAdapter.setSelectedItem(selected-1);
        mFeature.getItems().remove(selected);
    }
    public void getSpecificationFeature(){

        WebServices.getFeature(MemoryServices.getPublicKey(getActivity()), mFeature.getId(), new WebServices.OnFeatureListener() {
            @Override
            public void onFeatureReceived(Feature feature) {

                setFeatureData(feature);
            }
        });
    }

    public void setEnabled(boolean enabled){

        if(enabled){

            mLlNewRequirement.setVisibility(View.VISIBLE);

        }else{

            mLlNewRequirement.setVisibility(View.GONE);
        }
    }
    private void setFeatureData(Feature feature){


        if (isAdded()) {
            mFeature.setId(feature.getId());
            mFeature.setName(feature.getName());
            mFeature.getItems().clear();
            mFeature.getItems().addAll(feature.getItems());

            mTvFeature.setText(feature.getName());
            mRequirementsAdapter.notifyDataSetChanged();

            if (mFeature.getItems().size() > 0) {
                mListRequirements.performItemClick(mListRequirements.getAdapter().getView(0, null, null), 0, mListRequirements.getItemIdAtPosition(0));
                mListRequirements.smoothScrollToPosition(0);
            }

        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_PICTURE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                    btmapOptions.inSampleSize = 2;
                    mBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);

                    MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                            .title(R.string.new_image)
                            .customView(R.layout.dialog_add_image, true)
                            .positiveText(R.string.add)
                            .negativeText(R.string.cancel)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    String comment = ((EditText) dialog.getView().findViewById(R.id.et_name)).getText().toString();
                                    Requirement requirement = new Requirement();
                                    requirement.setId("1");
                                    requirement.setOrder(1);
                                    requirement.setType(Constants.TYPE_IMAGE);
                                    requirement.setImage(mBitmap);
                                    requirement.setValue(comment);
                                    requirement.setInLinks(new ArrayList<Link>());
                                    requirement.setOutLinks(new ArrayList<Link>());
                                    mFeature.getItems().add(mRequirementsAdapter.getSelectedItem() + 1, requirement);
                                    mRequirementsAdapter.notifyDataSetChanged();
                                    mListRequirements.smoothScrollToPosition(mRequirementsAdapter.getCount() - 1);
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    // TODO
                                }
                            })
                            .show();

                    ImageView ivImage = (ImageView)dialog.findViewById(R.id.iv_image);
                    ivImage.setImageBitmap(mBitmap);

                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
                    //mImageView.setImageBitmap(bm);


                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "test";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    fOut = new FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, this.getActivity());
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                btmapOptions.inSampleSize = 2;
                mBitmap = BitmapFactory.decodeFile(tempPath, btmapOptions);
                MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .title(R.string.new_image)
                        .customView(R.layout.dialog_add_image, true)
                        .positiveText(R.string.add)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                String comment = ((EditText) dialog.getView().findViewById(R.id.et_name)).getText().toString();
                                Requirement requirement = new Requirement();
                                requirement.setId("1");
                                requirement.setOrder(1);
                                requirement.setType(Constants.TYPE_IMAGE);
                                requirement.setImage(mBitmap);
                                requirement.setValue(comment);
                                requirement.setInLinks(new ArrayList<Link>());
                                requirement.setOutLinks(new ArrayList<Link>());
                                mFeature.getItems().add(mRequirementsAdapter.getSelectedItem() + 1, requirement);
                                mRequirementsAdapter.notifyDataSetChanged();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        })
                        .show();

                ImageView ivImage = (ImageView)dialog.findViewById(R.id.iv_image);
                ivImage.setImageBitmap(mBitmap);
            }
        }
    }
    public String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void orderRequirements(ArrayList requirements){

        Collections.sort(requirements, new Comparator() {

            public int compare(Object o1, Object o2) {
                Requirement r1 = (Requirement) o1;
                Requirement r2 = (Requirement) o2;
                return r1.getOrder()-r2.getOrder();
            }

        });
    }
}
