package me.mani.panemgames;

public enum GameState {
	LOBBY, STARTING, WARM_UP, INGAME, FINISHING, END;
	
	private static GameState currentGameState;
	
	public static GameState getGameState() {
		return currentGameState;
	}
	
	public static boolean isGameState(GameState gameState) {
		return gameState.equals(currentGameState);
	}
	
	public static void setGameState(GameState gameState) {
		currentGameState = gameState;
	}

}