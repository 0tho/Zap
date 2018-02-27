package me.otho.zap.simulation;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import me.otho.zap.util.Timer;

public class Game {
	
	protected long window;
	protected int width = 800;
	protected int height = 600;
	protected int fbWidth = 800;
	protected int fbHeight = 600;
	
	// Expected fps
	protected int fps = 60;
	// Current fps
	protected int frames;
	
	protected String title = "Zap Engine Game";
    
	protected boolean windowed = true;
    
    protected boolean leftMouseDown = false;
    protected boolean rightMouseDown = false;
    
    protected float mouseX = 0.0f;
    protected float mouseY = 0.0f;
    
    private GLCapabilities caps;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cpCallback;
    private GLFWMouseButtonCallback mbCallback;
    private GLFWFramebufferSizeCallback fbCallback;
    private GLFWWindowSizeCallback wsCallback;
    private Callback debugProc;
    
    public Game(String title, int width, int height, int fps) {
    	this.title = title;
    	this.width = width;
    	this.height = height;
    	this.fps = fps;    	
    }
    
    public final void glinit() {
    	// Try to initizalize glfw (the library that handles window)
    	if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

    	// Configurate window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);       

        // Get primary monitor
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        
        // Resizes window to fullscreen if game not windowed
        if (!windowed) {
            width = vidmode.width();
            height = vidmode.height();
            fbWidth = width;
            fbHeight = height;
        }
        
        // Creates the window
        window = glfwCreateWindow(width, height, title, !windowed ? monitor : 0L, NULL);
        
        // Check if window was created
        if (window == NULL) 
            throw new AssertionError("Failed to create the GLFW window");
        
        // Sets window cursor
        glfwSetCursor(window, glfwCreateStandardCursor(GLFW_HAND_CURSOR));
        
        // Callback for window resize
        glfwSetWindowSizeCallback(window, wsCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int width, int height) {
                if (width > 0 && height > 0 && (Game.this.width != width || Game.this.height != height)) {
                	Game.this.width = width;
                	Game.this.height = height;
                }
            }
        });
        
        // Callback for when a key is pressed
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_UNKNOWN) 
                    return;
                
                // System.out.println(key);
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        });
        
        // Callback for mouse movement
        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                float normX = (float) ((xpos - width/2.0) / width * 2.0);
                float normY = (float) ((ypos - height/2.0) / height * 2.0);
                Game.this.mouseX = Math.max(-width/2.0f, Math.min(width/2.0f, normX));
                Game.this.mouseY = Math.max(-height/2.0f, Math.min(height/2.0f, normY));
            }
        });
        
        // Callback for mouse click
        glfwSetMouseButtonCallback(window, mbCallback = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_LEFT) {
                    if (action == GLFW_PRESS)
                        leftMouseDown = true;
                    else if (action == GLFW_RELEASE)
                        leftMouseDown = false;
                } else if (button == GLFW_MOUSE_BUTTON_RIGHT) {
                    if (action == GLFW_PRESS)
                        rightMouseDown = true;
                    else if (action == GLFW_RELEASE)
                        rightMouseDown = false;
                }
            }
        });
        
        // Center our window on monitor
 		glfwSetWindowPos(
 			window,
 			(vidmode.width() - width) / 2,
 			(vidmode.height() - height) / 2
 		);
 		
 		// Creates a context for our window
        glfwMakeContextCurrent(window);
        
        // VSYNC
        glfwSwapInterval(1);        

        // Get framebuffersize
        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        
        // Width of framebuffer
        fbWidth = framebufferSize.get(0);
        
        // Heihgt of framebuffer
        fbHeight = framebufferSize.get(1);
        
        // Opengl capabilities
        caps = GL.createCapabilities();
        
        if (!caps.OpenGL20) {
            throw new AssertionError("This demo requires OpenGL 2.0.");
        }
        debugProc = GLUtil.setupDebugMessageCallback();
        
        glfwShowWindow(window);
    }
    
    private final void loop() {
    	double frame_cap = 1.0/fps;
    	double frameTime = 0;
    	double time = Timer.getTime();
    	double unprocessed = 0;
    	
        while (!glfwWindowShouldClose(window)) {
        	boolean shouldRender = false;
        	
        	double time_2 = Timer.getTime();
        	double passed = time_2 - time;
        	unprocessed += passed;        	
        	frameTime += passed;
        	
        	time = time_2;
        	
        	glfwPollEvents();
        	
        	while(unprocessed >= frame_cap ) {
        		unprocessed -= frame_cap;        		
        		update();
        		shouldRender = true;
        		
        		if ( frameTime >= 1.0 ) {
        			frameTime = 0;
        			frames = 0;
        		}
        	}
        	
            if ( shouldRender ) {
            	glViewport(0, 0, fbWidth, fbHeight);            
                draw();
                glfwSwapBuffers(window);
                frames++;
            }                 
            
        }
    }
	
	public final void run() {
		 try {
			glinit();
			init();
            loop();

            if (debugProc != null)
                debugProc.free();

            keyCallback.free();
            cpCallback.free();
            mbCallback.free();
            //fbCallback.free();
            wsCallback.free();
            glfwDestroyWindow(window);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
        	glfwTerminate();
        	finish();        
        }
	}
	
	public final boolean isKeyDown(int code) {
		return glfwGetKey(window, code) == GLFW_PRESS;
	}
	
    //------------------------------------------
	// Function below this line should be extended
	
    public void init() {
    	System.out.println("Initializing Game");
    }
    
    public void update() {
    	System.out.println("Updating");
    }
    
    public void draw() {
    	System.out.println("Drawing");
    }
    
    public void finish() {
	    
	}

}
