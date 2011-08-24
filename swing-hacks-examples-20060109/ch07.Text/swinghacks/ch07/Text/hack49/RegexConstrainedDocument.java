package swinghacks.ch07.Text.hack49;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class RegexConstrainedDocument extends PlainDocument {

	Pattern pattern;
	Matcher matcher;

	public RegexConstrainedDocument() {
		super();
	}

	public RegexConstrainedDocument(AbstractDocument.Content c) {
		super(c);
	}

	public RegexConstrainedDocument(AbstractDocument.Content c, String p) {
		super(c);
		setPatternByString(p);
	}

	public RegexConstrainedDocument(String p) {
		super();
		setPatternByString(p);
	}

	public void setPatternByString(String p) {
		Pattern pattern = Pattern.compile(p);
		// check the document against the new pattern
		// and removes the content if it no longer matches
		try {
			matcher = pattern.matcher(getText(0, getLength()));
			System.out.println("matcher reset to " + getText(0, getLength()));
			if (!matcher.matches()) {
				System.out.println("does not match");
				remove(0, getLength());
			}
		} catch (BadLocationException ble) {
			ble.printStackTrace(); // impossible?
		}
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
		// consider whether this insert will match
		String proposedInsert = getText(0, offs) + s + getText(offs, getLength() - offs);
		System.out.println("proposing to change to: " + proposedInsert);
		if (matcher != null) {
			matcher.reset(proposedInsert);
			System.out.println("matcher reset");
			if (!matcher.matches()) {
				System.out.println("insert doesn't match");
				return;
			}
		}
		super.insertString(offs, s, a);
	}

}
