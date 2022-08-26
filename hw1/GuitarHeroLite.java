import synthesizer.GuitarString;

/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHeroLite {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    private static GuitarString[] strings;
    private static String keyboard="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/'";

    public static void init(){
        strings = new GuitarString[keyboard.length()];
        for(int i=0;i<keyboard.length();i++){
            strings[i] = new GuitarString(440.0 * Math.pow(2.0,(i-24.0)/12.0));
            strings[i].tic();
        }

    }

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        //synthesizer.GuitarString stringA = new synthesizer.GuitarString(CONCERT_A);
        //synthesizer.GuitarString stringC = new synthesizer.GuitarString(CONCERT_C);
        init();
        while (true) {
            System.out.println("keep running");
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {

                char key = StdDraw.nextKeyTyped();
                System.out.printf("%s clicked\n",key);
                int index = keyboard.indexOf(key);
                if(index==-1){
                    continue;
                }
                strings[index].pluck();
            }
            double sample =0;
            for(int i=0;i<keyboard.length();i++){
                sample = sample+strings[i].sample();
                strings[i].tic();
            }
        /* compute the superposition of samples */
            //double sample = stringA.sample() + stringC.sample();

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            //stringA.tic();
            //stringC.tic();
        }
    }
}

