package me.otho.zap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
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
    
	protected boolean windowed = true;
    protected boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST];
    protected boolean leftMouseDown = false;
    protected boolean rightMouseDown = false;
    
    protected float mouseX = 0.0f;
    protected float mouseY = 0.0f;
    
    private GLCapabilities caps;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cpCallback;
    private GLFWMouseButtonCallback mbCallback;
//    private GLFWFramebufferSizeCallback fbCallback;
    private GLFWWindowSizeCallback wsCallback;
    private Callback debugProc;
    
    
    
    public void glinit() {
    	if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);       

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        if (!windowed) {
            width = vidmode.width();
            height = vidmode.height();
            fbWidth = width;
            fbHeight = height;
        }
        window = glfwCreateWindow(width, height, "Dodge Game", !windowed ? monitor : 0L, NULL);
        if (window == NULL) 
            throw new AssertionError("Failed to create the GLFW window");
        
        glfwSetCursor(window, glfwCreateStandardCursor(GLFW_HAND_CURSOR));
        
        glfwSetWindowSizeCallback(window, wsCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int width, int height) {
                if (width > 0 && height > 0 && (Game.this.width != width || Game.this.height != height)) {
                	Game.this.width = width;
                	Game.this.height = height;
                }
            }
        });
        
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_UNKNOWN) 
                    return;
                
                // System.out.println(key);
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }
                if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                    keyDown[key] = true;
                } else {
                    keyDown[key] = false;
                }
            }
        });
        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                float normX = (float) ((xpos - width/2.0) / width * 2.0);
                float normY = (float) ((ypos - height/2.0) / height * 2.0);
                Game.this.mouseX = Math.max(-width/2.0f, Math.min(width/2.0f, normX));
                Game.this.mouseY = Math.max(-height/2.0f, Math.min(height/2.0f, normY));
            }
        });
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
        
        // Center our window
 		glfwSetWindowPos(
 			window,
 			(vidmode.width() - width) / 2,
 			(vidmode.height() - height) / 2
 		);
        glfwMakeContextCurrent(window);
        
        glfwSwapInterval(1);        

        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        fbWidth = framebufferSize.get(0);
        fbHeight = framebufferSize.get(1);
        caps = GL.createCapabilities();
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        if (!caps.OpenGL20) {
            throw new AssertionError("This demo requires OpenGL 2.0.");
        }
        debugProc = GLUtil.setupDebugMessageCallback();
        
        glEnable(GL_TEXTURE_2D);
        
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glfwShowWindow(window);
    }
    
    private void loop() {
    	double frame_cap = 1.0/60.0;
    	double frameTime = 0;
    	int frames = 0;
    	double time = Timer.getTime();
    	double unprocessed = 0;
    	
        while (!glfwWindowShouldClose(window)) {
        	boolean shouldRender = false;
        	
        	double time_2 = Timer.getTime();
        	double passed = time_2 - time;
        	unprocessed += passed;        	
        	frameTime += passed;
        	
        	time = time_2;
        	
        	while(unprocessed >= frame_cap ) {
        		unprocessed -= frame_cap;
        		glfwPollEvents();
        		update();
        		shouldRender = true;
        		
        		if ( frameTime >= 1.0 ) {
        			frameTime = 0;
        			//System.out.println("FPS: " + frames);
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
    
    public void init() {
    	System.out.println("Initializing Game");
    }
    
    public void update() {
    	System.out.println("Updating");
    }
    
    public void draw() {
    	System.out.println("Drawing");
    }
	
	public void run() {
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
        }
	}
	
	public boolean isKeyDown(int code) {
		return keyDown[code];
	}

}
