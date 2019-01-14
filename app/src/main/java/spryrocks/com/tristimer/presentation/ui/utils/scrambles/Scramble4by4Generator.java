package spryrocks.com.tristimer.presentation.ui.utils.scrambles;

public class Scramble4by4Generator {
    private static final String[] faces = { "RX", "LX", "UY", "DY", "FZ", "BZ", "RwX", "UwY", "FwZ" };
    private static final String[] rotation = { "", "'", "2" };

    public static String generateScramble() {
        StringBuilder scramble = new StringBuilder();

        String penultimateFace = "  ";
        String lastFace = "  ";

        for(int i = 0; i < 40; i++) {
            String newFace = randomFace(penultimateFace, lastFace);
            scramble.append(newFace, 0, newFace.length() - 1).append(randomDirection()).append(" ");
            penultimateFace = lastFace;
            lastFace = newFace;
        }
        return scramble.toString();
    }

    private static String randomFace(String penultimate, String last) {
        String toReturn = faces[(int)(Math.random() * faces.length)];
        if(last.equals(toReturn) || sameAxis(penultimate, last, toReturn))
            return randomFace(penultimate, last);
        return toReturn;
    }

    private static boolean sameAxis(String a, String b, String c) {
        return a.charAt(a.length() - 1) == b.charAt(b.length() - 1) && b.charAt(b.length() - 1) == c.charAt(c.length() - 1);
    }

    private static String randomDirection() {
        return rotation[(int)(Math.random() * 3)];
    }
}
