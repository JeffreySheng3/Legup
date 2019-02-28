package edu.rpi.legup.aris;

import edu.rpi.aris.LibAris;
import edu.rpi.aris.assign.ArisClientModule;
import edu.rpi.aris.assign.ArisServerModule;
import edu.rpi.aris.assign.Problem;
import edu.rpi.aris.assign.ProblemConverter;
import edu.rpi.aris.assign.spi.ArisModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LegupModule implements ArisModule<LegupModule> {

    private final static Logger LOGGER = LogManager.getLogger(LegupModule.class.getName());

    private static LegupModule instance;

    private final String NAME = "Legup";

    private ArisClientModule<LegupModule> clientModule;
    private HashMap<String, String> assignProperties;
    private boolean loadedClient;

    public LegupModule() {
        this.assignProperties = new HashMap<>();
        this.loadedClient = false;
    }

    public static synchronized LegupModule getInstance() {
        if(instance == null) {
            instance = new LegupModule();
        }
        return instance;
    }


    /**
     * Gets the name of this module. This name should be unique among all other modules
     * and this should always return the same string for a given module regardless of version as the name is used to
     * uniquely identify this module. The name should also be the same on both the client and server side in the case
     * of a separate server and client module
     *
     * @return the module name
     */
    @Override
    public @NotNull String getModuleName() {
        return NAME;
    }

    /**
     * Gets the client side portion of this module ({@link ArisClientModule}). This can be null if the server side
     * implementation is separate from the client side implementation
     *
     * @return the {@link ArisClientModule} for this {@link ArisModule} or null if this {@link ArisModule} only contains
     * the server side implementation
     * @throws Exception for any error that may occur while trying to get the client module
     * @see ArisClientModule
     */
    @Override
    public @Nullable ArisClientModule<LegupModule> getClientModule() throws Exception {
        if (!loadedClient) {
            loadedClient = true;
            try {
                Class<?> clazz = Class.forName("edu.rpi.legup.aris.LegupClientModule");
                //noinspection unchecked
                clientModule = (ArisClientModule<LegupModule>) clazz.getMethod("getInstance").invoke(null);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOGGER.fatal("Failed get Legup client class", e);
                return clientModule;
            }
        }
        return clientModule;
    }

    /**
     * Gets the server side portion of this module ({@link ArisServerModule}). This can be null if the server side implementation is separate
     * from the client side implementation
     *
     * @return the {@link ArisServerModule} for this {@link ArisModule} or null if this {@link ArisModule} only contains
     * the client side implementation
     * @throws Exception for any error that may occur while trying to get the server module
     * @see ArisServerModule
     */
    @Override
    public @Nullable ArisServerModule<LegupModule> getServerModule() throws Exception {
        return null;
    }

    /**
     * Gets the {@link ProblemConverter} used to write a {@link Problem} to an {@link OutputStream}
     * and to read a {@link Problem} from an {@link InputStream}. It is recommended to always return
     * the same {@link ProblemConverter} instance to save on resources
     *
     * @return the {@link ProblemConverter} for this {@link ArisModule}
     * @throws Exception for any error that may occur while trying to get the {@link ProblemConverter}
     * @see ProblemConverter
     * @see Problem
     */
    @Override
    public @NotNull ProblemConverter<LegupModule> getProblemConverter() throws Exception {
        return null;
    }

    /**
     * A set of properties passed from the Assign client/server for the module to optionally use.
     * (Not yet implemented)
     *
     * @param properties the map of aris properties
     */
    @Override
    public void setArisProperties(@NotNull HashMap<String, String> properties) {
        this.assignProperties = properties;
    }

    /**
     * Gets an {@link InputStream} to read the icon for this {@link ArisModule}. This is here so Aris Assign can show your
     * program's icon within it's UI. This cannot be null
     *
     * @return An {@link InputStream} for the module icon
     * @throws Exception for any error that occurs while trying to load the module icon
     */
    @Override
    public @NotNull InputStream getModuleIcon() throws Exception {
        return LegupModule.class.getResourceAsStream("/edu/rpi/legup/images/Legup/Basic Rules.gif");
    }

    /**
     * Gets a list of file extensions supported by this {@link ArisModule}. These are used to know what files can be
     * imported as problems for this module. Note: the file extensions should NOT contain the dot (ie: txt, png, jpg)
     *
     * @return the list of file extensions supported by this {@link ArisModule}
     * @throws Exception for any error that may occur while trying to get the file extensions
     */
    @Override
    public @NotNull List<String> getProblemFileExtensions() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add(".legup");
        return list;
    }
}
