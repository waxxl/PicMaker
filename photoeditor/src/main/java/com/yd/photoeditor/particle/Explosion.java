package com.yd.photoeditor.particle;

import android.content.Context;
import android.graphics.Canvas;

public class Explosion {
    public static final int ALIVE = 0;
    public static final int DEAD = 1;
    private static final int LIFETIME = 50;
    private static final int MAX_SCALE = 4;
    private static final int MAX_SPEED = 30;
    private Particle[] mParticles;
    private int mState = 0;

    public Explosion(int i, int i2, int i3, Context context) {
        this.mParticles = new Particle[i];
        for (int i4 = 0; i4 < this.mParticles.length; i4++) {
            this.mParticles[i4] = new Particle(i2, i3, 50, 30, 4, context);
        }
    }

    public boolean isDead() {
        return this.mState == 1;
    }

    public void update(Canvas canvas) {
        if (this.mState != 1) {
            int i = 0;
            boolean z = true;
            while (true) {
                Particle[] particleArr = this.mParticles;
                if (i >= particleArr.length) {
                    break;
                }
                if (particleArr[i].isAlive()) {
                    this.mParticles[i].update();
                    z = false;
                }
                i++;
            }
            if (z) {
                this.mState = 1;
            }
        }
        draw(canvas);
    }

    public void draw(Canvas canvas) {
        int i = 0;
        while (true) {
            Particle[] particleArr = this.mParticles;
            if (i < particleArr.length) {
                if (particleArr[i].isAlive()) {
                    this.mParticles[i].draw(canvas);
                }
                i++;
            } else {
                return;
            }
        }
    }
}
