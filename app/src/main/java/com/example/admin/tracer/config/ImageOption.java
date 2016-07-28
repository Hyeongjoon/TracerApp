package com.example.admin.tracer.config;

import android.graphics.Bitmap;

import com.example.admin.tracer.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by admin on 2016-07-28.
 */
public class ImageOption {
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.image_loding) // 로딩중 이미지 설정
            .showImageForEmptyUri(R.drawable.image_err_url) // Uri주소가 잘못되었을경우(이미지없을때)
            .showImageOnFail(R.drawable.image_fail) // 로딩 실패시
            .resetViewBeforeLoading(false)  // 로딩전에 뷰를 리셋하는건데 false로 하세요 과부하!
            .delayBeforeLoading(1000) // 로딩전 딜레이라는데 필요한일이 있을까요..?ㅋㅋ
            .cacheInMemory(false) // 메모리케시 사용여부
            .cacheOnDisc(true) // 디스크캐쉬를 사용여부(사용하세요왠만하면)
            .considerExifParams(false) // 사진이미지의 회전률 고려할건지
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // 스케일타입설정
            .bitmapConfig(Bitmap.Config.ARGB_8888) // 이미지 컬러방식
            .build();
}
