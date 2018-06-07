package com.leyuan.aidong.module.scan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.example.aidong.R;
import com.leyuan.aidong.entity.model.FacePay;
import com.leyuan.aidong.module.scan.camera.CameraManager;
import com.leyuan.aidong.module.scan.decoding.CaptureActivityHandler;
import com.leyuan.aidong.module.scan.decoding.InactivityTimer;
import com.leyuan.aidong.module.scan.view.ViewfinderView;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Initial the camera
 */
public class MipcaActivityCapture extends BaseActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private static final int DECODE = 0;
    private static final int FOLLOWCODE = 1;
    private String code;
    private String mxidcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.scancode_capture);
        //initTop("扫一扫", true);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String code_type = result.getBarcodeFormat().toString();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else if (isGoodJson(resultString)) {
            Gson json = new Gson();
            try {
                FacePay pay = json.fromJson(resultString, FacePay.class);
                if (pay != null && pay.getNo() != null) {
                    Intent intent2 = new Intent(this, LoginActivity.class);
                    intent2.putExtra("order", pay.getNo());
                    startActivity(intent2);
                    finish();

                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }


            //解析json，
        } else if (isMxCard(resultString)) {
            Intent intent = new Intent(this, LoginActivity.class);
            if (resultString.length() > 9) {
                String id = resultString.substring(9);
                intent.putExtra("no", id);
                startActivity(intent);
                finish();
            } else {
                //ToastUtil.show("获取美型号失败", this);
            }

        } else {
            //			Intent intent = new Intent(this, SignInActivity.class);
            //			startActivity(intent);
            //			finish();

            new AlertDialog.Builder(MipcaActivityCapture.this).setTitle("提示")
                    .setMessage("请扫描爱动二维码")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                            onPause();
                            onResume();
                        }
                    }).setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.FLAG_CANCELED) {
                        dialog.dismiss();
                        onPause();
                        onResume();
                    }
                    return false;
                }
            }).show();


        }
    }

    private boolean isMxCard(String resultString) {
        Pattern p = Pattern.compile("^meix_shop(.*)");
        Matcher m = p.matcher(resultString);
        return m.matches();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    //	@Override
    //	public void onError(String reason, int requestCode) {
    //
    //	}
    //
    //	@Override
    //	public void onGetData(Object data, int requestCode, String response) {
    //		switch (requestCode) {
    //		case DECODE:
    //			MsgResult msgResult = (MsgResult) data;
    //			if(msgResult.getCode() == 1){
    ////				 Intent i = new Intent(this, YesDeCodeActivity.class);
    ////				 i.putExtra("code", code);
    ////				 startActivity(i);
    ////				 finish();
    //			}else{
    //				new AlertDialog.Builder(MipcaActivityCapture.this).setTitle("提示")
    //				.setMessage(msgResult.getMessage())
    //				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    //
    //					@Override
    //					public void onClick(DialogInterface dialog, int which) {
    //						// TODO Auto-generated method stub
    //						dialog.dismiss();
    //						onPause();
    //						onResume();
    //					}
    //				}).setOnKeyListener(new DialogInterface.OnKeyListener() {
    //
    //					@Override
    //					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
    //						// TODO Auto-generated method stub
    //						if (keyCode == KeyEvent.FLAG_CANCELED) {
    //							dialog.dismiss();
    //							onPause();
    //							onResume();
    //						}
    //						return false;
    //					}
    //				}).show();
    //			}
    //			break;
    //		case FOLLOWCODE:
    //			MsgResult msgResult2 = (MsgResult) data;
    //			if(msgResult2.getCode() == 1){
    //				 showShortToast("添加学员成功");
    //				 finish();
    //			}else{
    //				new AlertDialog.Builder(MipcaActivityCapture.this).setTitle("提示")
    //				.setMessage(msgResult2.getMessage())
    //				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    //
    //					@Override
    //					public void onClick(DialogInterface dialog, int which) {
    //						// TODO Auto-generated method stub
    //						dialog.dismiss();
    //						onPause();
    //						onResume();
    //					}
    //				}).setOnKeyListener(new DialogInterface.OnKeyListener() {
    //
    //					@Override
    //					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
    //						// TODO Auto-generated method stub
    //						if (keyCode == KeyEvent.FLAG_CANCELED) {
    //							dialog.dismiss();
    //							onPause();
    //							onResume();
    //						}
    //						return false;
    //					}
    //				}).show();
    //			}
    //			break;
    //		default:
    //			break;
    //		}
    //	}

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }

        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(json);
            return jsonElement.isJsonObject();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }


}