package com.scrabble.backend.solving;

import com.scrabble.backend.api.dto.GameStateDto;
import com.scrabble.backend.solving.scrabble.ScrabbleResources;
import com.scrabble.backend.solving.solver.finder.Word;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scrabble.backend.solving.scrabble.ScrabbleResources.*;
import static com.scrabble.backend.solving.scrabble.Alphabet.emptySymbol;
import static com.scrabble.backend.solving.solver.Solver.getWords;

@Service
public class SolvingService {
    public static final String[] modes = {"score", "length"};

    public SolvingService(@Value("${config.scrabble_resources}") String scrabbleResourcesPath) {
        ScrabbleResources.path = scrabbleResourcesPath;
        getAlphabet("pl");
        getAlphabet("en");
        getDictionary("pl");
        getDictionary("en");
    }

    public List<Word> bestWords(GameStateDto request, String lang, String mode, Integer number) {
        throwIfIncorrectLang(lang);
        String rack = preprocessRack(request.getRack(), lang);
        char[][] board = preprocessBoard(request.getBoard(), lang);

        return getWords(board, rack, lang, number, mode);
    }

    public void throwIfIncorrectLang(String lang) {
        if (!supportedLanguages.contains(lang))
            throw new IllegalArgumentException("Language \"" + lang + "\" is not supported");
    }

    public static char[][] preprocessBoard(String[][] board, String lang) {
        if (board == null) throw new IllegalArgumentException("Board is mandatory");
        if (board.length != boardSize || board[0].length != boardSize)
            throw new IllegalArgumentException("Invalid board size");

        char[][] charBoard = new char[boardSize][boardSize];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                String lowerCase = board[i][j].length() == 0 ? String.valueOf(emptySymbol) : board[i][j].toLowerCase();

                throwIfIncorrectValue(lowerCase, lang);

                charBoard[i][j] = lowerCase.charAt(0);
            }
        }
        return charBoard;
    }

    public static String preprocessRack(String[] rack, String lang) {
        if (rack == null) throw new IllegalArgumentException("Rack is mandatory");
        if (rack.length != rackSize)
            throw new IllegalArgumentException("Invalid rack size: " + rack.length);

        StringBuilder builder = new StringBuilder();
        for (String letter : rack) {
            String lowerCase = letter.length() == 0 ? String.valueOf(emptySymbol) : letter.toLowerCase();
            throwIfIncorrectValue(lowerCase, lang);
            builder.append(lowerCase.charAt(0));
        }
        return builder.toString();
    }

    public static void throwIfIncorrectValue(String symbol, String lang) {
        if (symbol.length() != 1)
            throw new IllegalArgumentException("Invalid symbol \"" + symbol + "\"");

        if (!getAlphabet(lang).isLetterOrEmptySymbol(symbol.charAt(0)))
            throw new IllegalArgumentException("Invalid letter: \"" + symbol + "\"");
    }


}

