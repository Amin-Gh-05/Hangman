package hangman.Model;

import java.util.UUID;

public class Game {
    private final UUID gameId;
    private final String username;
    private final String word;
    private final int wrongGuesses;
    private final int time;
    private final boolean win;

    public Game(UUID gameId, String username, String word, int wrongGuesses, int time, boolean win) {
        this.gameId = gameId;
        this.username = username;
        this.word = word;
        this.wrongGuesses = wrongGuesses;
        this.time = time;
        this.win = win;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getUsername() {
        return username;
    }

    public String getWord() {
        return word;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public int getTime() {
        return time;
    }

    public boolean isWin() {
        return win;
    }

    @Override
    public String toString() {
        return username +
                ", word: " + word +
                ", wrongGuesses: " + wrongGuesses +
                ", time: " + time +
                ", win: " + win;
    }
}