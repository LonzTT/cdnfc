import javax.smartcardio.*;
import java.util.List;

public class NfcScanner {

    public static void main(String[] args) {
        try {
            // Get the terminal factory
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            System.out.println("Available terminals: " + terminals);

            // Use the first terminal
            CardTerminal terminal = terminals.get(0);

            // Wait for card to be present
            terminal.waitForCardPresent(0);
            Card card = terminal.connect("*");
            System.out.println("Card detected: " + card);

            CardChannel channel = card.getBasicChannel();

            // Send a SELECT command
            CommandAPDU command = new CommandAPDU(new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x00});
            ResponseAPDU response = channel.transmit(command);
            System.out.println("Response: " + bytesToHex(response.getBytes()));

            // Perform operations on the card here
            // ...

            // Disconnect the card
            card.disconnect(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to convert bytes to hex
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
