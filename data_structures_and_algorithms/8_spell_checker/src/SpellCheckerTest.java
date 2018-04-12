package hw8;

import static org.junit.Assert.*;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

public class SpellCheckerTest {

    private ISpellChecker spellChecker;

    @Before
    public void setUp() throws Exception {
        spellChecker = new SpellChecker();

        String data = "about\nabove\nabsolutely\nacceptable\nadd\nadjacent\nafter\nalgorithm\nall\nalong\nalso\nan";

        spellChecker.readDictionary(new StringReader(data));
    }

    @Test
    public void testCorrectWord() {
        String[] variants = spellChecker.checkWord("acceptable");
        assertNull(variants);
    }

    @Test
    public void testMisspelledWord() {
        String[] expected = { "above" };
        String[] variants = spellChecker.checkWord("abuve");
        assertArrayEquals(variants, expected);
    }

    @Test
    public void testMisspelledWordWithNoVariant() {
        String[] expected = {};
        String[] variants = spellChecker.checkWord("bbbbb");
        assertArrayEquals(variants, expected);
    }

}