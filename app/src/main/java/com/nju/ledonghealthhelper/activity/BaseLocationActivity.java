package com.nju.ledonghealthhelper.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nju.ledonghealthhelper.util.LogUtils;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseLocationActivity extends BaseActivity  implements EasyPermissions.PermissionCallbacks {


    protected static LocationClient mLocationClient = null;
    private LDLocationListener ldLocationListener = new LDLocationListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getApplicationContext());
            mLocationClient.registerLocationListener(ldLocationListener);
            LocationClientOption option = new LocationClientOption();

            option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true

            option.setNeedNewVersionRgc(true);
//可选，设置是否需要最新版本的地址信息。默认需要，即参数为true

            mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        }
    }

    protected void startLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mLocationClient.start();
        } else {
            EasyPermissions.requestPermissions(this, "需要使用定位", 2, perms);
        }
    }

    protected void stopLocation() {
        mLocationClient.stop();
    }

    protected boolean isLocationStarted() {
        return mLocationClient.isStarted();
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
       mLocationClient.start();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getApplicationContext(),"拒绝位置权限",Toast.LENGTH_SHORT).show();
    }

    abstract void onReceiveLocation(BDLocation location);

    class LDLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            BaseLocationActivity.this.onReceiveLocation(location);
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String adcode = location.getAdCode();    //获取adcode
            String town = location.getTown();    //获取乡镇信息

            LogUtils.d("startLocation",province+" "+city+" "+district);
        }
    }
}
