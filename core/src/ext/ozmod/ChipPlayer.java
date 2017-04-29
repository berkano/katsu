package ext.ozmod;

public interface ChipPlayer {

	boolean OZ_DEBUG=false;
	OZMod.ERR load(LoaderFromMemory _input);
	void play();
	void done();
	void run();
	void setLoopable(boolean _b);
	void setVolume(float volume);
	void setFrequency(int frequency);
	void setDaemon(boolean on);
	boolean isLoopable();
	byte[] getMixBuffer();
}
