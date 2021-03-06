package com.honeycrisp.fave.ui.recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.honeycrisp.fave.util.AnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link RecyclerView.ItemAnimator} that fades & slides newly added items in from a given
 * direction.
 *
 * Created by Ryan Taylor on 6/7/16.
 */
public class SlideInItemAnimator extends DefaultItemAnimator {

    static final String TAG = SlideInItemAnimator.class.getSimpleName();
    private final List<RecyclerView.ViewHolder> pendingAdds = new ArrayList<>();
    private final int slideFromEdge;

    /**
     * Default to sliding in upward.
     */
    public SlideInItemAnimator() {
        this(Gravity.BOTTOM, -1); // undefined layout dir; bottom isn't relative
    }

    public SlideInItemAnimator(int slideFromEdge, int layoutDirection) {
        this.slideFromEdge = Gravity.getAbsoluteGravity(slideFromEdge, layoutDirection);
        setAddDuration(160L);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0f);
        switch (slideFromEdge) {
            case Gravity.START:
                holder.itemView.setTranslationX(-holder.itemView.getWidth() / 3.0f);
                break;
            case Gravity.TOP:
                holder.itemView.setTranslationY(-holder.itemView.getHeight() / 3.0f);
                break;
            case Gravity.END:
                holder.itemView.setTranslationX(holder.itemView.getWidth() / 3.0f);
                break;
            case Gravity.BOTTOM:
                holder.itemView.setTranslationY(holder.itemView.getHeight() / 3.0f);
            default:
                // This should never happen
                Log.e(TAG, "Unknown gravity value.");
        }
        pendingAdds.add(holder);
        return true;
    }

    @Override
    public void runPendingAnimations() {
        super.runPendingAnimations();
        if (!pendingAdds.isEmpty()) {
            for (int i = pendingAdds.size() - 1; i >= 0; i--) {
                final RecyclerView.ViewHolder holder = pendingAdds.get(i);
                holder.itemView.animate()
                        .alpha(1f)
                        .translationX(0f)
                        .translationY(0f)
                        .setDuration(getAddDuration())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                dispatchAddStarting(holder);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animation.getListeners().remove(this);
                                dispatchAddFinished(holder);
                                dispatchFinishedWhenDone();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                clearAnimatedValues(holder.itemView);
                            }
                        })
                        .setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(
                                holder.itemView.getContext()));
                pendingAdds.remove(i);
            }
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder holder) {
        holder.itemView.animate().cancel();
        if (pendingAdds.remove(holder)) {
            dispatchAddFinished(holder);
            clearAnimatedValues(holder.itemView);
        }
        super.endAnimation(holder);
    }

    @Override
    public void endAnimations() {
        for (int i = pendingAdds.size() - 1; i >= 0; i--) {
            final RecyclerView.ViewHolder holder = pendingAdds.get(i);
            clearAnimatedValues(holder.itemView);
            dispatchAddFinished(holder);
            pendingAdds.remove(i);
        }
        super.endAnimations();
    }

    @Override
    public boolean isRunning() {
        return !pendingAdds.isEmpty() || super.isRunning();
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    private void clearAnimatedValues(final View view) {
        view.setAlpha(1f);
        view.setTranslationX(0f);
        view.setTranslationY(0f);
    }
}