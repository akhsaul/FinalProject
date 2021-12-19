package org.kelompok3;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kelompok3.core.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (Desktop.isDesktopSupported()) {
            try {
                var url = "http://127.0.0.1:8585";
                //Desktop.getDesktop().browse(new URI(url));
                //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url).waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<LittleHole> wholeList1 = new ArrayList<>();
        List<LittleHole> wholeList2 = new ArrayList<>();

        /*
        wholeList1.add(new LittleHole("C-7", 15));
        wholeList1.add(new LittleHole("C-6", 0));
        wholeList1.add(new LittleHole("C-5", 8));
        wholeList1.add(new LittleHole("C-4", 0));
        wholeList1.add(new LittleHole("C-3", 5));
        wholeList1.add(new LittleHole("C-2", 5));
        wholeList1.add(new LittleHole("C-1", 3));
        wholeList2.add(new LittleHole("P-7", 0));
        wholeList2.add(new LittleHole("P-6", 5));
        wholeList2.add(new LittleHole("P-5", 3));
        wholeList2.add(new LittleHole("P-4", 4));
        wholeList2.add(new LittleHole("P-3", 12));
        wholeList2.add(new LittleHole("P-2", 4));
        wholeList2.add(new LittleHole("P-1", 2));
         */

        /*
        for (int i = 7; i >= 1; i--) {
            wholeList1.add(new Whole("C-" + i, Whole.Type.COMPUTER));
            wholeList2.add(new Whole("P-" + i, Whole.Type.HUMAN));
        }
         */

        var bigWhole1 = new BigHole("C-L");
        var bigWhole2 = new BigHole("P-L");

        var objectMapper = new ObjectMapper();

        try {
            var data = objectMapper.writeValueAsString(wholeList1);
            System.out.println(data);
            List<LittleHole> list = objectMapper.readValue(data, new TypeReference<>() {});
            System.out.println(list);
        }catch (Exception e ){
            e.printStackTrace();
        }
        System.exit(0);

        new Computer(1, wholeList1, bigWhole1);
        // computer player
        var player1 = new Computer(1, wholeList1, bigWhole1);
        // human player
        var player2 = new Human(2, wholeList2, bigWhole2);

        player1.getSolution(player2.littleHole);

        /*
        List<Hole> nodes = new ArrayList<>();
        // include all littleWhole and bigWhole has by computer player
        nodes.addAll(player1.holeList());
        // exclude bigWhole has by human player
        nodes.addAll(player2.littleWhole);

        // start backtracking
        Solution computer = player1.getSolution(nodes);
        if(computer.hasSolution()){
            System.out.println("SOLUTION");
            System.out.println(computer.getHole());
        }*/
    }
}
