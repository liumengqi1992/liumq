package com.deepblue.weather

import android.content.Context
import android.util.Log
import com.amap.api.location.AMapLocation.LOCATION_SUCCESS
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.search.Search
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.view.HeConfig
import interfaces.heweather.com.interfacesmodule.view.HeWeather

object Weather {
    //和风天气注册
    private const val userName = "HE1911061044411980"
    private const val key = "3eb7ef007bf44e5daf34b1930f2fad4d"
    //经纬度
    private var now_longitude: Double = 0.0
    private var now_latitude: Double = 0.0

    private val weatherIconMap = mapOf(
        "100" to R.mipmap.sunny_100,
        "101" to R.mipmap.cloudy_101,
        "102" to R.mipmap.few_clouds_102,
        "103" to R.mipmap.partly_couldy_103,
        "104" to R.mipmap.overcast_104,
        "200" to R.mipmap.windy_200,
        "201" to R.mipmap.calm_201,
        "202" to R.mipmap.light_breeze_202,
        "203" to R.mipmap.moderate_203,
        "204" to R.mipmap.fresh_breeze_204,
        "205" to R.mipmap.strong_breeze_205,
        "206" to R.mipmap.high_wind_206,
        "207" to R.mipmap.gale_207,
        "208" to R.mipmap.strong_gale_208,
        "209" to R.mipmap.storm_209,
        "210" to R.mipmap.violent_storm_210,
        "211" to R.mipmap.hurricane_211,
        "212" to R.mipmap.tornado_212,
        "213" to R.mipmap.tropical_storm_213,
        "300" to R.mipmap.shower_rain_300,
        "301" to R.mipmap.heavy_shower_rain_301,
        "302" to R.mipmap.thundershower_302,
        "303" to R.mipmap.heavy_thunderstorm_303,
        "304" to R.mipmap.thundersshower_with_hail_304,
        "305" to R.mipmap.light_rain_305,
        "306" to R.mipmap.moderate_rain_306,
        "307" to R.mipmap.heavy_rain_307,
        "309" to R.mipmap.drizzle_rain_309,
        "310" to R.mipmap.storm_310,
        "311" to R.mipmap.heavy_storm_311,
        "312" to R.mipmap.severe_storm_312,
        "313" to R.mipmap.freezing_rain_313,
        "314" to R.mipmap.light_to_moderate_rain_314,
        "315" to R.mipmap.moderate_to_heavy_rain_315,
        "316" to R.mipmap.heavy_rain_to_storm_316,
        "317" to R.mipmap.storm_to_heavy_storm_317,
        "318" to R.mipmap.heavy_to_severe_storm_318,
        "399" to R.mipmap.rain_399,
        "400" to R.mipmap.light_snow_400,
        "401" to R.mipmap.moderate_snow_401,
        "402" to R.mipmap.heavy_snow_402,
        "403" to R.mipmap.snowstorm_403,
        "404" to R.mipmap.sleet_404,
        "405" to R.mipmap.rain_and_snow_405,
        "406" to R.mipmap.shower_snow_406,
        "407" to R.mipmap.snow_flurry_407,
        "408" to R.mipmap.light_to_moderate_snow_408,
        "409" to R.mipmap.light_to_moderate_snow_409,
        "410" to R.mipmap.heavy_snow_to_snowstorm_410,
        "499" to R.mipmap.snow_499,
        "500" to R.mipmap.mist_500,
        "501" to R.mipmap.foggy_501,
        "502" to R.mipmap.haze_502,
        "503" to R.mipmap.sand_503,
        "504" to R.mipmap.dust_504,
        "507" to R.mipmap.duststorm_507,
        "508" to R.mipmap.sandstorm_508,
        "509" to R.mipmap.dense_fog_509,
        "510" to R.mipmap.strong_fog_510,
        "511" to R.mipmap.moderate_haze_511,
        "512" to R.mipmap.heavy_haze_512,
        "513" to R.mipmap.severe_haze_513,
        "514" to R.mipmap.heavy_fog_514,
        "515" to R.mipmap.extra_heavy_fog_515,
        "900" to R.mipmap.hot_900,
        "901" to R.mipmap.cold_901,
        "999" to R.mipmap.unknown_999
    )

