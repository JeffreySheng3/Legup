package edu.rpi.legup.aris;

import edu.rpi.aris.assign.ModuleUI;
import edu.rpi.aris.assign.ModuleUIListener;
import edu.rpi.aris.assign.Problem;
import edu.rpi.legup.Legup;
import edu.rpi.legup.app.GameBoardFacade;
import javafx.stage.Modality;
import javafx.stage.Window;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LegupUI implements ModuleUI<LegupModule> {
    @Override
    public void show() throws Exception {
        GameBoardFacade.getInstance();
    }

    @Override
    public void hide() throws Exception {

    }

    @Override
    public void setModal(@NotNull Modality modality, @NotNull Window owner) throws Exception {

    }

    @Override
    public void setDescription(@NotNull String description) throws Exception {

    }

    @Override
    public void setModuleUIListener(@NotNull ModuleUIListener listener) {

    }

    @Override
    public @Nullable Window getUIWindow() {
        return null;
    }

    @Override
    public @NotNull Problem<LegupModule> getProblem() throws Exception {
        return new LegupProblem(GameBoardFacade.getInstance().getPuzzleModule());
    }
}
