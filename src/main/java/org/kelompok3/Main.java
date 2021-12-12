package org.kelompok3;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if(Desktop.isDesktopSupported()){
            try {
                var url = "http://127.0.0.1:8585";
                //Desktop.getDesktop().browse(new URI(url));
                //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url).waitFor();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        List<Whole> wholeList1 = new ArrayList<>();
        List<Whole> wholeList2 = new ArrayList<>();

        wholeList1.add(new Whole("C-7", Whole.Type.COMPUTER, 15));
        wholeList1.add(new Whole("C-6", Whole.Type.COMPUTER, 0));
        wholeList1.add(new Whole("C-5", Whole.Type.COMPUTER, 8));
        wholeList1.add(new Whole("C-4", Whole.Type.COMPUTER, 0));
        wholeList1.add(new Whole("C-3", Whole.Type.COMPUTER, 5));
        wholeList1.add(new Whole("C-2", Whole.Type.COMPUTER, 5));
        wholeList1.add(new Whole("C-1", Whole.Type.COMPUTER, 3));
        wholeList2.add(new Whole("P-7", Whole.Type.HUMAN, 0));
        wholeList2.add(new Whole("P-6", Whole.Type.HUMAN, 5));
        wholeList2.add(new Whole("P-5", Whole.Type.HUMAN, 3));
        wholeList2.add(new Whole("P-4", Whole.Type.HUMAN, 4));
        wholeList2.add(new Whole("P-3", Whole.Type.HUMAN, 12));
        wholeList2.add(new Whole("P-2", Whole.Type.HUMAN, 4));
        wholeList2.add(new Whole("P-1", Whole.Type.HUMAN, 2));

        /*
        for (int i = 7; i >= 1; i--) {
            wholeList1.add(new Whole("C-" + i, Whole.Type.COMPUTER));
            wholeList2.add(new Whole("P-" + i, Whole.Type.HUMAN));
        }
         */

        var bigWhole1 = new Whole("C-L", Whole.Type.COMPUTER, 32, Whole.Size.BIG);
        var bigWhole2 = new Whole("P-L", Whole.Type.HUMAN, 0, Whole.Size.BIG);

        // computer player
        var player1 = new Player(1, wholeList1, bigWhole1);
        // human player
        var player2 = new Player(2, wholeList2, bigWhole2);

        List<Whole> nodes = new ArrayList<>();
        // include all littleWhole and bigWhole has by computer player
        nodes.addAll(player1.wholeList);
        // exclude bigWhole has by human player
        nodes.addAll(player2.littleWhole);

        // start backtracking
        var b = new Bot();
        b.backtracking(nodes);
        if(b.solution.hasSolution()){
            System.out.println("SOLUTION");
            System.out.println(b.solution.getWhole());
        }
    }
}