    /**
     * 初始化 和风天气 SDK
     */
    fun initHeWeather() {
        //初始化和风天气，传入用户名和key
        HeConfig.init(userName, key)
        //切换为免费域名
        HeConfig.switchToFreeServerNode()
    }

    /**
     * 开始使用高德地图sdk 定位
     *
     */
    fun startGaoDeLocating(
        context: Context,
        onLocationResultListener: OnLocationResultListener,
        onWeatherResultListener: OnWeatherResultListener, onCityResultListener: OnCityResultListener
    ) {
        //声明AMapLocationClient类对象
        val mLocationClient = AMapLocationClient(context)
        //声明AMapLocationClientOption对象
        val mLocationOption = AMapLocationClientOption()
        //设置定位模式为 高精度模式。
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.interval = 10000
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.httpTimeOut = 20000
        //声明定位回调监听器
        mLocationClient.setLocationListener(AMapLocationListener { aMapLocation ->
            if (aMapLocation.errorCode == LOCATION_SUCCESS) {
                now_longitude = aMapLocation.longitude
                now_latitude = aMapLocation.latitude
                //经纬度定位成功
                onLocationResultListener.getLocationResultSuccess(now_longitude, now_latitude)
                getNowCity(context, onWeatherResultListener, onCityResultListener)
            } else {
                //定位失败
                //TODO
                Log.d(
                    "Weather",
                    "error code = ${aMapLocation.errorCode} , error info = ${aMapLocation.errorInfo}"
                )
                //定位失败
                onWeatherResultListener.getWeatherResultFailure()
            }
            mLocationClient.onDestroy()
        })

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient.startLocation()
    }

    /**
     * 根据经纬度获取所在城市
     *
     */
    private fun getNowCity(
        context: Context,
        onWeatherResultListener: OnWeatherResultListener,
        onCityResultListener: OnCityResultListener
    ) {
        HeWeather.getSearch(context, "$now_longitude,$now_latitude", "cn",
            3,
            Lang.CHINESE_SIMPLIFIED,
            object : HeWeather.OnResultSearchBeansListener {
                override fun onError(throwable: Throwable) {
                    onWeatherResultListener.getWeatherResultFailure()
                }

                override fun onSuccess(search: Search) {
                    if (null != search.basic && search.basic.size > 0) {
                        val basic = search.basic[0]
                        getWeatherCond(context, basic.cid, onWeatherResultListener)
                        onCityResultListener.getCityResultSuccess(basic.parent_city, basic.cid)
                    } else {
                        onWeatherResultListener.getWeatherResultFailure()
                    }
                }
            })
    }

    /**
     * 获取天气信息
     *
     */
    private fun getWeatherCond(
        context: Context,
        cid: String,
        onWeatherResultListener: OnWeatherResultListener
    ) {
        HeWeather.getWeatherNow(
            context,
            cid,
            object : HeWeather.OnResultWeatherNowBeanListener {
                override fun onError(throwable: Throwable) {
                    onWeatherResultListener.getWeatherResultFailure()
                }

                override fun onSuccess(data: Now) {
                    //温度，天气
                    onWeatherResultListener.getWeatherResultSuccess(
                        data.now.tmp,
                        weatherIconMap[data.now.cond_code]
                    )
                }
            })
    }

    /**
     * 查询天气结果的回调接口
     *
     */
    interface OnWeatherResultListener {

        fun getWeatherResultSuccess(tmp: String, iconId: Int?)

        fun getWeatherResultFailure()
    }

    /**
     * 查询经纬度结果的回调接口
     *
     */
    interface OnLocationResultListener {

        fun getLocationResultSuccess(longitude: Double, latitude: Double)
    }

    /**
     * 根据经纬度查询城市结果的回调接口
     *
     */
    interface OnCityResultListener {

        fun getCityResultSuccess(cityName: String, cid: String)
    }
}