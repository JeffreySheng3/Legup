package edu.rpi.legup;

import edu.rpi.aris.assign.ArisClientModule;
import edu.rpi.legup.app.Config;
import edu.rpi.legup.app.GameBoardFacade;
import edu.rpi.legup.app.InvalidConfigException;

import edu.rpi.legup.aris.LegupModule;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;

public class Legup {
    static {
        String logPath = new File(Legup.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParent();
        logPath += logPath.endsWith(File.separator) ? "" : File.separator;
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        ConsoleAppender consoleAppender = config.getAppender("console");
        PatternLayout consolePattern = (PatternLayout) consoleAppender.getLayout();
        TimeBasedTriggeringPolicy triggeringPolicy = TimeBasedTriggeringPolicy.newBuilder().withInterval(1).withModulate(true).build();
        PatternLayout patternLayout = PatternLayout.newBuilder().withPattern(consolePattern.getConversionPattern()).build();
        RollingFileAppender rollingFileAppender = RollingFileAppender.newBuilder()
                .withName("fileLogger")
                .withFileName(logPath + "legup.log")
                .withFilePattern(logPath + "legup-%d{yyyy-MM-dd}.log.gz")
                .withPolicy(triggeringPolicy)
                .withLayout(patternLayout)
                .setConfiguration(config)
                .build();
        rollingFileAppender.start();
        config.addAppender(rollingFileAppender);
        LoggerConfig rootLogger = config.getRootLogger();
        rootLogger.addAppender(config.getAppender("fileLogger"), null, null);
        context.updateLoggers();

        System.setProperty("sun.java2d.noddraw", Boolean.TRUE.toString());
    }

    /**
     * Starts the Legup Program
     *
     * @param args arguments to Legup
     */
    public static void main(String[] args) {
//        System.out.println(LegupModule.class.getResourceAsStream("/edu/rpi/legup/images/Legup/Basic Rules.gif"));
        GameBoardFacade.getInstance();
        setConfig();
    }

    private static void setConfig() {
        Config config = null;
        try {
            config = new Config();
        } catch (InvalidConfigException e) {
            System.exit(1);
        }
        GameBoardFacade.getInstance().setConfig(config);
    }

    private static void parseCommandLine(String[] args) {
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Legup", options);

            System.exit(1);
        }

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");

        System.out.println(inputFilePath);
        System.out.println(outputFilePath);
    }
}