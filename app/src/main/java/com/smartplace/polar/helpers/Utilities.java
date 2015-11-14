package com.smartplace.polar.helpers;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by robertoreym on 17/10/15.
 */
public class Utilities {

    public static String getRequirementTypeByID(int type){

        String requirementType = null;

        switch (type){

            case Constants.TYPE_REQUIREMENT:
                requirementType = "Requerimiento";
                break;

            case Constants.TYPE_HEADER:
                requirementType = "Titulo";
                break;
            case Constants.TYPE_SUBHEADER:
                requirementType = "Subtitulo";
                break;
            case Constants.TYPE_IMAGE:
                requirementType = "Imagen";
                break;
            case Constants.TYPE_LINK:
                requirementType = "Url";
                break;

            default:
                requirementType = "Espacio";
                break;
        }

        return requirementType;
    }

    public static int getRequirementTypeByName(String name){

        if(name.equals("Requerimiento")){

            return Constants.TYPE_REQUIREMENT;

        }else if(name.equals("Titulo")){

            return Constants.TYPE_HEADER;

        }else if(name.equals("Subtitulo")){

            return Constants.TYPE_SUBHEADER;

        }else if(name.equals("Imagen")){

            return Constants.TYPE_IMAGE;

        }else if(name.equals("Url")){

            return Constants.TYPE_LINK;
        }else {

            return Constants.TYPE_SPACE;
        }
    }
    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
