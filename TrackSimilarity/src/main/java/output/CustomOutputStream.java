package output;

import java.io.IOException;
import java.io.OutputStream;

import javafx.scene.control.TextArea;

@SuppressWarnings("restriction")
public class CustomOutputStream extends OutputStream {

	private TextArea consoleTxtArea;

	public CustomOutputStream(TextArea textArea) {
		this.consoleTxtArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		this.consoleTxtArea.appendText(String.valueOf((char) b));

		this.consoleTxtArea.selectPositionCaret(this.consoleTxtArea.getLength());
	}

}
