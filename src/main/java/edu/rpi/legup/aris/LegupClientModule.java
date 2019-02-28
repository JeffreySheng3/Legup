package edu.rpi.legup.aris;

import edu.rpi.aris.assign.ArisClientModule;
import edu.rpi.aris.assign.ModuleUI;
import edu.rpi.aris.assign.ModuleUIOptions;
import edu.rpi.aris.assign.Problem;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LegupClientModule implements ArisClientModule<LegupModule> {

    private static LegupClientModule instance;

    public static synchronized LegupClientModule getInstance() {
        if(instance == null) {
            instance = new LegupClientModule();
        }
        return instance;
    }

    /**
     * Returns a {@link ModuleUI} for this {@link ArisModule} T with the given configuration options
     *
     * @param options the {@link ModuleUIOptions} for this module
     * @return the created {@link ModuleUI}
     * @throws Exception for any exception that occurs while creating the {@link ModuleUI}
     * @see ModuleUI
     * @see ModuleUIOptions
     */
    @Override
    public @NotNull ModuleUI<LegupModule> createModuleGui(@NotNull ModuleUIOptions options) throws Exception {
        return new LegupUI();
    }

    /**
     * Returns a {@link ModuleUI} for this {@link ArisModule} T with the given configuration options displaying the given
     * problem
     *
     * @param options the {@link ModuleUIOptions} for this module
     * @param problem the {@link Problem} to be displayed in the {@link ModuleUI}. If problem is null then this should act
     *                the same as {@link ArisClientModule#createModuleGui(ModuleUIOptions)}
     * @return the created {@link ModuleUI}
     * @throws Exception for any exception that occurs while creating the {@link ModuleUI}
     * @see ModuleUI
     * @see ModuleUIOptions
     * @see Problem
     */
    @Override
    public @NotNull ModuleUI<LegupModule> createModuleGui(@NotNull ModuleUIOptions options, @Nullable Problem<LegupModule> problem) throws Exception {
        return new LegupUI();
    }
}
