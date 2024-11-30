package eu.smoothcloud.node.terminal;

import lombok.SneakyThrows;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class CloudTerminal {
    private final Terminal terminal;

    public CloudTerminal() {
        try {
            this.terminal = TerminalBuilder.terminal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void run() {
        while (true) {
            LineReader lineReader = LineReaderBuilder.builder()
                    .completer(new AggregateCompleter(new StringsCompleter("help", "shutdown")))
                    .terminal(this.terminal)
                    .build();
            switch (lineReader.readLine()) {
                case "clear" -> {
                    lineReader.callWidget(LineReader.CLEAR_SCREEN);
                    this.terminal.flush();
                }
                case "help" -> {
                    this.write("--------------------------------");
                    this.write(" help - Shows this window.");
                    this.write(" shutdown - Stopps the cloud.");
                    this.write("--------------------------------");
                }
                case "shutdown" -> {
                    this.write("Shutting down...");
                    System.exit(0);
                }
            }
        }
    }

    public void write(String message) {
        terminal.writer().println(message);
        terminal.writer().flush();
    }

    public void lockInput() {

    }

    public void unlockInput() {

    }
}
