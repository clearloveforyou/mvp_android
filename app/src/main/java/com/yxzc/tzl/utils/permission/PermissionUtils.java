package com.yxzc.tzl.utils.permission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yxzc.tzl.utils.ObjectUtils;
import com.yxzc.tzl.utils.ToastUtils;

import io.reactivex.functions.Consumer;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.utils
 * @Author: HSL
 * @Time: 2018/10/10 09:47
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class PermissionUtils {

    private FragmentActivity mActivity;
    private RxPermissions mRxPermissions;

    public PermissionUtils() {
        // TODO: 2018/10/10
    }

    public void initRxPermission(FragmentActivity activity) {
        mActivity = activity;
        mRxPermissions = new RxPermissions(activity);
    }

    public void initRxPermission(Fragment fragment) {
        mActivity = fragment.getActivity();
        mRxPermissions = new RxPermissions(fragment);
    }

    /**
     * 请求权限
     *
     * @param permissions
     * @param requestCode
     */
    public void requestPermission(int requestCode, String... permissions) {
        if (ObjectUtils.isEmpty(mRxPermissions)) {
            ToastUtils.showShort("权限请求失败!");
            return;
        }
        mRxPermissions
                .requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        rxPermissionResult(permission, requestCode);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    private void rxPermissionResult(Permission permission, int requestCode) {
        if (permission.granted) {
            // TODO: 2018/10/11
            onPermissionResult(true, permission, requestCode);
        } else if (permission.shouldShowRequestPermissionRationale) {
            //权限被拒绝过一次,再次申请的解释说明
            showPermissionExplain(permission, requestCode);
        } else {
            //拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限
            openPermissionSetting(permission, requestCode);
        }
    }

    /**
     * 权限获取结果
     *
     * @param isSuccess
     * @param permission
     * @param requestCode
     */
    protected void onPermissionResult(boolean isSuccess, Permission permission, int requestCode) {

    }

    /**
     * @param permission
     * @param requestCode
     */
    private void showPermissionExplain(Permission permission, int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("提示");
        String permissionName = TransformUtils.transformText(mActivity, permission.name);
        String msg = String.format("请允许%s,否则将影响应用的使用", permissionName);
        builder.setMessage(msg);
        builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermission(requestCode, permission.name);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPermissionResult(false, permission, requestCode);
            }
        });
        builder.create().show();
    }

    /**
     * @param permission
     * @param requestCode
     */
    private void openPermissionSetting(Permission permission, int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("提示");
        String permissionName = TransformUtils.transformText(mActivity, permission.name);
        String msg = String.format("您已经拒绝%s,请前往应用设置界面打开权限", permissionName);
        builder.setMessage(msg);
        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OpenAppInfoUtils.openAppInfo(mActivity);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPermissionResult(false, permission, requestCode);
            }
        });
        builder.create().show();
    }

}
