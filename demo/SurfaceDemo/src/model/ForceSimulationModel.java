package model;

import model.surface.Surface;

public class ForceSimulationModel {
	
    private boolean isPlaying = true;
    private final Surface surface;

    public ForceSimulationModel(Surface surface) {
    	this.surface = surface;
    }
    
    public void pauseGame() {
        isPlaying = false;
    }

    public void continueGame() {
        isPlaying = true;
    }

    public void resetGame() {
        isPlaying = true;
    }

    public Surface getSurface() {
        return surface;
    }
}
