package ext.pathfinding.test;

import ext.pathfinding.grid.GridPath;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

@SuppressWarnings("serial")
public class BasicApplet extends Applet implements Runnable{
	
	//Set the size in the parameter when launching the applet
	private final int width = 850;
	private final int height = 850;
	
	DrawGridMap draw = new DrawGridMap(new BuildRandomMap(100, 100).getMap());
	MouseSelection mouse = new MouseSelection();
	Pathfinding pathfinding = new Pathfinding();
	Path path = new Path();
	Menu menu = new Menu();
	
	boolean selectionChanged = false;

	/**
	 * Overwrite this method in subclass
	 */
	protected void update(int deltaTime){
		if(selectionChanged){
			if(mouse.isSet()){
				GridPath aPath = pathfinding.getPath(mouse.getStart(), mouse.getEnd(), draw.getMap());
				path.setPath(aPath);
				selectionChanged = false;
			}
		}
	}
	
	/**
	 * Overwrite this method in subclass
	 */
	protected void render(Graphics2D g){
		draw.drawMap(g);
		path.drawPath(g);
		mouse.drawMouseSelection(g);
		menu.drawMenu(g);
	}
	
	/* --------------------------------- HELPING METHODS ------------------ */
	
	Canvas canvas;
	BufferStrategy bufferStrategy;
	Thread gameloopThread;
	
	@Override
	public void init(){
		canvas = new Canvas();
		canvas.setBounds(0, 0, width, height);
		add(canvas);
		canvas.setIgnoreRepaint(true);

		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();

		canvas.addMouseListener(new MouseControl());
		canvas.addMouseMotionListener(new MouseControl());
		canvas.requestFocus();
	}
	
	@Override
	public void start(){
		gameloopThread = new Thread(this);
		gameloopThread.start();
	}
	
	@Override
	public void stop(){
		setRunning(false);
		try {
			gameloopThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class MouseControl extends MouseAdapter{
		
		@Override
		public void mousePressed(MouseEvent e){
			selectionChanged = mouse.setMouse(e);
		}
		
	}
	
	private long desiredFPS = 60;
    private long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
    
	private boolean running = true;
	
	private synchronized void setRunning(boolean running){
		this.running = running;
	}
	
	private synchronized boolean isRunning(){
		return running;
	}
	
	public void run(){
		
		setRunning(true);
		
		long beginLoopTime;
		long endLoopTime;
		long currentUpdateTime = System.nanoTime();
		long lastUpdateTime;
		long deltaLoop;
		
		while(!isActive()){
			Thread.yield();
		}
		while(isRunning()){
			beginLoopTime = System.nanoTime();
			
			render();
			
			lastUpdateTime = currentUpdateTime;
			currentUpdateTime = System.nanoTime();
			update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));
			
			endLoopTime = System.nanoTime();
			deltaLoop = endLoopTime - beginLoopTime;
	        
	        if(deltaLoop > desiredDeltaLoop){
	            //Do nothing. We are already late.
	        }else{
	            try{
	                Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
	            }catch(InterruptedException e){
	                //Do nothing
	            }
	        }
		}
	}

	private void render() {
		try{
			Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
			g.clearRect(0, 0, width, height);
			render(g);
			bufferStrategy.show();
			g.dispose();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
