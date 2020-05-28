package com.github.myproject.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.github.myproject.R;

import java.util.List;

public class OnBoardingAdapter extends PagerAdapter {
    Context context;
    List<OnBoardingItem> onBoardingItemList;
    Animation onBoardingAnimation;
    Animation onBoardingTitleAnimation;
    Animation onBoardingDescAnimation;

    public OnBoardingAdapter(Context context, List<OnBoardingItem> onBoardingItemList) {
        this.context = context;
        this.onBoardingItemList = onBoardingItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.on_boarding_design, null);
        ImageView onBoardingImage = layoutScreen.findViewById(R.id.on_boarding_image);
        TextView onBoardingTitle = layoutScreen.findViewById(R.id.on_boarding_title);
        TextView onBoardingDescription = layoutScreen.findViewById(R.id.on_boarding_description);
        ConstraintLayout onBoardingLayout = layoutScreen.findViewById(R.id.on_boarding_layout);
        onBoardingAnimation = AnimationUtils.loadAnimation(context, R.anim.on_boarding_image);
        onBoardingTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.on_boarding_title);
        onBoardingDescAnimation = AnimationUtils.loadAnimation(context, R.anim.on_boarding_description);
        if (position == 0) {
            onBoardingTitle.setAnimation(onBoardingTitleAnimation);
            onBoardingDescription.setAnimation(onBoardingDescAnimation);
            onBoardingImage.setAnimation(onBoardingAnimation);
        }
        onBoardingTitle.setText(onBoardingItemList.get(position).getOnBoardingTitle());
        onBoardingDescription.setText(onBoardingItemList.get(position).getOnBoardingDescription());
        onBoardingImage.setImageResource(onBoardingItemList.get(position).getOnBoardingImage());
        onBoardingLayout.setBackgroundResource(onBoardingItemList.get(position).getOnBoardingBackground());

        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return onBoardingItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
