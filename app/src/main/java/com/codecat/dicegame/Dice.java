package com.codecat.dicegame;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class Dice {

    private ImageView imageView;

    private int[] faces;

    private int[] rollFrames;

    private Random random = new Random();

    private Handler handler = new Handler(Looper.getMainLooper());

    private int value = 1;

    private boolean isAnimating = false;


    public Dice(
            ImageView imageView,
            int[] faces,
            int[] rollFrames) {

        this.imageView = imageView;
        this.faces = faces;
        this.rollFrames = rollFrames;
    }


    public int getValue() {
        return value;
    }



    public void startAnimation() {

        if (isAnimating) {
            return;
        }

        isAnimating = true;

        animationStep();
    }


    private void animationStep() {

        if (!isAnimating) {
            return;
        }


        int frame = random.nextInt(rollFrames.length);

        imageView.setImageResource(rollFrames[frame]);


        float scale =
                0.8f + random.nextFloat() * 0.4f;

        imageView.setScaleX(scale);
        imageView.setScaleY(scale);


        float moveX =
                random.nextFloat() * 300f - 150f;

        float moveY =
                random.nextFloat() * 200f - 100f;

        imageView.setTranslationX(moveX);
        imageView.setTranslationY(moveY);



        float rotation =
                random.nextFloat() * 360f - 180f;

        imageView.setRotation(rotation);


        handler.postDelayed(animationRunnable, 70 );
    }


    private final Runnable animationRunnable =  new Runnable() {

        @Override
        public void run() {
            animationStep();
        }
    };


    public void stopAnimation(int finalValue) {

        // Зупиняємо подальші кадри
        isAnimating = false;

        // Видаляємо заплановані виклики animationStep()
        handler.removeCallbacks(animationRunnable);


        // Запам'ятовуємо результат
        value = finalValue;


        imageView.setImageResource(faces[value - 1]
        );


        imageView.animate()

                .translationX(random.nextInt(600) - 300)
                .translationY(random.nextInt(600) - 300)

                .scaleX(1f)
                .scaleY(1f)

                .rotation(0)

                /*
                 * Тривалість фінального
                 * "заспокоєння" кубика.
                 */
                .setDuration(300)

                .start();
    }



    public void reset() {

        isAnimating = false;

        handler.removeCallbacks(animationRunnable);

        value = 1;

        imageView.setImageResource(
                faces[0]
        );

        imageView.setTranslationX(0);
        imageView.setTranslationY(0);

        imageView.setScaleX(1);
        imageView.setScaleY(1);

        imageView.setRotation(0);
    }
}