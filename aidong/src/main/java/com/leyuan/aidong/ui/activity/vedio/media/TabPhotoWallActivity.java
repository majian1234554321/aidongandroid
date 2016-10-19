package com.leyuan.aidong.ui.activity.vedio.media;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.utils.common.MXLog;
import com.leyuan.aidong.utils.common.UrlLink;
import com.leyuan.aidong.http.HttpConfig;
import com.leyuan.aidong.entity.model.location.ImageItem;
import com.leyuan.aidong.entity.model.result.MsgResult;
import com.leyuan.aidong.utils.PopupWindowUitl;
import com.leyuan.aidong.utils.Utils;
import com.leyuan.aidong.utils.photo.Bimp;
import com.leyuan.aidong.utils.photo.Res;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabPhotoWallActivity extends BaseActivity implements IHttpCallback {
    public static Bitmap bimap;
    private View parentView;
    //	private PopupWindow window = null;
    private Button bt1, bt2;
    private LinearLayout l1, l2;
    private RelativeLayout ll_popup;
    private boolean uploadVideo = false;
    private boolean uploadPhoto = false;
    private boolean hasphoto = false;
    private GridAdapter adapter;
    public byte currentPosition;
    private GridView noScrollgridview;
    String currentAddFileName;
    private Intent intent;
    ArrayList<ImageItem> sbm = new ArrayList<ImageItem>();
    private static final int DELETEPHOTO = 0;
    private static final int UPLOADPHOTO = 1;
    private File file;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    // 图片处理
    protected DisplayImageOptions options;
    private View view_backgroud;

    int sum = 0;
    int m = 0;
    private PopupWindowUitl pop_album;
    private PopupWindowUitl pop_delete;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Res.init(this);
        bimap = BitmapFactory.decodeResource(getResources(),
                R.drawable.add_picture);
        bimap = Bimp.zoomImg(bimap, 50, 50);
        parentView = getLayoutInflater().inflate(R.layout.add_picture_layout,
                null);
        Constant.Num = Constant.MAX_PHOTO_WALL_COUNT;
        setContentView(parentView);
        intent = new Intent();
        //		initTop(getResources().getString(R.string.add_the_picture),
        //				getResources().getString(R.string.cancel), getResources()
        //						.getString(R.string.complete));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_picture)
                .showImageForEmptyUri(R.drawable.icon_picture)
                .showImageOnFail(R.drawable.icon_picture)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        //		//删除照片
        //		addTask(this, new IHttpTask(UrlLink.DELETEPHOTOS_URL, paramsinitDelete(""), MsgResult.class), HttpConfig.DELETE,
        //				DELETEPHOTO);
        //		//上传照片
        //		addTask(this, new IHttpTask(UrlLink.UPLOADPHOTOS_URL, paramsinitUpLoad(file), MsgResult.class), HttpConfig.POST,
        //				UPLOADPHOTO);
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        view_backgroud = (View) findViewById(R.id.view_backgroud);

        hasphoto = getIntent().getBooleanExtra("hasphoto", false);
        uploadPhoto = hasphoto;

        if (!getIntent().getBooleanExtra("isalbum", false)) {
            if (!hasphoto) {
                Bimp.tempSelectBitmap = new ArrayList<ImageItem>();
            }
            sbm.addAll(Bimp.tempSelectBitmap);
        }

        adapter = new GridAdapter(this);
        //		showOutMenu();

        //初始化popwindow
        initPopupWindow();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setNumColumns(4);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                currentPosition = (byte) position;
                if (position == Bimp.tempSelectBitmap.size()) {

                    //					setPopWindow(false);
                    //					pop_album.show(parentView);

                    intent = new Intent(App.mInstance, AlbumActivity.class);
                    intent.putExtra(Constant.BUNDLE_PHOTOWALL_POSITION, currentPosition);
                    intent.putExtra(Constant.BUNDLE_CLASS, TabPhotoWallActivity.class);
                    startActivityForResult(intent, Constant.RC_SELECTABLUMN);


                } else if (uploadPhoto) {
                    // 弹出删除相册
                    //					setPopWindow(true);
                    pop_delete.show(parentView);
                }
                //				ll_popup.startAnimation(AnimationUtils.loadAnimation(
                //						TabPhotoWallActivity.this,
                //						R.anim.activity_translate_in));
                //				window.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
            }
        });
    }


    public List<BasicNameValuePair> paramsinitDelete(String photoid) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("photoid", photoid));
        return paramsaaa;
    }

    public List<BasicNameValuePair> paramsinitUpLoad() {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        //		paramsaaa.add(new BasicNameValuePair("photo", photo));
        return paramsaaa;
    }

    // call this method when go out
    protected void mfinish() {
        Bimp.tempSelectBitmap = sbm;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        //		if(currentAddFileName!= null){
        //			ImageItem takePhoto = new ImageItem();
        //			// if (data != null) {
        //			// freeBitmap();
        //			// bimap = (Bitmap) data.getExtras().getParcelable("data");
        //			// }
        //			try {
        //				File photoFile = MxFileUtil.getTakePhotoFile(
        //						MxFileUtil.getTakePhotoFileFolder(),
        //						currentAddFileName);
        ////				Uri u = Uri.parse(android.provider.MediaStore.Images.Media
        ////						.insertImage(getContentResolver(),
        ////								photoFile.getAbsolutePath(), null, null));
        //				Uri u = Uri.parse(photoFile.getAbsolutePath());
        //				if (u != null) {
        ////					int dpSize = (int) getResources().getDimension(
        ////							R.dimen.pref_145dp);
        ////					takePhoto.setBitmap(MxImageUtil.resizeImage(
        ////							photoFile.getAbsolutePath(), dpSize, dpSize));
        //					takePhoto.setImagePath(photoFile.getAbsolutePath());
        //					Bimp.tempSelectBitmap.add(takePhoto);
        //					uploadPhoto = true;
        //					uploadVideo = false;
        //					refreshPopMenuStatus();
        //					adapter.notifyDataSetChanged();
        //				} else {
        //					showShortToast(getResources().getString(
        //							R.string.error_no_get_origin_pic));
        //				}
        //			} catch (Exception e) {
        //				e.printStackTrace();
        //				showShortToast(getResources().getString(
        //						R.string.error_no_get_origin_pic));
        //			}
        //			currentAddFileName =null;
        //		}
        super.onResume();

        uploadPhoto = true;
        uploadVideo = false;
        //			refreshPopMenuStatus();
        adapter.notifyDataSetChanged();

    }

    //返回键处理事件
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Bimp.tempSelectBitmap = sbm;
        super.onBackPressed();
    }

    //右键处理事件
    protected void rightTxtOnClick() {
        // TODO Auto-generated method stub

        try {
//            startloadingDialog();
            m = 0;
            sum = 0;
            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {

                if (Bimp.tempSelectBitmap.get(i).getImageserId() == null) {
                    sum++;
                    Map<String, Object[]> files = new HashMap<String, Object[]>();
                    List<String> list = new ArrayList<String>();
                    list.add(Bimp.tempSelectBitmap.get(i).getImagePath());

                    files.put("photo", list.toArray());

                    addTask(TabPhotoWallActivity.this, new IHttpTask(UrlLink.UPLOADPHOTOS_URL, paramsinitUpLoad(), files, MsgResult.class), HttpConfig.POST,
                            UPLOADPHOTO);
                }
            }
            if (sum == 0) {
//                stoploadingDialog();
                finish();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //		Intent intent = new Intent();
        //		setResult(RESULT_OK, intent);
        //		finish();
    }

    protected void initData() {

    }

    //	private void setPopWindow(boolean isDeletePhotoStatus) {
    //		if (isDeletePhotoStatus) {
    //			l1.setVisibility(View.GONE);
    //			// l2.setVisibility(View.GONE);
    //			// l3.setVisibility(View.GONE);
    //			bt2.setText(R.string.delete);
    //			bt2.setTextColor(Color.GRAY);
    //		} else {
    //			l1.setVisibility(View.VISIBLE);
    //			// l2.setVisibility(View.VISIBLE);
    //			// l3.setVisibility(View.VISIBLE);
    //			bt2.setText(R.string.album);
    //			// if (uploadPhoto) {
    //			// bt2.setTextColor(Color.GRAY);
    //			// } else {
    //			bt2.setTextColor(Color.BLACK);
    //			// }
    //		}
    //	}

    //	private void refreshPopMenuStatus() {
    //		if (!uploadPhoto && !uploadVideo) {
    //			bt1.setTextColor(Color.BLACK);
    //			bt2.setTextColor(Color.BLACK);
    //			// bt3.setTextColor(Color.BLACK);
    //			// bt4.setTextColor(Color.BLACK);
    //		} else if (uploadPhoto) {
    //			bt1.setTextColor(Color.BLACK);
    //			bt2.setTextColor(Color.BLACK);
    //			// bt3.setTextColor(Color.GRAY);
    //			// bt4.setTextColor(Color.GRAY);
    //		} else if (uploadVideo) {
    //			bt1.setTextColor(Color.GRAY);
    //			bt2.setTextColor(Color.GRAY);
    //			// bt3.setTextColor(Color.BLACK);
    //			// bt4.setTextColor(Color.BLACK);
    //		}
    //	}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.RC_SELECTABLUMN:
                if (resultCode == RESULT_OK) {
                    uploadPhoto = true;
                    uploadVideo = false;
                    //				refreshPopMenuStatus();
                    adapter.notifyDataSetChanged();
                }
                break;
            case Constant.RC_TAKEPHOTO:
                if (resultCode == RESULT_OK) {
                    ImageItem takePhoto = new ImageItem();
                    // if (data != null) {
                    // freeBitmap();
                    // bimap = (Bitmap) data.getExtras().getParcelable("data");
                    // }
                    try {
                        File photoFile = Utils.getTakePhotoFile(
                                Utils.getTakePhotoFileFolder(), currentAddFileName);
                        MXLog.out("file:" + photoFile.length());
                        Uri u = Uri.parse(photoFile
                                .getAbsolutePath());
                        if (u != null) {
                            //						int dpSize = (int) getResources().getDimension(
                            //								R.dimen.pref_145dp);
                            //						takePhoto.setBitmap(Utils.resizeImage(
                            //								photoFile.getAbsolutePath(), dpSize, dpSize));
                            takePhoto.setImagePath(photoFile.getAbsolutePath());
                            Bimp.tempSelectBitmap.add(takePhoto);
                            uploadPhoto = true;
                            uploadVideo = false;
                            //						refreshPopMenuStatus();
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.show(getResources().getString(
                                    R.string.error_no_get_origin_pic),this);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        ToastUtil.show(getResources().getString(
                                R.string.error_no_get_origin_pic),this);

                    }
                }
                break;
        }
    }

    private void initPopupWindow() {
        pop_album = new PopupWindowUitl(this, "相册", view_backgroud);
        pop_album.setOnPopupWindowItemClickListener(new PopupWindowUitl.OnPopupWindowItemClickListener() {

            @Override
            public void onThirdItemClick() {

            }

            @Override
            public void onSecondItemClick() {

            }

            @Override
            public void onFourthItemClick() {

            }

            @Override
            public void onFiveItemClick() {

            }

            @Override
            public void onFirstItemClick() {
                intent = new Intent(App.mInstance, AlbumActivity.class);
                intent.putExtra(Constant.BUNDLE_PHOTOWALL_POSITION, currentPosition);
                intent.putExtra(Constant.BUNDLE_CLASS, TabPhotoWallActivity.class);
                startActivityForResult(intent, Constant.RC_SELECTABLUMN);
            }

            @Override
            public void onDismissListener() {

            }
        });


        pop_delete = new PopupWindowUitl(this, "删除", view_backgroud);
        pop_delete.setOnPopupWindowItemClickListener(new PopupWindowUitl.OnPopupWindowItemClickListener() {

            @Override
            public void onThirdItemClick() {

            }

            @Override
            public void onSecondItemClick() {

            }

            @Override
            public void onFourthItemClick() {

            }

            @Override
            public void onFiveItemClick() {

            }

            @Override
            public void onFirstItemClick() {

                if (uploadPhoto
                        && currentPosition < Bimp.tempSelectBitmap.size()) {
                    ImageItem item = Bimp.tempSelectBitmap
                            .get(currentPosition);
                    if (item != null) {

                        if (item.getImageserId() != null) {
                            //删除照片
                            addTask(TabPhotoWallActivity.this, new IHttpTask(UrlLink.DELETEPHOTOS_URL, paramsinitDelete(item.getImageserId()), MsgResult.class), HttpConfig.DELETE,
                                    DELETEPHOTO);
                        } else {
                            Bimp.tempSelectBitmap.remove(currentPosition);

                            adapter.notifyDataSetChanged();
                        }


                    }
                }
            }

            @Override
            public void onDismissListener() {

            }
        });


    }

    //	private void showOutMenu() {
    //		window = new PopupWindow(TabPhotoWallActivity.this);
    //		View view = getLayoutInflater().inflate(
    //				R.layout.popmenu_the_individualdynamic, null);
    //		ll_popup = (RelativeLayout) view.findViewById(R.id.ll_popup);
    //		window.setWidth(LayoutParams.MATCH_PARENT);
    //		window.setHeight(LayoutParams.WRAP_CONTENT);
    //		window.setBackgroundDrawable(new BitmapDrawable());
    //		window.setFocusable(true);
    //		window.setOutsideTouchable(true);
    //		window.setContentView(view);
    //		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
    //		bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
    //		bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
    //		// bt3 = (Button) view.findViewById(R.id.item_popupwindows_recording);
    //		// bt4 = (Button) view
    //		// .findViewById(R.id.item_popupwindows_the_local_video);
    //		Button bt5 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
    //
    //		l1 = (LinearLayout) view.findViewById(R.id.l1);
    ////		l1.setVisibility(View.GONE);
    //		l2 = (LinearLayout) view.findViewById(R.id.l2);
    //		// l3 = (LinearLayout) view.findViewById(R.id.l3);
    //
    //		parent.setOnClickListener(new OnClickListener() {
    //			@Override
    //			public void onClick(View v) {
    //				window.dismiss();
    //			}
    //		});
    //		bt1.setOnClickListener(new OnClickListener() {
    //			@Override
    //			public void onClick(View v) {
    //				if (uploadVideo)
    //					return;
    //				window.dismiss();
    //				File photoFileFolder = Utils.getTakePhotoFileFolder();
    //				if (photoFileFolder == null) {
    //					showShortToast(getResources().getString(R.string.invalidSD));
    //				} else {
    //					currentAddFileName = String.valueOf(System
    //							.currentTimeMillis());
    //					File photoFile = Utils.getTakePhotoFile(photoFileFolder,
    //							currentAddFileName);
    //					if (photoFile != null) {
    //						Uri u = Uri.fromFile(photoFile);
    //						intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    ////						intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
    //						/*
    //						 * 指定一个路径，可以获得到照片原始尺寸大小图片，
    //						 * 且onresult中的data会为null，需要从uri中获取图片 若不指定，只能获取到照片缩略图
    //						 */
    //						intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
    //						startActivityForResult(intent, RC_TAKEPHOTO);
    //					} else {
    //						showShortToast(getResources().getString(
    //								R.string.error_create_photo_file));
    //					}
    //				}
    //			}
    //		});
    //		bt2.setOnClickListener(new OnClickListener() {
    //			@Override
    //			public void onClick(View v) {
    //				if (uploadVideo)
    //					return;
    //				if (bt2.getText().equals(
    //						getResources().getString(R.string.delete))) {
    //					if (uploadPhoto
    //							&& currentPosition < Bimp.tempSelectBitmap.size()) {
    //						ImageItem item = Bimp.tempSelectBitmap
    //								.get(currentPosition);
    //						if (item != null) {
    //
    //							if(item.getImageserId()!= null){
    //							//删除照片
    //							addTask(TabPhotoWallActivity.this, new IHttpTask(UrlLink.DELETEPHOTOS_URL, paramsinitDelete(item.getImageserId()), MsgResult.class), HttpConfig.DELETE,
    //									DELETEPHOTO);
    //							startloadingDialog();
    //							}else {
    //							Bimp.tempSelectBitmap.remove(currentPosition);
    ////							if (Bimp.tempSelectBitmap.size() == 0) {
    ////								uploadPhoto = false;
    ////								adapter = new GridAdapter(
    ////										TabPhotoWallActivity.this);
    ////								noScrollgridview.setAdapter(adapter);
    ////							}
    //							adapter.notifyDataSetChanged();
    //							}
    ////							System.gc();
    //
    //						}
    //					}
    //				} else {
    //					intent = new Intent(BaseApp.mInstance, AlbumActivity.class);
    //					intent.putExtra(BUNDLE_PHOTOWALL_POSITION, currentPosition);
    //					intent.putExtra(BUNDLE_CLASS, TabPhotoWallActivity.class);
    //					startActivityForResult(intent, RC_SELECTABLUMN);
    //				}
    //
    //				window.dismiss();
    //
    //			}
    //		});
    //		bt5.setOnClickListener(new OnClickListener() {
    //			@Override
    //			public void onClick(View v) {
    //				window.dismiss();
    //			}
    //		});
    //		window.setOnDismissListener(new OnDismissListener() {
    //			@Override
    //			public void onDismiss() {
    //				ll_popup.clearAnimation();
    //			}
    //		});
    //	}

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            if (uploadPhoto) {
                if (Bimp.tempSelectBitmap.size() >= Constant.MAX_PHOTO_WALL_COUNT) {
                    return Constant.MAX_PHOTO_WALL_COUNT;
                }
                return (Bimp.tempSelectBitmap.size() + 1);
            }
            return 1;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //			if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
            //			} else {
            //				holder = (ViewHolder) convertView.getTag();
            //			}
            if (uploadPhoto) {
                if (position == 8) {
                    holder.image.setVisibility(View.GONE);
                } else {
                    if (position == Bimp.tempSelectBitmap.size()) {
                        holder.image.setImageBitmap(bimap);
                    } else {
                        //						holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(
                        //								position).getBitmap());
                        if (Bimp.tempSelectBitmap.get(
                                position).getImagePath() != null) {
                            if (Bimp.tempSelectBitmap.get(
                                    position).getImagePath().startsWith("http")) {

                                imageLoader.displayImage(
                                        Bimp.tempSelectBitmap.get(position).getImagePath()
                                        , holder.image, options);

                            } else {
                                imageLoader.displayImage("file://" + Bimp.tempSelectBitmap.get(
                                        position).getImagePath(), holder.image);
                            }
                        } else {
                            holder.image.setImageBitmap(null);
                        }
                    }
                }
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.FLAG_CANCELED) {
            finish(); // 返回键的物理代码
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {

        switch (requestCode) {
            case DELETEPHOTO:
//                stoploadingDialog();
                MsgResult msgResult = (MsgResult) data;
                if (msgResult.getCode() == 1) {
                    Bimp.tempSelectBitmap.remove(currentPosition);
                    adapter.notifyDataSetChanged();
                }
                break;
            case UPLOADPHOTO:
                m++;
                if (m == sum) {
//                    stoploadingDialog();
                }
                MsgResult mupres = (MsgResult) data;
                if (mupres.getCode() == 1) {
                    if (m == sum) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String reason, final int requestCode) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                switch (requestCode) {
                    case DELETEPHOTO:
//                        stoploadingDialog();

                    case UPLOADPHOTO:
                        m++;
                        if (m == sum) {
//                            stoploadingDialog();
                        }

                    default:
                        break;
                }
            }
        });

    }
}
