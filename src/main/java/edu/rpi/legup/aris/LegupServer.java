package edu.rpi.legup.aris;

import edu.rpi.aris.assign.ArisServerModule;
import edu.rpi.aris.assign.AutoGrader;
import edu.rpi.aris.assign.spi.ArisModule;
import org.jetbrains.annotations.NotNull;

public class LegupServer implements ArisServerModule<LegupModule> {

    private static LegupServer instance;
    private final LegupGrader grader = new LegupGrader();

    private LegupServer() {
    }

    public static LegupServer getInstance() {
        if (instance == null)
            instance = new LegupServer();
        return instance;
    }
    /**
     * Returns the {@link AutoGrader} for this {@link ArisModule}
     *
     * @return the {@link AutoGrader}
     * @see AutoGrader
     */
    @Override
    public @NotNull AutoGrader<LegupModule> getAutoGrader() {
        return grader;
    }
}
