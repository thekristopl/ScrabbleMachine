package com.scrabble.backend.algorithm.scrabble.alphabet;

import com.scrabble.backend.api.resolving.algorithm.scrabble.util.Alphabet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlphabetTest {
    @Test
    public void getEmptySymbolTest() {
        Assertions.assertEquals(Alphabet.getEmptySymbol(), ' ');
    }

    @Test
    public void valueOfLetterTest() {
        Assertions.assertEquals(Alphabet.valueOfLetter('l'), 2);
    }

    @Test
    public void isLetterTest() {
        Assertions.assertTrue(Alphabet.isLetter('l'));
        Assertions.assertFalse(Alphabet.isLetter('3'));
        Assertions.assertFalse(Alphabet.isLetter('%'));
    }

    @Test
    public void isAllowedCharacterTest() {
        Assertions.assertTrue(Alphabet.isAllowedCharacter('g'));
        Assertions.assertTrue(Alphabet.isAllowedCharacter(' '));
        Assertions.assertFalse(Alphabet.isAllowedCharacter('('));
    }

    @Test
    public void getRandomLetterVisualTest() {
        System.out.println(Alphabet.getRandomLetter());
    }
}
