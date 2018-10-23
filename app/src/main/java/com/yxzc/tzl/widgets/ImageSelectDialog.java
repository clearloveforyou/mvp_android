package com.yxzc.tzl.widgets;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yxzc.tzl.R;
import com.yxzc.tzl.application.AppContext;
import com.yxzc.tzl.base.BaseDialog;
import com.yxzc.tzl.constants.CommonCode;
import com.yxzc.tzl.constants.PermissionCode;
import com.yxzc.tzl.utils.ObjectUtils;
import com.yxzc.tzl.utils.SDCardUtils;
import com.yxzc.tzl.utils.TimeUtil;
import com.yxzc.tzl.utils.ToastUtils;
import com.yxzc.tzl.utils.permission.PermissionUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.widgets
 * @Author: HSL
 * @Time: 2018/10/22 15:08
 * @E-mail: 13967189624@163.com
 * @Description: 图片选择
 */
public class ImageSelectDialog extends BaseDialog {

    @BindView(R.id.camera_tv)
    TextView cameraTv;
    @BindView(R.id.album_tv)
    TextView albumTv;

    private Fragment mFragment;
    private static Uri mImageUriFromCamera;

    public ImageSelectDialog(FragmentActivity activity) {
        super(activity);
        mFragment = null;
        mPermissionUtils.initRxPermission(activity);
    }

    public ImageSelectDialog(Fragment fragment) {
        super(fragment.getActivity());
        mFragment = fragment;
        mPermissionUtils.initRxPermission(fragment);
    }

    @Override
    protected void initAttribute(Window window, WindowManager.LayoutParams layoutParams) {
        //设置动画
        window.setWindowAnimations(R.style.dialog_translate_anim);
        //设置宽度
        setFullScreenWidth();
        //位置
        layoutParams.gravity = Gravity.BOTTOM;
    }

    @Override
    protected void initLayout(Context context) {
        View rootView = mLayoutInflater.inflate(R.layout.dialog_select_image, null);
        ButterKnife.bind(this, rootView);
        setContentView(rootView);
    }

    /**
     * 相机
     */
    @OnClick(R.id.camera_tv)
    public void onCameraTvClicked() {
        dismiss();
        mPermissionUtils.requestPermission(PermissionCode.CAMERA_CODE, Manifest.permission.CAMERA);
    }

    /**
     * 相册
     */
    @OnClick(R.id.album_tv)
    public void onAlbumTvClicked() {
        dismiss();
        if (ObjectUtils.isEmpty(mFragment)) {
            openLocalImage((Activity) mContext);
        } else {
            openLocalImage(mFragment);
        }
    }

    /**
     * 权限处理
     */
    private PermissionUtils mPermissionUtils = new PermissionUtils() {
        @Override
        protected void onPermissionResult(boolean isSuccess, Permission permission, int requestCode) {
            if (requestCode == PermissionCode.CAMERA_CODE) {
                if (isSuccess) {
                    mPermissionUtils.requestPermission(PermissionCode.WRITE_EXTERNAL_STORAGE_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    ToastUtils.showShort("未获取相机权限!");
                }
            } else if (requestCode == PermissionCode.WRITE_EXTERNAL_STORAGE_CODE) {
                if (isSuccess) {
                    if (ObjectUtils.isEmpty(mFragment)) {
                        openCameraImage((FragmentActivity) mContext);
                    } else {
                        openCameraImage(mFragment);
                    }
                } else {
                    ToastUtils.showShort("未获取存储权限!");
                }
            }
        }
    };

    /**
     * 回调处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CommonCode.OPEN_CAMERA:
                //相机拍照结果
                initUCrop(mImageUriFromCamera);
                break;
            case CommonCode.OPEN_ALBUM:
                //相册选择结果
                initUCrop(data.getData());
                break;
            case UCrop.REQUEST_CROP:
                //UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    Uri output = UCrop.getOutput(data);
                    onSelectImageResult(true, output);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                    ToastUtils.showShort(cropError.getMessage());
                    onSelectImageResult(false, null);
                }
                break;
            case UCrop.RESULT_ERROR:
                //UCrop裁剪错误之后的处理
                Throwable cropError = UCrop.getError(data);
                ToastUtils.showShort(cropError.getMessage());
                onSelectImageResult(false, null);
                break;
            default:
                break;
        }
    }

    /**
     * 图片选择结果
     * 重写此方法，获取结果~
     *
     * @param isSuccess true:成功
     * @param uri
     */
    protected void onSelectImageResult(boolean isSuccess, Uri uri) {

    }

    /**
     * 打开相机
     *
     * @param activity
     */
    public void openCameraImage(FragmentActivity activity) {
        mImageUriFromCamera = createImagePathUri(activity);
        //系统常量，启动相机的关键
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
        // 返回图片在onActivityResult中通过以下代码获取
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUriFromCamera);
        activity.startActivityForResult(intent, CommonCode.OPEN_CAMERA);
    }

    /**
     * 打开相机
     *
     * @param fragment
     */
    public void openCameraImage(Fragment fragment) {
        mImageUriFromCamera = createImagePathUri(fragment.getContext());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
        // 返回图片在onActivityResult中通过以下代码获取
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUriFromCamera);
        fragment.startActivityForResult(intent, CommonCode.OPEN_CAMERA);
    }

    /**
     * 打开相册
     *
     * @param activity
     */
    public static void openLocalImage(final Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, CommonCode.OPEN_ALBUM);
    }

    /**
     * 打开相册
     *
     * @param fragment
     */
    public static void openLocalImage(final Fragment fragment) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        fragment.startActivityForResult(intent, CommonCode.OPEN_ALBUM);
    }

    /**
     * 创建一条图片地址uri
     * 用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImagePathUri(Context context) {
        final Uri[] imageFilePath = {null};
        //文件名
        long time = System.currentTimeMillis();
        String imageName = TimeUtil.dateFormat4(time);
        //ContentValues是我们希望这条记录被创建时包含的数据信息
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (SDCardUtils.hasSDCard()) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
            imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        Logger.t("CAMERA").d("生成的照片输出路径:%s", imageFilePath[0]);
        return imageFilePath[0];
    }

    /**
     * 裁剪
     *
     * @param uri
     */
    private void initUCrop(Uri uri) {
        String imageName = TimeUtil.dateFormat4(System.currentTimeMillis());
        Uri destinationUri = Uri.fromFile(new File(AppContext.getInstance().getCacheDir(), imageName + ".jpeg"));
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
//        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(mContext, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(mContext, R.color.colorPrimaryDark));
        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        options.setCircleDimmedLayer(true);
        //设置是否展示矩形裁剪框
        options.setShowCropFrame(false);
        // 设置裁剪框横竖线的宽度
        options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        options.setCropGridColumnCount(2);
        //设置横线的数量
        options.setCropGridRowCount(1);
        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)//长宽比
                .withMaxResultSize(1000, 1000)//图片大小
                .withOptions(options)
                .start((FragmentActivity) mContext);
    }
}
