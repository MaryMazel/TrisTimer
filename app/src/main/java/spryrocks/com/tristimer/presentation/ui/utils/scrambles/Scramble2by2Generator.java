package spryrocks.com.tristimer.presentation.ui.utils.scrambles;

public class Scramble2by2Generator {
    private static final String[] faces = { "RX", "UY", "FZ" };
    private static final String[] rotation = { "", "'", "2" };

    public static String generateScramble() {
        String scramble = "";

        String penultimateFace = "  ";
        String lastFace = "  ";

        for(int i = 0; i < 10; i++) {
            String newFace = randomFace(penultimateFace, lastFace);
            scramble += newFace.charAt(0) + randomDirection() + " ";
            penultimateFace = lastFace;
            lastFace = newFace;
        }
        return scramble;
    }

    private static String randomFace(String penultimate, String last) {
        String toReturn = faces[(int)(Math.random() * faces.length)];
        if(last.equals(toReturn) || sameAxis(penultimate, last, toReturn))
            return randomFace(penultimate, last);
        return toReturn;
    }

    private static boolean sameAxis(String a, String b, String c) {
        return a.charAt(1) == b.charAt(1) && b.charAt(1) == c.charAt(1);
    }

    private static String randomDirection() {
        return rotation[(int)(Math.random() * 3)];
    }
}
