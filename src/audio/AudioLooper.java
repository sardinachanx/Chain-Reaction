package audio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioLooper implements Runnable{

	private static final Object LOCK = new Object();

	protected volatile boolean paused;
	protected volatile boolean restart;
	protected String audio;
	protected boolean on;

	public AudioLooper(String audio){
		this.audio = audio;
		on = true;
		paused = true;
		restart = false;
	}

	@Override
	public void run(){
		while(on){
			restart = false;
			synchronized(LOCK){
				SourceDataLine line = null;
				try{
					int bufferSize = 4096;
					AudioFormat format = AudioSystem.getAudioFileFormat(new File(audio)).getFormat();
					line = AudioSystem.getSourceDataLine(format);
					line.open(format, bufferSize);
					BufferedInputStream stream = new BufferedInputStream(
							AudioSystem.getAudioInputStream(new File(audio)));
					byte[] buffer = new byte[bufferSize];
					int bufferLength = 0;
					line.start();
					bufferLength = stream.read(buffer);
					while(bufferLength >= 0 && on && !restart){
						while(paused){
							LOCK.wait();
						}
						line.write(buffer, 0, bufferLength);
						bufferLength = stream.read(buffer);
					}
					line.drain();
					line.stop();
				}
				catch(IOException e){
					e.printStackTrace();
				}
				catch(UnsupportedAudioFileException e){
					e.printStackTrace();
				}
				catch(LineUnavailableException e){
					e.printStackTrace();
				}
				catch(InterruptedException e){
					return;
				}
				finally{
					if(line != null){
						line.close();
					}
				}
			}
		}
	}

	public void setPaused(boolean paused){
		if(paused == this.paused){
			return;
		}
		if(!paused){
			synchronized(LOCK){
				this.paused = false;
				LOCK.notifyAll();
			}
		}
		else{
			this.paused = true;
		}
	}

	public boolean isPaused(){
		return paused;
	}

	public boolean isRestart(){
		return restart;
	}

}
