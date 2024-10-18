package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    public static final double CONCERT_A = 440.0;
    public static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);

    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */

        GuitarString[] guitarStrings = new GuitarString[KEYBOARD.length()];
        for (int i = 0; i < guitarStrings.length; i++) {
            double concert = 440 *  Math.pow(2, (i - 24) / 12.0);
            guitarStrings[i] = new GuitarString(concert);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int idx = KEYBOARD.indexOf(key);
                if (idx != -1) {
                    guitarStrings[idx].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;  // 初始化为0
            for (int i = 0; i < guitarStrings.length; i++) {
                sample += guitarStrings[i].sample();  // 叠加每个弦的样本
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < guitarStrings.length; i++) {
                guitarStrings[i].tic();
            }
        }
    }
}
