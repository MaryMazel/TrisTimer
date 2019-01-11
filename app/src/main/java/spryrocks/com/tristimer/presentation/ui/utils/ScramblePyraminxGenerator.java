package spryrocks.com.tristimer.presentation.ui.utils;

import java.util.ArrayList;
import java.util.List;

public class ScramblePyraminxGenerator {
    private static final String[] faces = { "RX", "LX", "UY", "BZ" };
    private static final String[] facesLast = { "r", "l", "b", "u" };
    private static final String[] rotation = { "", "'" };

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
        return scramble + lastFaces();
    }

    private static String lastFaces() {
        StringBuilder scramble = new StringBuilder();

        int number = (int) (Math.random() * 5);
        if (number == 0) {
            return "";
        } else {
            List<Integer> faces = new ArrayList<>();
            while (faces.size() < number) {
                int i = (int) (Math.random() * 4);
                if (!faces.contains(i)) {
                    faces.add(i);
                }
            }
            for (Integer face : faces) {
                int i = Integer.parseInt(face.toString());
                scramble.append(facesLast[i]).append(randomDirection()).append(" ");
            }
            return scramble.toString();
        }
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
        return rotation[(int)(Math.random() * 2)];
    }
}
