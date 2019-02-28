package edu.rpi.legup.utility;

import edu.rpi.legup.app.GameBoardFacade;
import edu.rpi.legup.model.Puzzle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVWriter;


public class Gradable {

    private static final String[] blanks = {"C:\\Users\\jeffp\\Documents\\Legup Blank Solutions\\lightup1solution",
                                            "C:\\Users\\jeffp\\Documents\\Legup Blank Solutions\\lightup2solution",
                                            "C:\\Users\\jeffp\\Documents\\Legup Blank Solutions\\nurikabe1solution",
                                            "C:\\Users\\jeffp\\Documents\\Legup Blank Solutions\\nurikabe2solution"};
    private static final String studentDir = "C:\\Users\\jeffp\\Documents\\LEGUP Student Solutions";
//    private static final String studentDir = "C:\\Users\\jeffp\\Documents\\Test Solutions";
    private static final String outputFile = "C:\\Users\\jeffp\\Documents\\LegupGrades.csv";

    public static void main(String[] args) throws Exception {
        File[] blankFiles = new File[blanks.length];
        for(int i = 0; i < blanks.length; i++) {
            blankFiles[i] = new File(blanks[i]);
        }

        File studentDirFile = new File(studentDir);
        File[] students = studentDirFile.listFiles();

        String[] csvHeader = new String[blankFiles.length + 1];
        csvHeader[0] = "rcsid";
        for(int i = 1; i <= blankFiles.length; i++) {
            System.err.println(blankFiles[i - 1].getName());
            csvHeader[i] = blankFiles[i - 1].getName();
        }

        Puzzle[] puzzles = new Puzzle[blankFiles.length];
        for(int i = 0; i < puzzles.length; i++) {
            puzzles[i] = GameBoardFacade.getPuzzleFromStream(new FileInputStream(blanks[i]));
        }

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END)
        ) {

            csvWriter.writeNext(csvHeader);

            for(File student : students) {
                String[] entry = new String[blankFiles.length + 1];
                String rcsid = student.getName();

                entry[0] = rcsid;
                for(int i = 0; i < puzzles.length; i++) {
//                    System.err.println("Checking:" + blankFiles[i].getName());
                    boolean isCorrect = false;
                    for(File sPuzzleFile : student.listFiles()) {
                        try {
                            Puzzle studentPuzzle = GameBoardFacade.getPuzzleFromStream(new FileInputStream(sPuzzleFile));
//                            System.err.println(i + " : " + studentPuzzle.isPuzzleComplete());
                            if(puzzles[i].getTree().getRootNode().getBoard().equalsBoard(studentPuzzle.getTree().getRootNode().getBoard())) {
                                isCorrect = studentPuzzle.isPuzzleComplete();
//                                System.err.println("Boards equal");
                                System.err.println(blankFiles[i].getName() + " : " + isCorrect);

                                break;
                            }
                        } catch (Exception e) {

                        }
                    }
                    entry[i + 1] = isCorrect ? "1" : "0";
                }

                csvWriter.writeNext(entry);
            }

            csvWriter.flush();
        } catch (Exception e) {

        }
    }

}
