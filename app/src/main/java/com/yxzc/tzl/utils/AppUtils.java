package com.yxzc.tzl.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.yxzc.tzl.application.AppContext;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * App 相关
 */
public final class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断 App 是否安装
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(@NonNull final String packageName) {
        return !isSpace(packageName)
                && AppContext.getInstance().getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }

    /**
     * 判断 App 是否安装.
     *
     * @param action   The Intent action, such as ACTION_VIEW.
     * @param category The desired category.
     * @return
     */
    public static boolean isAppInstalled(@NonNull final String action,
                                         @NonNull final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = AppContext.getInstance().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判断 App 是否有 root 权限
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("echo root", true);
        if (result.result == 0) return true;
        if (result.errorMsg != null) {
            Log.d("AppUtils", "isAppRoot() called" + result.errorMsg);
        }
        return false;
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug() {
        return isAppDebug(AppContext.getInstance().getPackageName());
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem() {
        return isAppSystem(AppContext.getInstance().getPackageName());
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 打开 App
     *
     * @param packageName The name of the package.
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) return;
        AppContext.getInstance().startActivity(getLaunchAppIntent(packageName, true));
    }

    /**
     * 打开 App
     *
     * @param activity    The activity.
     * @param packageName The name of the package.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void launchApp(final Activity activity,
                                 final String packageName,
                                 final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 重启 App
     */
    public static void relaunchApp() {
        PackageManager packageManager = AppContext.getInstance().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(AppContext.getInstance().getPackageName());
        if (intent == null) return;
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        AppContext.getInstance().startActivity(mainIntent);
        System.exit(0);
    }

    /**
     * 打开 App 具体设置
     */
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(AppContext.getInstance().getPackageName());
    }

    /**
     * 打开 App 具体设置
     *
     * @param packageName The name of the package.
     */
    public static void launchAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) return;
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        AppContext.getInstance().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 关闭应用
     */
    public static void exitApp() {
        List<Activity> activityList = AppContext.getInstance().getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {// remove from top
            Activity activity = activityList.get(i);
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish();
        }
        System.exit(0);
    }

    /**
     * 获取应用Icon
     *
     * @return the application's icon
     */
    public static Drawable getAppIcon() {
        return getAppIcon(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取应用Icon
     *
     * @param packageName The name of the package.
     * @return the application's icon
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 包名
     *
     * @return the application's package name
     */
    public static String getAppPackageName() {
        return AppContext.getInstance().getPackageName();
    }

    /**
     * 获取 App 名称
     *
     * @return the application's name
     */
    public static String getAppName() {
        return getAppName(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取 App 名称
     *
     * @param packageName The name of the package.
     * @return the application's name
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取 App 路径
     *
     * @return the application's path
     */
    public static String getAppPath() {
        return getAppPath(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取 App 路径
     *
     * @param packageName The name of the package.
     * @return the application's path
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取 App 版本号
     *
     * @return the application's version name
     */
    public static String getAppVersionName() {
        return getAppVersionName(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取 App 版本号
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取 App 版本码
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取 App 版本码
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取 App 签名
     *
     * @return the application's signature
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取 App 签名
     *
     * @param packageName The name of the package.
     * @return the application's signature
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名的的 SHA1 值
     *
     * @return the application's signature for SHA1 value
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取应用签名的的 SHA1 值
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA1 value
     */
    public static String getAppSignatureSHA1(final String packageName) {
        if (isSpace(packageName)) return "";
        Signature[] signature = getAppSignature(packageName);
        if (signature == null || signature.length <= 0) return "";
        return encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 获取 App 信息
     *
     * @return the application's information
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(AppContext.getInstance().getPackageName());
    }

    /**
     * 获取 App 信息
     *
     * @param packageName The name of the package.
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = AppContext.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * The application's information.
     */
    public static class AppInfo {

        private String packageName;
        private String name;
        private Drawable icon;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp icon: " + getIcon() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    private static final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String encryptSHA1ToString(final byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    private static byte[] encryptSHA1(final byte[] data) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    private static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = AppContext.getInstance().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) return null;
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }
}