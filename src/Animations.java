import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Animations {

    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // has animations stopped

    private List<Frame> frames = new ArrayList<Frame>();    // Arraylist of frames 

    public Animations(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();

    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        stopped = false;
    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame());
        currentFrame = 0;
    }

    public Frame[] getSprite() {
        frames.get(currentFrame);
		return Frame.getFrames();
    }

    public void update() {
        if (!stopped) {
            frameCount++;

            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }

    }

    public Animations walkLeft = new Animations(Images.lefttWalkDog1, 10);
	public Animations jumpLeft = new Animations(Images.leftJumpDog1, 10);
	public Animations idleLeft = new Animations(Images.leftIdleDog1, 10);

	public Animations walkRight = new Animations(Images.rightWalkDog1, 10);
	public Animations jumpRight = new Animations(Images.rightJumpDog1, 10);
	public Animations idleRight = new Animations(Images.rightIdleDog1, 10);

	public Animations move = idleRight;
}