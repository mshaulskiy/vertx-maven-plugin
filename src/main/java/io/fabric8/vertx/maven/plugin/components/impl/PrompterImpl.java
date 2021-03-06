package io.fabric8.vertx.maven.plugin.components.impl;

import io.fabric8.vertx.maven.plugin.components.Prompter;
import jline.console.ConsoleReader;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.component.annotations.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Default {@link Prompter} implementation.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@Component(role = Prompter.class, instantiationStrategy = "per-lookup")
public class PrompterImpl implements Prompter {

    private final ConsoleReader console;

    public PrompterImpl() throws IOException {
        this.console = new ConsoleReader();
        console.setHistoryEnabled(false);
        console.setExpandEvents(false);
    }

    public PrompterImpl(InputStream in, OutputStream out) throws IOException {
        this.console = new ConsoleReader(in, out);
        console.setHistoryEnabled(false);
        console.setExpandEvents(false);
    }

    public ConsoleReader getConsole() {
        return console;
    }

    public String prompt(final String message, final Character mask) throws IOException {
        Objects.requireNonNull(message);

        final String prompt = String.format("%s: ", message);
        String value;
        do {
            value = console.readLine(prompt, mask);
        }
        while (StringUtils.isBlank(value));
        return value;
    }


    public String prompt(final String message) throws IOException {
        Objects.requireNonNull(message);
        return prompt(message, null);
    }

    public String promptWithDefaultValue(final String message, final String defaultValue) throws IOException {
        Objects.requireNonNull(message);
        Objects.requireNonNull(defaultValue);

        final String prompt = String.format("%s [%s]: ", message, defaultValue);
        String value = console.readLine(prompt);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

}
