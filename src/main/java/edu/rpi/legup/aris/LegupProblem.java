package edu.rpi.legup.aris;

import edu.rpi.aris.assign.Problem;
import edu.rpi.legup.model.Puzzle;

public class LegupProblem implements Problem<LegupModule> {

    private Puzzle puzzle;

    public LegupProblem(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }
}
